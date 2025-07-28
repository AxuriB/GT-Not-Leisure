package com.science.gtnl.common.item;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;
import static com.science.gtnl.loader.ItemLoader.infinityCell;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import appeng.api.AEApi;
import appeng.api.exceptions.AppEngException;
import appeng.api.storage.data.IItemList;
import appeng.items.contents.CellUpgrades;
import appeng.util.item.ItemList;
import com.github.bsideup.jabel.Desugar;
import com.glodblock.github.api.FluidCraftAPI;
import com.glodblock.github.common.storage.FluidCellInventory;
import com.glodblock.github.common.storage.FluidCellInventoryHandler;
import com.glodblock.github.common.storage.IStorageFluidCell;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;

import com.glodblock.github.inventory.InventoryHandler;
import com.glodblock.github.inventory.gui.GuiType;
import com.glodblock.github.util.BlockPos;
import com.science.gtnl.client.GTNLCreativeTabs;

import appeng.api.config.AccessRestriction;
import appeng.api.config.Actionable;
import appeng.api.implementations.tiles.IChestOrDrive;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.storage.ICellHandler;
import appeng.api.storage.IMEInventory;
import appeng.api.storage.IMEInventoryHandler;
import appeng.api.storage.ISaveProvider;
import appeng.api.storage.StorageChannel;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IAEStack;
import appeng.core.sync.GuiBridge;
import appeng.items.storage.ItemCreativeStorageCell;
import appeng.util.Platform;
import appeng.util.item.AEFluidStack;
import appeng.util.item.AEItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemInfinityCell extends ItemCreativeStorageCell implements IStorageFluidCell {

    private static final long StorageSIZE = 1L << 53 - 1;

    public ItemInfinityCell() {
        setTextureName(RESOURCE_ROOT_ID + ":" + "infinity_cell");
        setUnlocalizedName("infinity_cell");
        setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        AEApi.instance().registries().cell().addCellHandler(new InfinityCellHandler());
    }

    @Override
    protected void getCheckedSubItems(final Item sameItem, final CreativeTabs creativeTab,
        final List<ItemStack> itemStacks) {
        var s = new SubItem[16];
        for (short i = 0;i < 16;i++){
            s[i] = SubItem.getInstance("minecraft:dye",i);
        }
        itemStacks.add(getSubItem(s));
    }

    private ItemStack getSubItem(String... fluidNames) {
        var cell = new ItemStack(infinityCell);
        var tag = new NBTTagCompound();
        tag.setString("t", "f");
        var list = new NBTTagList();
        for (String fluidName : fluidNames) {
            var rtag = new NBTTagCompound();
            rtag.setString("FluidName", fluidName);
            list.appendTag(rtag);
        }
        tag.setTag("list", list);
        cell.setTagCompound(tag);
        return cell;
    }

    private ItemStack getSubItem(SubItem... subItems) {
        var cell = new ItemStack(infinityCell);
        var tag = new NBTTagCompound();
        tag.setString("t", "i");
        var list = new NBTTagList();
        for (SubItem subItem : subItems) {
            var rtag = new NBTTagCompound();
            rtag.setString("id", subItem.id);
            rtag.setShort("Damage", subItem.Damage);
            if (subItem.nbt != null) {
                rtag.setTag("tag", subItem.nbt);
            }
            list.appendTag(rtag);
        }
        tag.setTag("list", list);
        cell.setTagCompound(tag);
        return cell;
    }

    @Desugar
    private record SubItem(String id, short Damage,@Nullable NBTTagCompound nbt){

        public static SubItem getInstance(String id, short Damege){
            return new SubItem(id,Damege,null);
        }

        public static SubItem getInstance(String id, short Damege,NBTTagCompound nbt){
            return new SubItem(id,Damege,nbt);
        }
    }

    @Nullable
    public List<? extends IAEStack<?>> getRecord(@NotNull ItemStack stack, @NotNull StorageChannel s) {
        if (stack.hasTagCompound()) {
            if (stack.getTagCompound().hasKey("list")) {
                return getInfinityStack(stack.getTagCompound().getTagList("list",10), s);
            }
        }
        return null;
    }

    @Nullable
    private List<? extends IAEStack<?>> getInfinityStack(NBTTagList list, @NotNull StorageChannel s) {
        return switch (s) {
            case ITEMS -> {
                List<IAEItemStack> out = new ArrayList<>();
                for (int i = 0; i < list.tagCount(); i++) {
                    var tag = list.getCompoundTagAt(i);
                    var item = (Item) Item.itemRegistry.getObject(tag.getString("id"));
                    if (item == null) {
                        yield null;
                    }
                    final ItemStack itemstack = new ItemStack(item, 1, tag.getShort("Damage"));

                    if (tag.hasKey("tag", 10)) {
                        itemstack.stackTagCompound = tag.getCompoundTag("tag");
                    }
                    out.add(AEItemStack.create(itemstack));
                }
                yield out;
            }
            case FLUIDS -> {
                List<IAEFluidStack> out = new ArrayList<>();
                for (int i = 0; i < list.tagCount(); i++) {
                    var tag = list.getCompoundTagAt(i);
                    String fluidName = tag.getString("FluidName");
                    if (fluidName == null) {
                        yield null;
                    }
                    var fluid = FluidRegistry.getFluid(fluidName);
                    if (fluid == null) {
                        yield null;
                    }
                    FluidStack stack = new FluidStack(fluid, 1);
                    out.add(AEFluidStack.create(stack));
                }
                yield out;
            }
        };
    }

    public StorageChannel getChannel(ItemStack stack) {
        if (stack.getItem() instanceof ItemInfinityCell && stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();

            String type = tag.getString("t");
            if (type.equals("f")) {
                return StorageChannel.FLUIDS;
            }

            if (type.equals("i")) {
                return StorageChannel.ITEMS;
            }
        }
        return null;
    }

    @Override
    public IInventory getUpgradesInventory(ItemStack is) {
        return new CellUpgrades(is, 0);
    }

    @Override
    public boolean isEditable(final ItemStack is) {
        return false;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        /*
        var c = getChannel(stack);
        if (c != null) {
            var r = getRecord(stack, c);
            if (r != null) {
                return StatCollector.translateToLocalFormatted(
                    "item.infinity_cell.name",
                    c == StorageChannel.ITEMS ? ((IAEItemStack) r).getItemStack()
                        .getDisplayName()
                        : ((IAEFluidStack) r).getFluidStack()
                            .getLocalizedName());
            }
            return StatCollector.translateToLocal("item.infinity_cell.unknown");
        }
         */
        return StatCollector.translateToLocal("item.infinity_cell.unknown");
    }

    @Override
    public long getBytes(ItemStack cellItem) {
        return 0;
    }

    @Override
    public int getBytesPerType(ItemStack cellItem) {
        return 0;
    }

    @Override
    public boolean isBlackListed(ItemStack cellItem, IAEFluidStack requestedAddition) {
        return requestedAddition == null || requestedAddition.getFluid() == null
            || FluidCraftAPI.instance().isBlacklistedInStorage(requestedAddition.getFluid().getClass());
    }

    @Override
    public boolean storableInStorageCell() {
        return false;
    }

    @Override
    public boolean isStorageCell(ItemStack i) {
        return true;
    }

    @Override
    public double getIdleDrain(ItemStack is) {
        return 0;
    }

    @Override
    public int getTotalTypes(ItemStack cellItem) {
        return 0;
    }

    @SuppressWarnings("unchecked")
    public static class InfinityCellHandler implements ICellHandler {

        @Override
        public boolean isCell(ItemStack is) {
            return is != null && is.getItem() instanceof ItemInfinityCell;
        }

        @Override
        public IMEInventoryHandler<?> getCellInventory(ItemStack item, ISaveProvider host, StorageChannel s) {
            if (s != null) {
                var stack = infinityCell.getRecord(item, s);
                if (stack != null) {
                    return switch (s){
                        case ITEMS -> new InfinityItemCellHandler((List<IAEItemStack>) stack);
                        case FLUIDS -> {
                            try {
                                yield new InfinityFluidCellHandler(item,host,(List<IAEFluidStack>) stack);
                            } catch (AppEngException e) {
                                yield null;
                            }
                        }
                    };
                }
            }
            return null;
        }

        @Override
        // TODO:图标渲染自己写去（
        @SideOnly(Side.CLIENT)
        public IIcon getTopTexture_Light() {
            return null;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public IIcon getTopTexture_Medium() {
            return null;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public IIcon getTopTexture_Dark() {
            return null;
        }

        @Override
        public void openChestGui(EntityPlayer player, IChestOrDrive chest, ICellHandler cellHandler,
            IMEInventoryHandler inv, ItemStack is, StorageChannel chan) {
            switch (inv.getChannel()) {
                case ITEMS -> Platform.openGUI(player, (TileEntity) chest, chest.getUp(), GuiBridge.GUI_ME);
                case FLUIDS -> InventoryHandler.openGui(
                    player,
                    ((TileEntity) chest).getWorldObj(),
                    new BlockPos((TileEntity) chest),
                    chest.getUp(),
                    GuiType.FLUID_TERMINAL);
            }
        }

        @Override
        public int getStatusForCell(ItemStack is, IMEInventory handler) {
            return 2;
        }

        @Override
        // TODO:耗电你自己填啦
        public double cellIdleDrain(ItemStack is, IMEInventory handler) {
            return 0;
        }
    }

    public static class InfinityFluidCellHandler extends FluidCellInventoryHandler {
        public InfinityFluidCellHandler(ItemStack o, ISaveProvider container,List<IAEFluidStack> stack) throws AppEngException {
            super(new InfinityFluidCellInventory(o, container, stack));
        }
    }

    public static class InfinityItemCellHandler implements IMEInventoryHandler<IAEItemStack> {
        private final List<IAEItemStack> record;

        private InfinityItemCellHandler(List<IAEItemStack> stack) {
            this.record = stack;
            this.record.forEach(i -> i.setStackSize(StorageSIZE));
        }

        @Override
        public StorageChannel getChannel() {
            return StorageChannel.ITEMS;
        }

        @Override
        public AccessRestriction getAccess() {
            return AccessRestriction.READ_WRITE;
        }

        @Override
        public boolean isPrioritized(IAEItemStack stack) {
            for (IAEItemStack item : this.record) {
                if (item.getItem() == stack.getItem() && item.getItemDamage() == stack.getItemDamage()){
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean canAccept(IAEItemStack stack) {
            for (IAEItemStack item : this.record) {
                if (item.getItem() == stack.getItem() && item.getItemDamage() == stack.getItemDamage()){
                    return true;
                }
            }
            return false;
        }

        @Override
        public IItemList<IAEItemStack> getAvailableItems(final IItemList<IAEItemStack> out, int iteration) {
            if (out instanceof ItemList) {
                record.forEach(out::add);
            }
            return out;
        }

        @Override
        public IAEItemStack getAvailableItem(@Nonnull IAEItemStack request, int iteration) {
            return request.copy();
        }

        @Override
        public int getPriority() {
            return 0;
        }

        @Override
        public int getSlot() {
            return 0;
        }

        @Override
        public boolean validForPass(final int i) {
            return true;
        }

        @Override
        public IAEItemStack injectItems(IAEItemStack stack, Actionable mode, BaseActionSource src) {
            for (IAEItemStack item : this.record) {
                if (item.getItem() == stack.getItem() && item.getItemDamage() == stack.getItemDamage()){
                    return null;
                }
            }
            return stack;
        }

        @Override
        public IAEItemStack extractItems(final IAEItemStack stack, final Actionable mode, final BaseActionSource src) {
            for (IAEItemStack item : this.record) {
                if (item.getItem() == stack.getItem() && item.getItemDamage() == stack.getItemDamage()){
                    return stack;
                }
            }
            return null;
        }
    }

    public static class InfinityFluidCellInventory extends FluidCellInventory {
        private final List<IAEFluidStack> record;

        public InfinityFluidCellInventory(ItemStack o, ISaveProvider container,List<IAEFluidStack> stack) throws AppEngException {
            super(o, container);
            this.record = stack;
            this.record.forEach(i -> i.setStackSize(StorageSIZE));
        }

        @Override
        public IAEFluidStack injectItems(IAEFluidStack stack, Actionable mode, BaseActionSource src) {
            for (IAEFluidStack fluid : this.record) {
                if (fluid.getFluid() == stack.getFluid()){
                    return null;
                }
            }
            return stack;
        }

        @Override
        public IAEFluidStack extractItems(IAEFluidStack stack, Actionable mode, BaseActionSource src) {
            for (IAEFluidStack fluid : this.record) {
                if (fluid.getFluid() == stack.getFluid()){
                    return stack;
                }
            }
            return null;
        }

        @Override
        protected void loadCellFluids() {
            if (this.cellFluids == null) {
                this.cellFluids = AEApi.instance().storage().createFluidList();
            }
            this.cellFluids.resetStatus();
            this.record.forEach(this.cellFluids::add);
        }
    }
}
