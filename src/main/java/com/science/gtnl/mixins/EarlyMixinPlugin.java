package com.science.gtnl.mixins;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.science.gtnl.config.MainConfig;

public class EarlyMixinPlugin {

    public static List<String> getEarlyMixins(Set<String> loadedMods) {
        final List<String> mixins = new ArrayList<>();
        mixins.add("Minecraft.AccessorEntityRenderer");
        mixins.add("Minecraft.Entity_Mixin");
        mixins.add("Minecraft.Minecraft_Mixin");
        mixins.add("Minecraft.World_Mixin");
        mixins.add("Minecraft.WorldClient_Mixin");
        mixins.add("Minecraft.WorldServer_Mixin");
        mixins.add("Minecraft.Explosion_Mixin");
        mixins.add("Minecraft.Item_Mixin");
        mixins.add("Forge.FMLProxyPacket_Mixin");
        mixins.add("Forge.ForgeHook_Mixin");

        if (MainConfig.enableSpecialCheatIcon) {
            mixins.add("NotEnoughItems.DrawableBuilderAccessor");
            mixins.add("NotEnoughItems.DrawableResourceAccessor");
            mixins.add("NotEnoughItems.DrawableResource_Mixin");
            mixins.add("NotEnoughItems.LayoutStyleMinecraft_Mixin");
            mixins.add("NotEnoughItems.LayoutManager_Mixin");
        }

        if (MainConfig.enableSuperCreeper) {
            mixins.add("Minecraft.EntityCreeperAccessor");
            mixins.add("Minecraft.EntityAICreeperSwell_Mixin");
            mixins.add("Minecraft.EntityCreeper_Mixin");
            mixins.add("Minecraft.EntitySpider_Mixin");
        }
        return mixins;
    }
}
