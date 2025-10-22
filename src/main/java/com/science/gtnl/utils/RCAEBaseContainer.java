package com.science.gtnl.utils;

import net.minecraft.inventory.Container;

public interface RCAEBaseContainer {

    int rc$getInventorySlot();

    boolean rc$isBauble();

    boolean rc$isSpecial();

    void rc$setOldContainer(Container oldContainer);

    Container rc$getOldContainer();
}
