package com.science.gtnl.Utils.gui.portableWorkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.science.gtnl.common.item.items.PortableItem;

public class ContainerBasicWorkbench extends ContainerWorkbench {

    public ContainerBasicWorkbench(EntityPlayer player, World world) {
        super(player.inventory, world, 0, 0, 0);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        ItemStack held = player.getHeldItem();
        return held != null && held.getItemDamage() == 0 && held.getItem() instanceof PortableItem;
    }
}
