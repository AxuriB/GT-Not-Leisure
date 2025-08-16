package com.science.gtnl.mixins.early.Minecraft;

import java.util.Random;

import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.science.gtnl.common.item.TimeStopManager;

import Forge.NullPointerException;

@Mixin(value = Entity.class)
public class Entity_Mixin {

    @Inject(method = "moveEntity", at = @At("HEAD"), cancellable = true)
    public void mixin$moveEntity(double x, double y, double z, CallbackInfo ci) {
        if (TimeStopManager.isTimeStopped() && !(((Entity) ((Object) this)) instanceof EntityPlayer)) {
            ci.cancel();
        }
    }

    @Inject(method = "addEntityCrashInfo", at = @At("HEAD"), cancellable = true)
    public void addEntityCrashInfo(CrashReportCategory category, CallbackInfo ci) {
        Entity entity = (Entity) ((Object) this);

        if (entity instanceof NullPointerException) {

            category.addCrashSection("Entity Type", randomError() + " (" + randomError() + ")");
            category.addCrashSection("Entity ID", randomError());
            category.addCrashSection("Entity Name", randomError());
            category.addCrashSection(
                "Entity's Exact location",
                randomError() + ", " + randomError() + ", " + randomError());
            category.addCrashSection(
                "Entity's Block location",
                "World: (" + randomError()
                    + ","
                    + randomError()
                    + ","
                    + randomError()
                    + "), "
                    + "Chunk: (at "
                    + randomError()
                    + ","
                    + randomError()
                    + ","
                    + randomError()
                    + " in "
                    + randomError()
                    + ","
                    + randomError()
                    + "; contains blocks "
                    + randomError()
                    + ","
                    + randomError()
                    + ","
                    + randomError()
                    + " to "
                    + randomError()
                    + ","
                    + randomError()
                    + ","
                    + randomError()
                    + "), "
                    + "Region: ("
                    + randomError()
                    + ","
                    + randomError()
                    + "; contains chunks "
                    + randomError()
                    + ","
                    + randomError()
                    + " to "
                    + randomError()
                    + ","
                    + randomError()
                    + ", blocks "
                    + randomError()
                    + ","
                    + randomError()
                    + ","
                    + randomError()
                    + " to "
                    + randomError()
                    + ","
                    + randomError()
                    + ","
                    + randomError()
                    + ")");
            category.addCrashSection("Entity's Momentum", randomError() + ", " + randomError() + ", " + randomError());

            ci.cancel();
        }
    }

    @Unique
    private String[] errors = { "ArrayIndexOutOfBoundsException", "ArrayStoreException", "BootstrapMethodError",
        "IllegalThreadStateException", "IllegalArgumentException", "ArithmeticException", "ClassCastException",
        "IllegalStateException", "UnsupportedOperationException", "NumberFormatException", "IndexOutOfBoundsException",
        "NullPointerException", "NoSuchMethodException", "ClassNotFoundException", "ConcurrentModificationException",
        "StackOverflowError", "OutOfMemoryError", "RuntimeException", "SecurityException",
        "StringIndexOutOfBoundsException", "ThreadDeath", "TypeNotPresentException", "UnknownError",
        "VirtualMachineError", "VerifyError", "AbstractMethodError" };

    @Unique
    private String randomError() {
        return "java.lang." + errors[new Random().nextInt(errors.length)];
    }
}
