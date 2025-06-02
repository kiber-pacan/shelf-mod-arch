package com.akciater.fabricShelfMod;



import com.akciater.blocks.FloorShelfBlockEntity;
import com.akciater.blocks.ShelfBlockEntity;
import net.fabricmc.api.ModInitializer;

import com.akciater.ShelfModCommon;

#if MC_VER >= V1_21_3
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.resources.ResourceLocation;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.architectury.platform.Platform;
import net.minecraft.world.level.block.Block;
#endif

public final class ShelfModFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ShelfModCommon.initializeServer();

        #if MC_VER >= V1_21_3
        if (!Platform.isForgeLike()) {
            ShelfModCommon.SHELF_BLOCK_ENTITY = ShelfModCommon.blockEntities.register(
                #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(ShelfModCommon.MODID, "shelf_block_entity"),
                    () -> FabricBlockEntityTypeBuilder.create(
                            ShelfBlockEntity::new,
                            ShelfModCommon.SHELVES_BLOCK.stream().map(RegistrySupplier::get).toArray(Block[]::new)
                    ).build()
            ).get();

            ShelfModCommon.FLOOR_SHELF_BLOCK_ENTITY = ShelfModCommon.blockEntities.register(
                #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(ShelfModCommon.MODID, "floor_shelf_block_entity"),
                    () -> FabricBlockEntityTypeBuilder.create(
                            FloorShelfBlockEntity::new,
                            ShelfModCommon.FLOOR_SHELVES_BLOCK.stream().map(RegistrySupplier::get).toArray(Block[]::new)
                    ).build()
            ).get();
        }
        #endif
    }
}
