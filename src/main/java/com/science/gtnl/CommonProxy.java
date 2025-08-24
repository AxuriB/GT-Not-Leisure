package com.science.gtnl;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import com.science.gtnl.Utils.SubscribeEventUtils;
import com.science.gtnl.Utils.detrav.DetravScannerGUI;
import com.science.gtnl.Utils.enums.ModList;
import com.science.gtnl.Utils.gui.portableWorkbench.ContainerAdvancedWorkbench;
import com.science.gtnl.Utils.gui.portableWorkbench.ContainerBasicWorkbench;
import com.science.gtnl.Utils.gui.portableWorkbench.ContainerPortableFurnace;
import com.science.gtnl.Utils.gui.portableWorkbench.GuiAdvancedWorkbench;
import com.science.gtnl.Utils.gui.portableWorkbench.GuiBasicWorkbench;
import com.science.gtnl.Utils.gui.portableWorkbench.GuiPortableFurnace;
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
        if (ID == PortableBasicWorkBenchGUI) return new ContainerBasicWorkbench(player, world);
        if (ID == PortableAdvancedWorkBenchGUI)
            return new ContainerAdvancedWorkbench(player.inventory, player.worldObj, player.getHeldItem());
        if (ID == PortableFurnaceGUI)
            return new ContainerPortableFurnace(player.inventory, player.worldObj, player.getHeldItem());
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == DetravScannerGUI) return new DetravScannerGUI();
        if (ID == PortableBasicWorkBenchGUI) return new GuiBasicWorkbench(player.inventory, world);
        if (ID == PortableAdvancedWorkBenchGUI) return new GuiAdvancedWorkbench(
            new ContainerAdvancedWorkbench(player.inventory, player.worldObj, player.getHeldItem()));
        if (ID == PortableFurnaceGUI) return new GuiPortableFurnace(player.inventory);
        return null;
    }

    public void openProspectorGUI() {
        // just Client code
    }
}
