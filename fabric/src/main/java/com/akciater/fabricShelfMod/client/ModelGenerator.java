package com.akciater.fabricShelfMod.client;

#if MC_VER > 100

import com.akciater.ShelfModCommon;
import dev.architectury.registry.registries.RegistrySupplier;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.apache.logging.log4j.LogManager;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

#if MC_VER <= V1_21_3
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
#else
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
#endif

public class ModelGenerator extends FabricModelProvider {
    public ModelGenerator(FabricDataOutput temp) {
        super(
                new FabricDataOutput(
                        temp.getModContainer(),
                        temp.getOutputFolder().getParent().getParent().getParent().getParent().resolve("common/src/main/resources"),
                        false
                )
        );
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        List<RegistrySupplier<Block>> shelfBlocks = ShelfModCommon.SHELVES_BLOCK;
        List<RegistrySupplier<Block>> floorShelfBlocks = ShelfModCommon.FLOOR_SHELVES_BLOCK;


        blockStateModelGenerator #if MC_VER <= V1_21_3 .delegateItemModel #else .registerSimpleItemModel #endif(
                ShelfModCommon.ICON.get(),
                BuiltInRegistries.BLOCK.getKey(shelfBlocks.get(0).get()).withPrefix("block/")
        );

        for (int i = 0; i < shelfBlocks.size(); i++) {
            blockStateModelGenerator#if MC_VER <= V1_21_3 .delegateItemModel #else .registerSimpleItemModel #endif(
                    shelfBlocks.get(i).get(),
                    BuiltInRegistries.BLOCK.getKey(shelfBlocks.get(i).get()).withPrefix("block/")
            );
        }

        for (int i = 0; i < floorShelfBlocks.size(); i++) {
            blockStateModelGenerator#if MC_VER <= V1_21_3 .delegateItemModel #else .registerSimpleItemModel #endif(
                    floorShelfBlocks.get(i).get(),
                    BuiltInRegistries.BLOCK.getKey(floorShelfBlocks.get(i).get()).withPrefix("block/")
            );
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
    }
}

#endif
