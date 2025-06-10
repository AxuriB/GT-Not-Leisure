package com.science.gtnl.Utils.text;

import net.minecraft.util.StatCollector;

public class TextUtils {

    public static String texter(String aTextLine, String aKey) {

        /**
         * If not in Dev mode , return vanilla forge method directly.
         */
        if (null != StatCollector.translateToLocal(aKey)) {
            return StatCollector.translateToLocal(aKey);
        }
        return "texterError: " + aTextLine;
    }

    /**
     * Auto generate the Key of textLine
     * {@link TextUtils#texter(String aTextLine, String aKey)}
     *
     * @param aTextLine The default String to where you use.
     * @return
     */
    public static String texter(String aTextLine) {
        String aKey = "Auto." + aTextLine + ".text";
        return texter(aTextLine, aKey);
    }
}
