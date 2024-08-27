package com.akicater.quilt.client;

import com.akicater.quilt.client.ber.FloorShelfBER_Quilt;
import com.akicater.quilt.client.ber.ShelfBER_Quilt;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.fabricmc.api.ClientModInitializer;

import static com.akciater.ShelfModCommon.FLOOR_SHELF_BLOCK_ENTITY;
import static com.akciater.ShelfModCommon.SHELF_BLOCK_ENTITY;

public final class ShelfModQuiltClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(SHELF_BLOCK_ENTITY.get(), ShelfBER_Quilt::new);
        BlockEntityRendererRegistry.register(FLOOR_SHELF_BLOCK_ENTITY.get(), FloorShelfBER_Quilt::new);
    }
}
