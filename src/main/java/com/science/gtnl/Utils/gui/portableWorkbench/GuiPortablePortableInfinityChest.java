package com.science.gtnl.Utils.gui.portableWorkbench;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import com.science.gtnl.api.mixinHelper.IInfinityChestGui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiPortablePortableInfinityChest extends GuiPortableAvaritiaddonsChest implements IInfinityChestGui {

    public GuiPortablePortableInfinityChest(@Nonnull final ItemStack stack, final InventoryPlayer inventoryPlayer) {
        super(new ContainerPortableInfinityChest(stack, inventoryPlayer));
    }

    @Override
    @NotNull
    public String getContainerName() {
        return "container.InfinityChest";
    }

}
