package com.akciater.fabric.client;

import com.akciater.fabric.client.ber.FloorShelfBER_Fabric;
import com.akciater.fabric.client.ber.ShelfBER_Fabric;
import com.akciater.fabric.client.config.ShelfModConfigFabric;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

import static com.akciater.ShelfModCommon.FLOOR_SHELF_BLOCK_ENTITY;
import static com.akciater.ShelfModCommon.SHELF_BLOCK_ENTITY;

public final class ShelfModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        AutoConfig.register(ShelfModConfigFabric.class, Toml4jConfigSerializer::new);
        BlockEntityRendererRegistry.register(SHELF_BLOCK_ENTITY.get(), ShelfBER_Fabric::new);
        BlockEntityRendererRegistry.register(FLOOR_SHELF_BLOCK_ENTITY.get(), FloorShelfBER_Fabric::new);
    }
}
