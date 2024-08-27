package com.akicater.quilt;

import com.akciater.ShelfModCommon;
import org.quiltmc.loader.api.ModContainer;
#if MC_VER >= V1_21
import net.fabricmc.api.ModInitializer;
#else
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
#endif


public final class ShelfModQuilt implements ModInitializer {
    @Override
    public void onInitialize(#if MC_VER < V1_21 ModContainer modContainer #endif) {
        ShelfModCommon.initializeServer();

    }
}
