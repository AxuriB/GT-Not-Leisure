package com.science.gtnl;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import com.science.gtnl.Utils.SubscribeEventUtils;
import com.science.gtnl.Utils.detrav.DetravScannerGUI;
import com.science.gtnl.Utils.enums.ModList;
import com.science.gtnl.Utils.gui.portableWorkbench.ContainerPortableAdvancedWorkbench;
import com.science.gtnl.Utils.gui.portableWorkbench.ContainerPortableAnvil;
import com.science.gtnl.Utils.gui.portableWorkbench.ContainerPortableBasicWorkbench;
import com.science.gtnl.Utils.gui.portableWorkbench.ContainerPortableCompressedChest;
import com.science.gtnl.Utils.gui.portableWorkbench.ContainerPortableEnchanting;
import com.science.gtnl.Utils.gui.portableWorkbench.ContainerPortableEnderChest;
import com.science.gtnl.Utils.gui.portableWorkbench.ContainerPortableFurnace;
import com.science.gtnl.Utils.gui.portableWorkbench.ContainerPortableInfinityChest;
import com.science.gtnl.Utils.gui.portableWorkbench.GuiPortableAdvancedWorkbench;
import com.science.gtnl.Utils.gui.portableWorkbench.GuiPortableAnvil;
import com.science.gtnl.Utils.gui.portableWorkbench.GuiPortableBasicWorkbench;
import com.science.gtnl.Utils.gui.portableWorkbench.GuiPortableEnchanting;
import com.science.gtnl.Utils.gui.portableWorkbench.GuiPortableEnderChest;
import com.science.gtnl.Utils.gui.portableWorkbench.GuiPortableFurnace;
import com.science.gtnl.Utils.gui.portableWorkbench.GuiPortablePortableCompressedChest;
import com.science.gtnl.Utils.gui.portableWorkbench.GuiPortablePortableInfinityChest;
import com.science.gtnl.Utils.machine.VMTweakHelper;
import com.science.gtnl.Utils.text.PlayerDollWaila;
import com.science.gtnl.common.machine.hatch.SuperCraftingInputHatchME;
import com.science.gtnl.common.packet.NetWorkHandler;
import com.science.gtnl.common.recipe.GTNL.ExtremeExtremeEntityCrusherRecipes;
import com.science.gtnl.config.MainConfig;

import appeng.api.AEApi;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import gregtech.api.enums.Mods;

public class CommonProxy implements IGuiHandler {

    public static final int DetravScannerGUI = 0;
    public static final int PortableBasicWorkBenchGUI = 1;
    public static final int PortableAdvancedWorkBenchGUI = 2;
    public static final int PortableFurnaceGUI = 3;
    public static final int PortableAnvilGUI = 4;
    public static final int PortableEnderChestGUI = 5;
    public static final int PortableEnchantingGUI = 6;
    public static final int PortableCompressedChestGUI = 7;
    public static final int PortableInfinityChestGUI = 8;

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new SubscribeEventUtils());
        if (Mods.MobsInfo.isModLoaded()) {
            MinecraftForge.EVENT_BUS.register(new ExtremeExtremeEntityCrusherRecipes());
        }
        FMLCommonHandler.instance()
            .bus()
            .register(new SubscribeEventUtils());

        NetWorkHandler.registerAllMessage();
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
        PlayerDollWaila.init();
        if (!ModList.VMTweak.isModLoaded() && MainConfig.enableVoidMinerTweak) {
            MinecraftForge.EVENT_BUS.register(new VMTweakHelper());
            FMLCommonHandler.instance()
                .bus()
                .register(new VMTweakHelper());
        }
    }

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
        AEApi.instance()
            .registries()
            .interfaceTerminal()
            .register(SuperCraftingInputHatchME.class);

        // AltarStructure.registerAltarStructureInfo();
    }

    public void completeInit(FMLLoadCompleteEvent event) {}

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == PortableBasicWorkBenchGUI) return new ContainerPortableBasicWorkbench(player, world);
        if (ID == PortableAdvancedWorkBenchGUI)
            return new ContainerPortableAdvancedWorkbench(player.inventory, player.worldObj, player.getHeldItem());
        if (ID == PortableFurnaceGUI)
            return new ContainerPortableFurnace(player.inventory, player.worldObj, player.getHeldItem());
        if (ID == CommonProxy.PortableAnvilGUI) return new ContainerPortableAnvil(player.inventory, player);
        if (ID == CommonProxy.PortableEnderChestGUI)
            return new ContainerPortableEnderChest(player.inventory, player.getInventoryEnderChest());
        if (ID == CommonProxy.PortableEnchantingGUI) return new ContainerPortableEnchanting(player.inventory, world);
        if (ID == CommonProxy.PortableCompressedChestGUI)
            return new ContainerPortableCompressedChest(player.getHeldItem(), player.inventory);
        if (ID == CommonProxy.PortableInfinityChestGUI)
            return new ContainerPortableInfinityChest(player.getHeldItem(), player.inventory);
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == DetravScannerGUI) return new DetravScannerGUI();
        if (ID == PortableBasicWorkBenchGUI) return new GuiPortableBasicWorkbench(player.inventory, world);
        if (ID == PortableAdvancedWorkBenchGUI) return new GuiPortableAdvancedWorkbench(
            new ContainerPortableAdvancedWorkbench(player.inventory, player.worldObj, player.getHeldItem()));
        if (ID == PortableFurnaceGUI) return new GuiPortableFurnace(player.inventory);
        if (ID == CommonProxy.PortableAnvilGUI) return new GuiPortableAnvil(player.inventory, world);
        if (ID == CommonProxy.PortableEnderChestGUI)
            return new GuiPortableEnderChest(player.inventory, player.getInventoryEnderChest());
        if (ID == CommonProxy.PortableEnchantingGUI) return new GuiPortableEnchanting(player.inventory, world);
        if (ID == CommonProxy.PortableCompressedChestGUI)
            return new GuiPortablePortableCompressedChest(player.getHeldItem(), player.inventory);
        if (ID == CommonProxy.PortableInfinityChestGUI)
            return new GuiPortablePortableInfinityChest(player.getHeldItem(), player.inventory);
        return null;
    }

    public void openProspectorGUI() {
        // just Client code
    }
}
