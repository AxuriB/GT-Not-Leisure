package com.science.gtnl.Utils.text;

import net.minecraft.util.StatCollector;

public class TextUtils {

    public static String texter(String aTextLine, String aKey) {

        if (StatCollector.translateToLocal(aKey) != null) {
            return StatCollector.translateToLocal(aKey);
        }
        return "texterError: " + aTextLine;
    }
}
