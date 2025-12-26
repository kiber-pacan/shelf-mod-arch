package com.akciater.fabricShelfMod.client;

import com.akciater.client.ber.FloorShelfBER;
import com.akciater.client.ber.ShelfBER;
import net.fabricmc.api.ClientModInitializer;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;


import static com.akciater.ShelfModCommon.FLOOR_SHELF_BLOCK_ENTITY;
import static com.akciater.ShelfModCommon.SHELF_BLOCK_ENTITY;

public final class ShelfModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(SHELF_BLOCK_ENTITY #if MC_VER < V1_21_3 .get() #endif, ShelfBER::new);
        BlockEntityRendererRegistry.register(FLOOR_SHELF_BLOCK_ENTITY #if MC_VER < V1_21_3 .get() #endif, FloorShelfBER::new);
    }
}
