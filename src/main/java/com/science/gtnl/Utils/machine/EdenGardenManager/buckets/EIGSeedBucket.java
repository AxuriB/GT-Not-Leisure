package com.science.gtnl.Utils.machine.EdenGardenManager.buckets;

import static com.science.gtnl.common.machine.multiblock.EdenGarden.*;

import java.util.*;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.*;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import com.science.gtnl.Utils.machine.EdenGardenManager.*;
import com.science.gtnl.common.machine.multiblock.EdenGarden;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.util.GTUtility;
import gregtech.common.GTDummyWorld;

public class EIGSeedBucket extends EIGBucket {

    public static final IEIGBucketFactory factory = new Factory();
    private static final String NBT_IDENTIFIER = "SEED";
    private static final int REVISION_NUMBER = 0;
    private static final int FORTUNE_LEVEL = 200;

    private static final int DEFAULT_GROWTH = 7;
    private static final int NATURA_GROWTH = 8;

    private static final GreenHouseWorld fakeWorld = new GreenHouseWorld(5, 5, 5);

    private boolean isValid = false;
    private EIGDropTable drops = new EIGDropTable();

    public static class Factory implements IEIGBucketFactory {

        @Override
        public String getNBTIdentifier() {
            return NBT_IDENTIFIER;
        }

        @Override
        public EIGBucket tryCreateBucket(EdenGarden greenhouse, ItemStack input) {
            return new EIGSeedBucket(greenhouse, input);
        }

        @Override
        public EIGBucket restore(NBTTagCompound nbt) {
            return new EIGSeedBucket(nbt);
        }
    }

    private EIGSeedBucket(EdenGarden greenhouse, ItemStack seed) {
        super(seed, 1, null);
        this.recalculateDrops(greenhouse);
    }

    private EIGSeedBucket(NBTTagCompound nbt) {
        super(nbt);
        this.drops = new EIGDropTable(nbt, "drops");
        this.isValid = nbt.getInteger("version") == REVISION_NUMBER && !this.drops.isEmpty();
    }

    @Override
    public NBTTagCompound save() {
        NBTTagCompound nbt = super.save();
        nbt.setTag("drops", this.drops.save());
        nbt.setInteger("version", REVISION_NUMBER);
        return nbt;
    }

    @Override
    public String getNBTIdentifier() {
        return NBT_IDENTIFIER;
    }

    @Override
    public void addProgress(double multiplier, EIGDropTable tracker) {
        if (this.isValid) {
            this.drops.addTo(tracker, multiplier * this.seedCount);
        }
    }

    @Override
    public boolean isValid() {
        return super.isValid() && this.isValid;
    }

    @Override
    public boolean revalidate(EdenGarden greenhouse) {
        this.recalculateDrops(greenhouse);
        return this.isValid();
    }

    /** 核心：重新计算掉落表 */
    private void recalculateDrops(EdenGarden greenhouse) {
        this.isValid = false;

        Item item = this.seed.getItem();
        if (!(item instanceof IPlantable)) return;

        Block plantBlock = getPlantBlock(item);
        if (plantBlock == null) return;

        int optimalGrowthMetadata = getOptimalGrowthStage(item);

        // 模拟掉落
        EIGDropTable newDrops = new EIGDropTable();
        fakeWorld.dropTable = newDrops;
        for (int i = 0; i < NUMBER_OF_DROPS_TO_SIMULATE; i++) {
            List<ItemStack> blockDrops = plantBlock.getDrops(fakeWorld, 0, 0, 0, optimalGrowthMetadata, FORTUNE_LEVEL);
            if (blockDrops != null) {
                for (ItemStack drop : blockDrops) {
                    newDrops.addDrop(drop, drop.stackSize);
                }
            }
        }

        // 移除种子，确保平衡
        World world = greenhouse.getBaseMetaTileEntity()
            .getWorld();
        if (!removeSeedFromDrops(world, newDrops, this.seed, NUMBER_OF_DROPS_TO_SIMULATE)) return;

        // 平均化
        newDrops.entrySet()
            .forEach(e -> e.setValue(e.getValue() / NUMBER_OF_DROPS_TO_SIMULATE));

        if (newDrops.isEmpty()) return;

        this.drops = newDrops;
        this.isValid = true;
    }

    /** 获取对应的植物方块 */
    private Block getPlantBlock(Item item) {
        if (item instanceof ItemSeeds seeds) {
            return seeds.getPlant(fakeWorld, 0, 0, 0);
        }
        if (item instanceof ItemSeedFood seedFood) {
            return seedFood.getPlant(fakeWorld, 0, 0, 0);
        }
        return null;
    }

    /** 判断最佳成熟阶段 */
    private int getOptimalGrowthStage(Item item) {
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(item);
        return (id != null && Objects.equals(id.modId, "Natura")) ? NATURA_GROWTH : DEFAULT_GROWTH;
    }

    /** 移除掉落物中的种子 */
    private boolean removeSeedFromDrops(World world, EIGDropTable drops, ItemStack seed, int seedsToConsume) {
        ItemStack safeSeed = seed.copy();
        safeSeed.stackSize = 1;

        int seedCountAfterRemoval = (int) Math.round(drops.getItemAmount(safeSeed)) - seedsToConsume;
        if (seedCountAfterRemoval >= 0) {
            drops.setItemAmount(safeSeed, seedCountAfterRemoval);
            return true;
        }

        // 否则尝试合成路径
        return tryConsumeCraftableSeeds(world, drops, safeSeed, -seedCountAfterRemoval);
    }

    /** 尝试用合成配方消耗掉种子 */
    private boolean tryConsumeCraftableSeeds(World world, EIGDropTable drops, ItemStack seed, int needed) {
        List<IRecipe> recipes = CraftingManager.getInstance()
            .getRecipeList()
            .parallelStream()
            .filter(r -> GTUtility.areStacksEqual(r.getRecipeOutput(), seed))
            .collect(Collectors.toList());

        if (recipes.isEmpty()) return false;

        for (Iterator<Map.Entry<ItemStack, Double>> it = drops.entrySet()
            .iterator(); it.hasNext();) {
            Map.Entry<ItemStack, Double> entry = it.next();
            ItemStack input = entry.getKey()
                .copy();
            input.stackSize = 1;

            int count = (int) Math.round(entry.getValue());
            EIGCraftingSeedFinder finder = new EIGCraftingSeedFinder(input);

            for (IRecipe recipe : recipes) {
                if (recipe.matches(finder, world)) {
                    int outputsPerCraft = recipe.getCraftingResult(finder).stackSize;
                    int craftableSeeds = outputsPerCraft * count;

                    if (needed >= craftableSeeds) {
                        it.remove();
                        needed -= craftableSeeds;
                    } else {
                        entry.setValue(entry.getValue() - (double) needed / outputsPerCraft);
                        return true;
                    }

                    if (needed <= 0) return true;
                }
            }
        }
        return false;
    }

    /** 简易合成环境 */
    public static class EIGCraftingSeedFinder extends InventoryCrafting {

        private final ItemStack recipeInput;

        public EIGCraftingSeedFinder(ItemStack recipeInput) {
            super(null, 3, 3);
            this.recipeInput = recipeInput;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return slot == 0 ? this.recipeInput.copy() : null;
        }

        @Override
        public ItemStack getStackInSlotOnClosing(int slot) {
            return null;
        }

        @Override
        public ItemStack decrStackSize(int slot, int amount) {
            return null;
        }

        @Override
        public void setInventorySlotContents(int slot, ItemStack stack) {}
    }

    /** 假想的温室世界 */
    public static class GreenHouseWorld extends GTDummyWorld {

        public int x, y, z, meta = 0;
        public Block block;
        public EIGDropTable dropTable;

        GreenHouseWorld(int x, int y, int z) {
            super();
            this.x = x;
            this.y = y;
            this.z = z;
            this.rand = new GreenHouseRandom();
        }

        @Override
        public int getBlockMetadata(int aX, int aY, int aZ) {
            return (aX == x && aY == y && aZ == z) ? 7 : 0;
        }

        @Override
        public Block getBlock(int aX, int aY, int aZ) {
            return (aY == y - 1) ? Blocks.farmland : Blocks.air;
        }

        @Override
        public boolean spawnEntityInWorld(Entity entity) {
            if (dropTable == null) return false;
            if (entity instanceof EntityLivingBase living) {
                living.captureDrops = true;
                living.onDeath(DamageSource.generic);
                living.captureDrops = false;
                if (living.capturedDrops != null) {
                    for (EntityItem drop : living.capturedDrops) {
                        ItemStack stack = drop.getEntityItem();
                        dropTable.addDrop(stack, stack.stackSize);
                    }
                }
            }
            return false;
        }

        @Override
        public int getBlockLightValue(int x, int y, int z) {
            return 10;
        }

        @Override
        public boolean setBlock(int aX, int aY, int aZ, Block aBlock, int aMeta, int aFlags) {
            if (aBlock == Blocks.air) return false;
            if (aX == x && aY == y && aZ == z) return false;
            this.block = aBlock;
            this.meta = aMeta;
            return true;
        }
    }

    private static class GreenHouseRandom extends Random {

        private static final long serialVersionUID = -387271808935248890L;

        @Override
        public int nextInt(int bound) {
            return 0;
        }
    }
}
