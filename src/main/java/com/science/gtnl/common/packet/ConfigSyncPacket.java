package com.science.gtnl.common.packet;

import com.science.gtnl.config.ConfigData;
import com.science.gtnl.config.MainConfig;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class ConfigSyncPacket implements IMessage {

    public ConfigData data = new ConfigData();

    public ConfigSyncPacket() {
        data.readFromConfig();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        data.write(buf);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        data.read(buf);
    }

    public static class Handler implements IMessageHandler<ConfigSyncPacket, IMessage> {

        @Override
        public IMessage onMessage(ConfigSyncPacket msg, MessageContext ctx) {
            MainConfig.reloadConfig();
            msg.data.writeToConfig();
            return null;
        }
    }
}
