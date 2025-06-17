package com.science.gtnl.common.packet;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

@Getter
public class TileEntityNBTPacket implements IMessage {

    private int blockId;
    private int metadata;
    private NBTTagCompound nbt;

    public TileEntityNBTPacket() {}

    public TileEntityNBTPacket(int blockId, int metadata, NBTTagCompound nbt) {
        this.blockId = blockId;
        this.metadata = metadata;
        this.nbt = nbt;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.blockId = buf.readInt();
        this.metadata = buf.readInt();
        this.nbt = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.blockId);
        buf.writeInt(this.metadata);
        ByteBufUtils.writeTag(buf, this.nbt);
    }

    public static class Handler implements IMessageHandler<TileEntityNBTPacket, IMessage> {

        @Override
        public IMessage onMessage(TileEntityNBTPacket message, MessageContext ctx) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityClientPlayerMP player = mc.thePlayer;
            if (player != null) {
                int blockId = message.getBlockId();
                int metadata = message.getMetadata();
                NBTTagCompound tileEntityNBT = message.getNbt();

                Block block = Block.getBlockById(blockId);

                if (block != null) {
                    ItemStack itemstack = new ItemStack(block, 1, metadata);

                    if (tileEntityNBT != null && !tileEntityNBT.hasNoTags()) {
                        NBTTagCompound itemTag = new NBTTagCompound();
                        itemTag.setTag("BlockEntityTag", tileEntityNBT);

                        NBTTagCompound displayTag = new NBTTagCompound();
                        NBTTagList loreList = new NBTTagList();
                        loreList.appendTag(new NBTTagString("§5§o(+NBT)"));
                        displayTag.setTag("Lore", loreList);
                        itemTag.setTag("display", displayTag);
                        itemstack.setTagCompound(itemTag);
                    }

                    boolean added = false;
                    for (int i = 0; i < 9; i++) {
                        ItemStack stackInSlot = player.inventory.getStackInSlot(i);
                        if (stackInSlot != null && stackInSlot.isItemEqual(itemstack)
                            && ItemStack.areItemStackTagsEqual(stackInSlot, itemstack)) {
                            player.inventory.currentItem = i;
                            added = true;
                            break;
                        }
                    }

                    if (!added) {
                        int slot = player.inventory.getFirstEmptyStack();
                        if (slot < 0 || slot >= 9) {
                            slot = player.inventory.currentItem;
                        }
                        player.inventory.setInventorySlotContents(slot, itemstack);
                        player.inventory.currentItem = slot;
                    }

                    mc.playerController.sendSlotPacket(
                        player.inventory.getStackInSlot(player.inventory.currentItem),
                        player.inventoryContainer.inventorySlots.size() - 9 + player.inventory.currentItem);
                }
            }
            return null;
        }
    }
}
