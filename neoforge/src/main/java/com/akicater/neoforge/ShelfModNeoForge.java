package com.akicater.neoforge;

import com.akciater.ShelfModCommon;
import net.neoforged.fml.common.Mod;

import static com.akciater.ShelfModCommon.*;

@Mod(MODID)
public final class ShelfModNeoForge {
    public ShelfModNeoForge() {
        // Initialization
        ShelfModCommon.initializeServer();
    }
}
