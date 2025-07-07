package com.science.gtnl.common.item.items;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.client.GTNLCreativeTabs;

public class InfinityTorch extends Item {

    public InfinityTorch() {
        this.setUnlocalizedName("InfinityTorch");
        this.setTextureName(RESOURCE_ROOT_ID + ":" + "InfinityTorch");
        this.setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
        this.setMaxStackSize(1);
        GTNLItemList.InfinityTorch.set(new ItemStack(this, 1));
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (world.isRemote) return true;

        x += Facing.offsetsXForSide[side];
        y += Facing.offsetsYForSide[side];
        z += Facing.offsetsZForSide[side];

        if (!world.isAirBlock(x, y, z)) return false;

        if (Blocks.torch.canPlaceBlockAt(world, x, y, z)) {
            world.setBlock(x, y, z, Blocks.torch, 0, 3);
            world.playSoundEffect(
                x + 0.5,
                y + 0.5,
                z + 0.5,
                Blocks.torch.stepSound.getStepResourcePath(),
                (Blocks.torch.stepSound.getVolume() + 1.0F) / 2.0F,
                Blocks.torch.stepSound.getPitch() * 0.8F);
            return true;
        }

        return false;
    }
}
