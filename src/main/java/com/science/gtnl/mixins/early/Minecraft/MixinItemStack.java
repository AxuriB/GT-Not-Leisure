package com.science.gtnl.mixins.early.Minecraft;

import static net.minecraft.item.ItemStack.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.ForgeEventFactory;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Multimap;
import com.science.gtnl.common.item.items.Stick;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {

    @Shadow
    private Item field_151002_e;

    @Shadow
    int itemDamage;

    @Inject(method = "getTooltip", at = @At("HEAD"), cancellable = true)
    private void onGetTooltip(EntityPlayer player, boolean advanced, CallbackInfoReturnable<List<String>> cir) {
        ItemStack self = (ItemStack) (Object) this;
        ArrayList<String> arraylist = new ArrayList<>();
        String s = self.getDisplayName();

        if (self.hasDisplayName()) {
            s = EnumChatFormatting.ITALIC + s + EnumChatFormatting.RESET;
        }

        int i;

        int fakeMeta = -1;
        int fakeID = -1;

        if (field_151002_e instanceof Stick && self.hasTagCompound() && !Stick.isShiftDown()) {
            if (self.getTagCompound()
                .hasKey("ID")) {
                Item item = (Item) Item.itemRegistry.getObject(
                    self.getTagCompound()
                        .getString("ID"));
                if (item != null) {
                    fakeID = Item.getIdFromItem(item);
                }
            }
            if (self.getTagCompound()
                .hasKey("Meta")) {
                fakeMeta = self.getTagCompound()
                    .getInteger("Meta");
            }
        }

        if (advanced) {
            String advIDMeta = "";

            if (!s.isEmpty()) {
                s = s + " (";
                advIDMeta = ")";
            }

            i = Item.getIdFromItem(this.field_151002_e);

            if (self.getHasSubtypes()) {
                s = s + String.format(
                    "#%04d/%d%s",
                    fakeID >= 0 ? fakeID : i,
                    fakeMeta >= 0 ? fakeMeta : this.itemDamage,
                    advIDMeta);
            } else {
                s = s + String.format("#%04d%s", fakeID >= 0 ? fakeID : i, advIDMeta);
            }
        } else if (!self.hasDisplayName() && this.field_151002_e == Items.filled_map) {
            s = s + " #" + (fakeMeta >= 0 ? fakeMeta : this.itemDamage);
        }

        arraylist.add(s);
        this.field_151002_e.addInformation(self, player, arraylist, advanced);

        if (self.hasTagCompound()) {
            NBTTagList nbttaglist = self.getEnchantmentTagList();

            if (nbttaglist != null) {
                for (i = 0; i < nbttaglist.tagCount(); ++i) {
                    short short1 = nbttaglist.getCompoundTagAt(i)
                        .getShort("id");
                    short short2 = nbttaglist.getCompoundTagAt(i)
                        .getShort("lvl");

                    if (Enchantment.enchantmentsList[short1] != null) {
                        arraylist.add(Enchantment.enchantmentsList[short1].getTranslatedName(short2));
                    }
                }
            }

            if (self.stackTagCompound.hasKey("display", 10)) {
                NBTTagCompound nbttagcompound = self.stackTagCompound.getCompoundTag("display");

                if (nbttagcompound.hasKey("color", 3)) {
                    if (advanced) {
                        arraylist.add(
                            "Color: #" + Integer.toHexString(nbttagcompound.getInteger("color"))
                                .toUpperCase());
                    } else {
                        arraylist.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.dyed"));
                    }
                }

                if (nbttagcompound.func_150299_b("Lore") == 9) {
                    NBTTagList nbttaglist1 = nbttagcompound.getTagList("Lore", 8);

                    if (nbttaglist1.tagCount() > 0) {
                        for (int j = 0; j < nbttaglist1.tagCount(); ++j) {
                            arraylist.add(
                                EnumChatFormatting.DARK_PURPLE + ""
                                    + EnumChatFormatting.ITALIC
                                    + nbttaglist1.getStringTagAt(j));
                        }
                    }
                }
            }
        }

        Multimap<String, AttributeModifier> multimap = self.getAttributeModifiers();

        if (!multimap.isEmpty()) {
            arraylist.add("");

            for (Map.Entry<String, AttributeModifier> entry : multimap.entries()) {
                AttributeModifier attributemodifier = entry.getValue();
                double d0 = attributemodifier.getAmount();

                if (attributemodifier.getID() == UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF")) {
                    d0 += EnchantmentHelper.func_152377_a(self, EnumCreatureAttribute.UNDEFINED);
                }

                double d1;

                if (attributemodifier.getOperation() != 1 && attributemodifier.getOperation() != 2) {
                    d1 = d0;
                } else {
                    d1 = d0 * 100.0D;
                }

                if (d0 > 0.0D) {
                    arraylist.add(
                        EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted(
                            "attribute.modifier.plus." + attributemodifier.getOperation(),
                            new Object[] { field_111284_a.format(d1),
                                StatCollector.translateToLocal("attribute.name." + entry.getKey()) }));
                } else if (d0 < 0.0D) {
                    d1 *= -1.0D;
                    arraylist.add(
                        EnumChatFormatting.RED + StatCollector.translateToLocalFormatted(
                            "attribute.modifier.take." + attributemodifier.getOperation(),
                            new Object[] { field_111284_a.format(d1),
                                StatCollector.translateToLocal("attribute.name." + entry.getKey()) }));
                }
            }
        }

        if (self.hasTagCompound() && self.getTagCompound()
            .getBoolean("Unbreakable")) {
            arraylist.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("item.unbreakable"));
        }

        if (advanced && self.isItemDamaged()) {
            arraylist.add(
                "Durability: " + (self.getMaxDamage() - self.getItemDamageForDisplay()) + " / " + self.getMaxDamage());
        }
        ForgeEventFactory.onItemTooltip(self, player, arraylist, advanced);

        cir.setReturnValue(arraylist);
        cir.cancel();
    }
}
