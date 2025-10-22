package com.science.gtnl.utils;

import appeng.tile.qnb.TileQuantumBridge;

public interface RCQuantumCluster {

    TileQuantumBridge[] r$getRing();

    TileQuantumBridge getCenter();

    void r$setIdlePowerUsage(Object obj, double power);

    boolean r$noWork();
}
