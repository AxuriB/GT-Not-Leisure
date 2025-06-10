package com.science.gtnl.Utils.machine.EdenGardenManager;

import static gregtech.api.enums.Mods.ThaumicBases;

import com.science.gtnl.Utils.machine.EdenGardenManager.buckets.EIGFlowerBucket;
import com.science.gtnl.Utils.machine.EdenGardenManager.buckets.EIGIC2Bucket;
import com.science.gtnl.Utils.machine.EdenGardenManager.buckets.EIGRainbowCactusBucket;
import com.science.gtnl.Utils.machine.EdenGardenManager.buckets.EIGSeedBucket;
import com.science.gtnl.Utils.machine.EdenGardenManager.buckets.EIGStemBucket;

public class EIGBucketLoader {

    public static void LoadEIGBuckets() {
        // IC2 buckets
        EIGModes.IC2.addLowPriorityFactory(EIGIC2Bucket.factory);

        // Regular Mode Buckets
        if (ThaumicBases.isModLoaded()) {
            EIGModes.Normal.addLowPriorityFactory(EIGRainbowCactusBucket.factory);
        }
        EIGModes.Normal.addLowPriorityFactory(EIGFlowerBucket.factory);
        EIGModes.Normal.addLowPriorityFactory(EIGStemBucket.factory);
        EIGModes.Normal.addLowPriorityFactory(EIGSeedBucket.factory);
    }

}
