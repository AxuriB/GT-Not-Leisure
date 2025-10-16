package com.science.gtnl.Utils.text;

public class TranslationKeyExtension {

    public static String a(CCC c, Object o) {
        return c.a(o.toString());
    }

    public static class CCC {

        public String test;

        public CCC(String s) {
            test = s;
        }

        public String a(String s) {
            return test;
        }

    }
}
