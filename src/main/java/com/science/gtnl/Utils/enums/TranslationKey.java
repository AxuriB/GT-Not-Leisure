package com.science.gtnl.Utils.enums;

import java.util.regex.Pattern;

import com.science.gtnl.Utils.text.TranslationKeyExtension;
import lombok.experimental.ExtensionMethod;
import net.minecraft.util.StatCollector;

import org.jetbrains.annotations.NotNull;

@ExtensionMethod(TranslationKeyExtension.class)
public enum TranslationKey implements CharSequence {

    EXAMPLE_KEY("example.key", "This is the default translation: %s"),

    ANOTHER_KEY("another.key", "Another default translation without format.");

    public final String key;
    public final String defaultTranslation;
    public static final Pattern FORMAT_PATTERN = Pattern
        .compile("%(\\d+\\$)?[-#+ 0,(]*\\d*(\\.\\d+)?[bBhHsScCdoxXeEfgGaAtT]|%%");

    TranslationKey(String key, String defaultTranslation) {
        this.key = key;
        this.defaultTranslation = defaultTranslation;
    }

    public String translate(Object... formatArgs) {
        if (formatArgs == null || formatArgs.length == 0) {
            return StatCollector.translateToLocal(this.key);
        } else {
            return StatCollector.translateToLocalFormatted(this.key, formatArgs);
        }
    }

    public boolean containsFormatPlaceholder() {
        return FORMAT_PATTERN.matcher(defaultTranslation)
            .find();
    }

    @NotNull
    @Override
    public String toString() {
        if (containsFormatPlaceholder()) {
            throw new UnsupportedOperationException("Use translate() method with parameters for this translation key.");
        }
        return StatCollector.translateToLocal(this.key);
    }

    @Override
    public int length() {
        return key.length();
    }

    @Override
    public char charAt(int index) {
        return key.charAt(index);
    }

    @NotNull
    @Override
    public CharSequence subSequence(int start, int end) {
        return key.subSequence(start, end);
    }
}
