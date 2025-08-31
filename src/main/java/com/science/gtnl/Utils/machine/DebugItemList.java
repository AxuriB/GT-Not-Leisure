package com.science.gtnl.Utils.machine;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import appeng.api.config.FuzzyMode;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.storage.data.IItemList;

public class DebugItemList implements IItemList<IAEItemStack> {

    private final IItemList<IAEItemStack> delegate;

    public DebugItemList(IItemList<IAEItemStack> real) {
        this.delegate = real;
    }

    private void log(String method, Object... args) {
        System.out.println("[DebugItemList] 调用 " + method);
        if (args != null && args.length > 0) {
            System.out.println("  参数: " + Arrays.toString(args));
        }
        for (StackTraceElement ste : Thread.currentThread()
            .getStackTrace()) {
            System.out.println("    at " + ste);
        }
    }

    @Override
    public void add(IAEItemStack item) {
        log("add", item);
        delegate.add(item);
    }

    @Override
    public IAEItemStack findPrecise(IAEItemStack item) {
        log("findPrecise", item);
        return delegate.findPrecise(item);
    }

    @Override
    public Collection<IAEItemStack> findFuzzy(IAEItemStack input, FuzzyMode fuzzy) {
        log("findFuzzy", input, fuzzy);
        return delegate.findFuzzy(input, fuzzy);
    }

    @Override
    public boolean isEmpty() {
        log("isEmpty");
        return delegate.isEmpty();
    }

    @Override
    public void addStorage(IAEItemStack option) {
        log("addStorage", option);
        delegate.addStorage(option);
    }

    @Override
    public void addCrafting(IAEItemStack option) {
        log("addCrafting", option);
        delegate.addCrafting(option);
    }

    @Override
    public void addRequestable(IAEItemStack option) {
        log("addRequestable", option);
        delegate.addRequestable(option);
    }

    @Override
    public IAEItemStack getFirstItem() {
        log("getFirstItem");
        return delegate.getFirstItem();
    }

    @Override
    public int size() {
        log("size");
        return delegate.size();
    }

    @Override
    public Iterator<IAEItemStack> iterator() {
        log("iterator");
        return delegate.iterator();
    }

    @Override
    public void forEach(Consumer<? super IAEItemStack> action) {
        log("forEach", action);
        delegate.forEach(action);
    }

    @Override
    public Spliterator<IAEItemStack> spliterator() {
        log("spliterator");
        return delegate.spliterator();
    }

    @Override
    public void resetStatus() {
        log("resetStatus");
        delegate.resetStatus();
    }

    @Override
    public IAEItemStack[] toArray(IAEItemStack[] zeroSizedArray) {
        log("toArray", (Object) zeroSizedArray);
        return delegate.toArray(zeroSizedArray);
    }
}
