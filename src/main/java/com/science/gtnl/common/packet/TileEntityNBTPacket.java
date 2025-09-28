package com.science.gtnl.common.packet;

import net.minecraft.nbt.NBTTagCompound;

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
            TileEntityNBTPacketHandler.apply(message.getBlockId(), message.getMetadata(), message.getNbt());
            return null;
        }
    }
}
