package com.science.gtnl.common.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;

public class StatusMessage implements IMessage, IMessageHandler<StatusMessage, IMessage> {

    private IChatComponent chat;

    public StatusMessage() {

    }

    public StatusMessage(IChatComponent chat) {
        this.chat = chat;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.chat = IChatComponent.Serializer.func_150699_a(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, IChatComponent.Serializer.func_150696_a(this.chat));
    }

    @Override
    public IMessage onMessage(StatusMessage message, MessageContext ctx) {
        if (ctx.side.isClient()) sendStatusMessage(message.chat);
        return null;
    }

    @SideOnly(Side.CLIENT)
    public void sendStatusMessage(IChatComponent chat) {
        Minecraft.getMinecraft().ingameGUI.func_110326_a(chat.getFormattedText(), true);
    }
}
