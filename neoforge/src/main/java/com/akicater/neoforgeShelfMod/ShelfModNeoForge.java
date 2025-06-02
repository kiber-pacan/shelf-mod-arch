package com.akicater.neoforgeShelfMod;

import com.akciater.ShelfModCommon;


import com.akciater.blocks.FloorShelf;
import com.akciater.blocks.FloorShelfBlockEntity;
import com.akciater.blocks.ShelfBlockEntity;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;

#if MC_VER >= V1_21_3
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
#endif


@Mod(ShelfModCommon.MODID)
public final class ShelfModNeoForge {
    #if MC_VER >= V1_21_3
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ShelfModCommon.MODID);

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ShelfBlockEntity>> shelfBlockEntity;
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<FloorShelfBlockEntity>> floorShelfBlockEntity;
    #endif

    public ShelfModNeoForge(IEventBus modBus, ModContainer container) {
        #if MC_VER >= V1_21_3
        ShelfModCommon.initializeServer();

        shelfBlockEntity = BLOCK_ENTITY_TYPES.register(
                "shelf_block_entity",
                () -> new BlockEntityType<>(
                        ShelfBlockEntity::new,
                        ShelfModCommon.SHELVES_BLOCK.stream().map(RegistrySupplier::get).toArray(Block[]::new)
                )
        );

        floorShelfBlockEntity = BLOCK_ENTITY_TYPES.register(
                "floor_shelf_block_entity",
                () -> new BlockEntityType<>(
                        FloorShelfBlockEntity::new,
                        ShelfModCommon.FLOOR_SHELVES_BLOCK.stream().map(RegistrySupplier::get).toArray(Block[]::new)
                )
        );

        BLOCK_ENTITY_TYPES.register(modBus);

        modBus.addListener(this::onCommonSetup);

        #else
        ShelfModCommon.initializeServer();
        #endif

    }

    #if MC_VER >= V1_21_3
    private void onCommonSetup(final FMLCommonSetupEvent event) {
        ShelfModCommon.SHELF_BLOCK_ENTITY = shelfBlockEntity.get();
        ShelfModCommon.FLOOR_SHELF_BLOCK_ENTITY = floorShelfBlockEntity.get();
    }
    #endif
}
