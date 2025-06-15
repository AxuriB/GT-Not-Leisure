package com.science.gtnl.asm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.commons.Remapper;
import org.objectweb.asm.commons.RemappingClassAdapter;

import codechicken.obfuscator.ObfuscationRun;

public class ObfuscationRunPatcher {

    public static void patchConstructor() {
        try {
            Constructor<? extends ClassVisitor> newCtor = RemappingClassAdapter.class
                .getConstructor(ClassVisitor.class, Remapper.class);

            Field field = ObfuscationRun.class.getDeclaredField("theConstructor");
            field.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(null, newCtor);

            System.out.println("Successfully patched ObfuscationRun.theConstructor to use RemappingClassAdapter.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to patch ObfuscationRun.theConstructor", e);
        }
    }
}
