package com.science.gtnl.Utils.text;

import net.minecraft.util.StatCollector;

public class TextUtils {

    public static String texter(String aTextLine, String aKey) {

        if (StatCollector.translateToLocal(aKey) != null) {
            return StatCollector.translateToLocal(aKey);
        }
        return "texterError: " + aTextLine;
    }

    public static String insertBlocksAfterColon(String original) {
        int colonIndex = original.indexOf(':');
        if (colonIndex != -1) {
            return original.substring(0, colonIndex + 1) + "blocks/" + original.substring(colonIndex + 1);
        }
        return "blocks/" + original;
    }
}
