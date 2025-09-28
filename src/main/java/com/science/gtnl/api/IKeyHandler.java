package com.science.gtnl.api;

public interface IKeyHandler {

    default boolean isShiftDown() {
        return true;
    }
}
