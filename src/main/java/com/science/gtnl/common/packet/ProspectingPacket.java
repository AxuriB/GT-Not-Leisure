package com.science.gtnl.common.packet;

import java.util.HashMap;

import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.github.bsideup.jabel.Desugar;
import com.google.common.base.Objects;
import com.science.gtnl.ScienceNotLeisure;
import com.science.gtnl.Utils.detrav.DetravMapTexture;
import com.science.gtnl.Utils.detrav.DetravScannerGUI;

import bartworks.system.material.Werkstoff;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import detrav.utils.FluidColors;
import detrav.utils.GTppHelper;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.Materials;
import gregtech.api.util.GTLanguageManager;
import gtPlusPlus.core.material.Material;
import io.netty.buffer.ByteBuf;

public class ProspectingPacket implements IMessage {

    public int chunkX, chunkZ, posX, posZ, size, ptype;
    public HashMap<Byte, Short>[][] map;
    public HashMap<String, Integer> ores = new HashMap<>();
    public HashMap<Short, String> metaMap = new HashMap<>();

    public ProspectingPacket() {}

    public ProspectingPacket(int chunkX, int chunkZ, int posX, int posZ, int size, int ptype) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.posX = posX;
        this.posZ = posZ;
        this.size = size;
        this.ptype = ptype;
        this.map = new HashMap[(size * 2 + 1) * 16][(size * 2 + 1) * 16];
        this.ores = new HashMap<>();
        this.metaMap = new HashMap<>();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        chunkX = buf.readInt();
        chunkZ = buf.readInt();
        posX = buf.readInt();
        posZ = buf.readInt();
        size = buf.readInt();
        ptype = buf.readInt();

        int len = (size * 2 + 1) * 16;
        map = new HashMap[len][len];
        int total = 0;

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                byte count = buf.readByte();
                if (count == 0) continue;
                map[i][j] = new HashMap<>();
                for (int k = 0; k < count; k++) {
                    byte y = buf.readByte();
                    short meta = buf.readShort();
                    map[i][j].put(y, meta);
                    if (ptype != 2 || y == 1) {
                        addOre(this, y, i, j, meta);
                    }
                    total++;
                }
            }
        }

        int check = buf.readInt();
        if (check != total) {
            map = null; // signal error
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(chunkX);
        buf.writeInt(chunkZ);
        buf.writeInt(posX);
        buf.writeInt(posZ);
        buf.writeInt(size);
        buf.writeInt(ptype);

        int len = (size * 2 + 1) * 16;
        int count = 0;

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                HashMap<Byte, Short> data = map[i][j];
                if (data == null) {
                    buf.writeByte(0);
                } else {
                    buf.writeByte(data.size());
                    for (Byte y : data.keySet()) {
                        buf.writeByte(y);
                        buf.writeShort(data.get(y));
                        count++;
                    }
                }
            }
        }

        buf.writeInt(count);
    }

    public static class Handler implements IMessageHandler<ProspectingPacket, IMessage> {

        @Override
        public IMessage onMessage(ProspectingPacket message, MessageContext ctx) {
            if (message.map == null) return null;

            ProspectingWrapper wrapper = new ProspectingWrapper(
                message.chunkX,
                message.chunkZ,
                message.posX,
                message.posZ,
                message.size,
                message.ptype,
                message.map,
                message.ores,
                message.metaMap);

            DetravScannerGUI.newMap(new DetravMapTexture(wrapper));
            ScienceNotLeisure.proxy.openProspectorGUI();
            return null;
        }
    }

    public static void addOre(ProspectingPacket packet, byte y, int i, int j, short meta) {
        final short[] rgba;
        final String name;
        try {
            if (packet.ptype == 0 || packet.ptype == 1) {
                if (meta < 7000 || meta > 7500) {
                    if (meta > 0) {
                        Materials mat = GregTechAPI.sGeneratedMaterials[meta % 1000];
                        rgba = mat.getRGBA();
                        name = mat.getLocalizedNameForItem(
                            GTLanguageManager.getTranslation("gt.blockores." + meta + ".name"));
                    } else {
                        Werkstoff w = Werkstoff.werkstoffHashMap.get((short) (-meta));
                        name = GTLanguageManager.getTranslation("bw.blocktype.ore")
                            .replace("%material", w.getLocalizedName());
                        rgba = w.getRGBA();
                    }
                } else {
                    Material mat = GTppHelper.getMatFromMeta(meta);
                    rgba = mat.getRGBA();
                    name = mat.getLocalizedName() + " Ore";
                }
            } else if (packet.ptype == 2) {
                rgba = FluidColors.getColor(meta);
                name = Objects.firstNonNull(
                    FluidRegistry.getFluid(meta)
                        .getLocalizedName(new FluidStack(FluidRegistry.getFluid(meta), 0)),
                    StatCollector.translateToLocal("gui.detrav.scanner.unknown_fluid"));
            } else if (packet.ptype == 3) {
                name = StatCollector.translateToLocal("gui.detrav.scanner.pollution");
                rgba = new short[] { 125, 123, 118, 0 };
            } else return;
        } catch (Exception e) {
            return;
        }
        packet.ores.put(name, ((rgba[0] & 0xFF) << 16) + ((rgba[1] & 0xFF) << 8) + ((rgba[2] & 0xFF)));
        packet.metaMap.put(meta, name);
    }

    public void addBlock(int x, int y, int z, short metaData) {
        int aX = x - (chunkX - size) * 16;
        int aZ = z - (chunkZ - size) * 16;
        if (map[aX][aZ] == null) map[aX][aZ] = new HashMap<>();
        map[aX][aZ].put((byte) y, metaData);
    }

    @Desugar
    public record ProspectingWrapper(int chunkX, int chunkZ, int posX, int posZ, int size, int ptype,
        HashMap<Byte, Short>[][] map, HashMap<String, Integer> ores, HashMap<Short, String> metaMap) {}
}
