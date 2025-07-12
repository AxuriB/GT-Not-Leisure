package com.science.gtnl.common.item.items.Bauble;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.client.GTNLCreativeTabs;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import vazkii.botania.common.item.equipment.bauble.ItemBauble;

public class RoyalGel extends ItemBauble {

    public RoyalGel() {
        super("RoyalGel");
        this.setTextureName(RESOURCE_ROOT_ID + ":" + "RoyalGel");
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        GTNLItemList.RoyalGel.set(new ItemStack(this, 1));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public BaubleType getBaubleType(ItemStack stack) {
        return BaubleType.UNIVERSAL;
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
    public void onPlayerAttacked(LivingAttackEvent event) {
        if (!(event.entityLiving instanceof EntityPlayerMP player)) return;
        if (!(event.source.getSourceOfDamage() instanceof EntitySlime)) return;

        if (hasRoyalGelEquipped(player)) {
            event.setCanceled(true);
        }
    }

    private boolean hasRoyalGelEquipped(EntityPlayerMP player) {
        if (BaublesApi.getBaubles(player) == null) return false;

        for (int i = 0; i < BaublesApi.getBaubles(player)
            .getSizeInventory(); i++) {
            ItemStack stack = BaublesApi.getBaubles(player)
                .getStackInSlot(i);
            if (stack != null && stack.getItem() instanceof RoyalGel) {
                return true;
            }
        }
        return false;
    }
}
