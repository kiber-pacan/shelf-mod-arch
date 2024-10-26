package com.akciater.fabric.client;

import com.akciater.client.ber.FloorShelfBER;
import com.akciater.client.ber.ShelfBER;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.fabricmc.api.ClientModInitializer;

import static com.akciater.ShelfModCommon.FLOOR_SHELF_BLOCK_ENTITY;
import static com.akciater.ShelfModCommon.SHELF_BLOCK_ENTITY;

public final class ShelfModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(SHELF_BLOCK_ENTITY.get(), ShelfBER::new);
        BlockEntityRendererRegistry.register(FLOOR_SHELF_BLOCK_ENTITY.get(), FloorShelfBER::new);
    }
}
