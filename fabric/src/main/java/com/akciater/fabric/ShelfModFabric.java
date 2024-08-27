package com.akciater.fabric;

import net.fabricmc.api.ModInitializer;

import com.akciater.ShelfModCommon;

public final class ShelfModFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ShelfModCommon.initializeServer();
    }
}
