package com.science.gtnl.client.nei;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

import com.science.gtnl.Utils.enums.GTNLItemList;
import com.science.gtnl.Utils.enums.ModList;
import com.science.gtnl.Utils.gui.portableWorkbench.GuiPortableAdvancedWorkbench;
import com.science.gtnl.Utils.gui.portableWorkbench.GuiPortableBasicWorkbench;
import com.science.gtnl.Utils.gui.portableWorkbench.GuiPortableFurnace;
import com.science.gtnl.common.material.MaterialPool;
import com.science.gtnl.loader.RecipePool;
import com.science.gtnl.mixins.late.EnhancedLootBags.AccessorItemLootBag;

import bartworks.system.material.Werkstoff;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.DefaultOverlayHandler;
import eu.usrv.enhancedlootbags.EnhancedLootBags;
import eu.usrv.enhancedlootbags.core.LootGroupsHandler;
import eu.usrv.enhancedlootbags.core.serializer.LootGroups;
import gregtech.api.enums.Mods;
import gregtech.api.enums.OrePrefixes;

@SuppressWarnings("unused")
public class NEIGTNLConfig implements IConfigureNEI {

    @Override
    public String getName() {
        return "GTNL NEI Plugin";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public void loadConfig() {
        API.registerGuiOverlay(GuiPortableAdvancedWorkbench.class, "crafting");
        API.registerGuiOverlay(GuiPortableBasicWorkbench.class, "crafting");
        API.registerGuiOverlay(GuiPortableFurnace.class, "smelting");
        API.registerGuiOverlay(GuiPortableFurnace.class, "fuel");

        API.registerGuiOverlayHandler(GuiPortableAdvancedWorkbench.class, new DefaultOverlayHandler(), "crafting");
        API.registerGuiOverlayHandler(GuiPortableBasicWorkbench.class, new DefaultOverlayHandler(), "crafting");

        API.addRecipeCatalyst(GTNLItemList.ShimmerBucket.get(1), RecipePool.ShimmerRecipes.unlocalizedName);
        API.addRecipeCatalyst(GTNLItemList.InfinityShimmerBucket.get(1), RecipePool.ShimmerRecipes.unlocalizedName);
        API.addRecipeCatalyst(GTNLItemList.ShimmerFluidBlock.get(1), RecipePool.ShimmerRecipes.unlocalizedName);

        API.addRecipeCatalyst(GTNLItemList.ReactionFurnace.get(1), "smelting");
        API.addRecipeCatalyst(GTNLItemList.LargeSteamFurnace.get(1), "smelting");
        API.addRecipeCatalyst(GTNLItemList.PortableFurnace.get(1), "smelting");
        API.addRecipeCatalyst(GTNLItemList.PortableBasicWorkBench.get(1), "crafting");
        API.addRecipeCatalyst(GTNLItemList.PortableAdvancedWorkBench.get(1), "crafting");
        API.addRecipeCatalyst(GTNLItemList.LootBagRedemption.get(1), Mods.EnhancedLootBags.ID);

        for (LootGroups.LootGroup tGrp : ((AccessorItemLootBag) LootGroupsHandler.getLootBagItem()).getLGHandler()
            .getLootGroupsClient()
            .getLootTable()) {
            ItemStack loogBag = new ItemStack(LootGroupsHandler.getLootBagItem(), 1, tGrp.getGroupID());
            API.addRecipeCatalyst(loogBag, Mods.EnhancedLootBags.ID);

            if (EnhancedLootBags.ELBCfg.AllowFortuneBags && tGrp.getCombineWithTrash()) {
                ItemStack enhancedLootBag = loogBag.copy();
                enhancedLootBag.addEnchantment(Enchantment.fortune, 3);
                API.addRecipeCatalyst(enhancedLootBag, Mods.EnhancedLootBags.ID);
            }
        }

        Werkstoff[] hiddenMaterials = { MaterialPool.Polyimide, MaterialPool.AcrylonitrileButadieneStyrene,
            MaterialPool.Polyetheretherketone, MaterialPool.HSLASteel, MaterialPool.Actinium,
            MaterialPool.Rutherfordium, MaterialPool.Dubnium, MaterialPool.Seaborgium, MaterialPool.Technetium,
            MaterialPool.Bohrium, MaterialPool.Hassium, MaterialPool.Meitnerium, MaterialPool.Darmstadtium,
            MaterialPool.Roentgenium, MaterialPool.Copernicium, MaterialPool.Moscovium, MaterialPool.Livermorium,
            MaterialPool.Astatine, MaterialPool.Tennessine, MaterialPool.Francium, MaterialPool.Berkelium,
            MaterialPool.Einsteinium, MaterialPool.Mendelevium, MaterialPool.Nobelium, MaterialPool.Lawrencium,
            MaterialPool.Nihonium, MaterialPool.CompressedSteam, MaterialPool.Breel, MaterialPool.Stronze,
            MaterialPool.Periodicium, MaterialPool.Stargate };

        OrePrefixes[] orePrefixes = { OrePrefixes.ingotHot, OrePrefixes.toolHeadSaw, OrePrefixes.toolHeadWrench,
            OrePrefixes.toolHeadHammer };

        for (OrePrefixes ore : orePrefixes) {
            for (Werkstoff mat : hiddenMaterials) {
                API.hideItem(mat.get(ore));
            }
        }

        API.hideItem(GTNLItemList.EternalGregTechWorkshopRender.get(1));
        API.hideItem(GTNLItemList.NanoPhagocytosisPlantRender.get(1));
        API.hideItem(GTNLItemList.ArtificialStarRender.get(1));
        API.hideItem(GTNLItemList.NullPointerException.get(1));
        API.hideItem(GTNLItemList.TwilightSword.get(1));
        API.hideItem(GTNLItemList.FakeItemSiren.get(1));
        API.hideItem(ModList.ScienceNotLeisure.ID + ":" + GTNLItemList.Stick.name());
    }
}
