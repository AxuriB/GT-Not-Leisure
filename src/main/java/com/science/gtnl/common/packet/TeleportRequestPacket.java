package com.science.gtnl.common.packet;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class TeleportRequestPacket implements IMessage {

    private int worldX, worldZ;

    public TeleportRequestPacket() {}

    public TeleportRequestPacket(int x, int z) {
        this.worldX = x;
        this.worldZ = z;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(worldX);
        buf.writeInt(worldZ);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        worldX = buf.readInt();
        worldZ = buf.readInt();
    }

    public static class Handler implements IMessageHandler<TeleportRequestPacket, IMessage> {

        @Override
        public IMessage onMessage(TeleportRequestPacket message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            WorldServer world = player.getServerForPlayer();

            int x = message.worldX;
            int z = message.worldZ;

            int y = world.getTopSolidOrLiquidBlock(x, z);
            player.setPositionAndUpdate(x + 0.5, y + 1, z + 0.5);
            return null;
        }
    }
}
