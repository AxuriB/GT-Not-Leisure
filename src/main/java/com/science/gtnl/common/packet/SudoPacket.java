package com.science.gtnl.common.packet;

import net.minecraft.client.Minecraft;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class SudoPacket implements IMessage {

    public String message;

    public SudoPacket() {}

    public SudoPacket(String message) {
        this.message = message;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int length = buf.readInt();
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);
        this.message = new String(bytes);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        byte[] bytes = message.getBytes();
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }

    public static class Handler implements IMessageHandler<SudoPacket, IMessage> {

        @Override
        public IMessage onMessage(SudoPacket message, MessageContext ctx) {
            if (ctx.side.isClient()) {
                Minecraft mc = Minecraft.getMinecraft();
                mc.thePlayer.sendChatMessage(message.message);
            }
            return null;
        }
    }
}
