package com.science.gtnl.Utils.gui.portableWorkbench;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

public class GuiPortablePortableCompressedChest extends GuiPortableAvaritiaddonsChest {

    public GuiPortablePortableCompressedChest(ItemStack stack, InventoryPlayer playerInv) {
        super(new ContainerPortableCompressedChest(stack, playerInv));
    }

    @Override
    protected @NotNull String getContainerName() {
        return "container.CompressedChest";
    }
}
