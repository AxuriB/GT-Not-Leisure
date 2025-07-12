package com.science.gtnl.common.item.items.Bauble;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;

import java.util.Random;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.client.GTNLCreativeTabs;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.common.item.equipment.bauble.ItemBauble;

public class LuckyHorseshoe extends ItemBauble {

    private static final Random rand = new Random();

    public LuckyHorseshoe() {
        super("LuckyHorseshoe");
        this.setTextureName(RESOURCE_ROOT_ID + ":" + "LuckyHorseshoe");
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        GTNLItemList.LuckyHorseshoe.set(new ItemStack(this, 1));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public BaubleType getBaubleType(ItemStack stack) {
        return BaubleType.UNIVERSAL;
    }

    @Override
    public void onWornTick(ItemStack stack, EntityLivingBase player) {
        player.fallDistance = 0;
    }

    @Override
    public String getUnlocalizedNameInefficiently(ItemStack stack) {
        String s = this.getUnlocalizedName(stack);
        return s == null ? "" : StatCollector.translateToLocal(s);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon(this.getIconString());
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.entityLiving instanceof EntityPlayerMP player)) return;
        if (!hasLuckyHorseshoeEquipped(player)) return;

        float chance = 0.9F;
        if (rand.nextFloat() < chance) {
            event.setCanceled(true);
            player.setHealth(1.0F);
            player.hurtResistantTime = 60;
            player.worldObj.playSoundAtEntity(player, "random.orb", 1.0F, 1.0F);
        }
    }

    private boolean hasLuckyHorseshoeEquipped(EntityPlayerMP player) {
        if (BaublesApi.getBaubles(player) == null) return false;

        for (int i = 0; i < BaublesApi.getBaubles(player)
            .getSizeInventory(); i++) {
            ItemStack stack = BaublesApi.getBaubles(player)
                .getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof LuckyHorseshoe) {
                return true;
            }
        }
        return false;
    }
}
