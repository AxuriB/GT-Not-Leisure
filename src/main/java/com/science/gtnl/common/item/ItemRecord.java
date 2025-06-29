package com.science.gtnl.common.item;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;

import net.minecraft.util.ResourceLocation;

import com.science.gtnl.client.GTNLCreativeTabs;

public class ItemRecord extends net.minecraft.item.ItemRecord {

    public ItemRecord(String recordName) {
        super(recordName);
        setTextureName(RESOURCE_ROOT_ID + ":" + "Record." + recordName);
        setUnlocalizedName("record");
        setCreativeTab(GTNLCreativeTabs.GTNotLeisureItem);
    }

    @Override
    public ResourceLocation getRecordResource(String name) {
        return new ResourceLocation(RESOURCE_ROOT_ID + ":" + recordName);
    }

}
