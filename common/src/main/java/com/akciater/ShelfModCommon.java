package com.akciater;

import com.akciater.blocks.FloorShelf;
import com.akciater.blocks.FloorShelfBlockEntity;
import com.akciater.blocks.Shelf;
import com.akciater.blocks.ShelfBlockEntity;
import com.google.common.base.Suppliers;
import dev.architectury.platform.Platform;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

#if MC_VER >= V1_19_4
    import net.minecraft.registry.Registries;
    import dev.architectury.registry.registries.RegistrarManager;
#else
    import dev.architectury.registry.registries.Registries;
    import net.minecraft.util.registry.Registry;
    import net.minecraft.network.Packet;
#endif

import java.util.function.Supplier;

public final class ShelfModCommon {
    public static final String MODID = "shelfmod";
    public static final Logger LOGGER = LoggerFactory.getLogger("shelf-mod");

    #if MC_VER >= V1_19_4
        public static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(MODID));
        public static final Registrar<Block> blocks = MANAGER.get().get(Registries.BLOCK);
        public static final Registrar<Item> items = MANAGER.get().get(Registries.ITEM);
        public static final Registrar<BlockEntityType<?>> blockEntities = MANAGER.get().get(Registries.BLOCK_ENTITY_TYPE);
        #if MC_VER >= V1_20_1
        public static final Registrar<ItemGroup> itemGroups = MANAGER.get().get(Registries.ITEM_GROUP);
        #endif

    #else
        public static final Supplier<Registries> MANAGER = Suppliers.memoize(() -> Registries.get(MODID));
        public static final Registrar<Block> blocks = MANAGER.get().get(Registry.BLOCK);
        public static final Registrar<Item> items = MANAGER.get().get(Registry.ITEM);
        public static final Registrar<BlockEntityType<?>> blockEntities = MANAGER.get().get(Registry.BLOCK_ENTITY_TYPE);
    #endif



    public static RegistrySupplier<Item> ICON;



    public static RegistrySupplier<Block> SHELF_OAK;
    public static RegistrySupplier<Block> FLOOR_SHELF_OAK;

    public static RegistrySupplier<Block> SHELF_ACACIA;
    public static RegistrySupplier<Block> FLOOR_SHELF_ACACIA;

    public static RegistrySupplier<Block> SHELF_BIRCH;
    public static RegistrySupplier<Block> FLOOR_SHELF_BIRCH;

    public static RegistrySupplier<Block> SHELF_DARK_OAK;
    public static RegistrySupplier<Block> FLOOR_SHELF_DARK_OAK;

    public static RegistrySupplier<Block> SHELF_SPRUCE;
    public static RegistrySupplier<Block> FLOOR_SHELF_SPRUCE;

    public static RegistrySupplier<Block> SHELF_JUNGLE;
    public static RegistrySupplier<Block> FLOOR_SHELF_JUNGLE;

    public static RegistrySupplier<Block> SHELF_MANGROVE;
    public static RegistrySupplier<Block> FLOOR_SHELF_MANGROVE;

    public static RegistrySupplier<Block> SHELF_BAMBOO;
    public static RegistrySupplier<Block> FLOOR_SHELF_BAMBOO;

    public static RegistrySupplier<Block> SHELF_WARPED;
    public static RegistrySupplier<Block> FLOOR_SHELF_WARPED;

    public static RegistrySupplier<Block> SHELF_CHERRY;
    public static RegistrySupplier<Block> FLOOR_SHELF_CHERRY;

    public static RegistrySupplier<Block> SHELF_CRIMSON;
    public static RegistrySupplier<Block> FLOOR_SHELF_CRIMSON;

    public static RegistrySupplier<Item> SHELF_ITEM_OAK;
    public static RegistrySupplier<Item> FLOOR_SHELF_ITEM_OAK;

    public static RegistrySupplier<Item> SHELF_ITEM_ACACIA;
    public static RegistrySupplier<Item> FLOOR_SHELF_ITEM_ACACIA;

    public static RegistrySupplier<Item> SHELF_ITEM_BIRCH;
    public static RegistrySupplier<Item> FLOOR_SHELF_ITEM_BIRCH;

    public static RegistrySupplier<Item> SHELF_ITEM_DARK_OAK;
    public static RegistrySupplier<Item> FLOOR_SHELF_ITEM_DARK_OAK;

    public static RegistrySupplier<Item> SHELF_ITEM_SPRUCE;
    public static RegistrySupplier<Item> FLOOR_SHELF_ITEM_SPRUCE;

    public static RegistrySupplier<Item> SHELF_ITEM_JUNGLE;
    public static RegistrySupplier<Item> FLOOR_SHELF_ITEM_JUNGLE;

    public static RegistrySupplier<Item> SHELF_ITEM_MANGROVE;
    public static RegistrySupplier<Item> FLOOR_SHELF_ITEM_MANGROVE;

    public static RegistrySupplier<Item> SHELF_ITEM_BAMBOO;
    public static RegistrySupplier<Item> FLOOR_SHELF_ITEM_BAMBOO;

    public static RegistrySupplier<Item> SHELF_ITEM_WARPED;
    public static RegistrySupplier<Item> FLOOR_SHELF_ITEM_WARPED;

    public static RegistrySupplier<Item> SHELF_ITEM_CHERRY;
    public static RegistrySupplier<Item> FLOOR_SHELF_ITEM_CHERRY;

    public static RegistrySupplier<Item> SHELF_ITEM_CRIMSON;
    public static RegistrySupplier<Item> FLOOR_SHELF_ITEM_CRIMSON;

    public static RegistrySupplier<BlockEntityType<ShelfBlockEntity>> SHELF_BLOCK_ENTITY;
    public static RegistrySupplier<BlockEntityType<FloorShelfBlockEntity>> FLOOR_SHELF_BLOCK_ENTITY;

    public static RegistrySupplier<ItemGroup> SHELF_MOD_TAB;


    public static void initializeServer() {
        ICON = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "icon"), () -> new Item(new Item.Settings()));

        #if MC_VER >= V1_20_1
        SHELF_MOD_TAB = itemGroups.register(
                Identifier.of(MODID, "shelf_mod_tab"),
                () -> CreativeTabRegistry.create(
                        Text.of("Shelf mod"),
                        () -> ICON.get().asItem().getDefaultStack()
                )
        );
        #else
        #if MC_VER == V1_19_4 CreativeTabRegistry.TabSupplier #else ItemGroup #endif SHELF_MOD_TAB = CreativeTabRegistry.create(
                new Identifier(MODID, "shelf_mod_tab"),
                () -> ICON.get().asItem().getDefaultStack()
        );
        #endif

        SHELF_OAK = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_oak"), () -> new Shelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
        FLOOR_SHELF_OAK = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_oak"), () -> new FloorShelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));

        SHELF_ACACIA = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_acacia"), () -> new Shelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
        FLOOR_SHELF_ACACIA = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_acacia"), () -> new FloorShelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));

        SHELF_BIRCH = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_birch"), () -> new Shelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
        FLOOR_SHELF_BIRCH = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_birch"), () -> new FloorShelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));

        SHELF_DARK_OAK = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_dark_oak"), () -> new Shelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
        FLOOR_SHELF_DARK_OAK = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_dark_oak"), () -> new FloorShelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));

        SHELF_SPRUCE = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_spruce"), () -> new Shelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
        FLOOR_SHELF_SPRUCE = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_spruce"), () -> new FloorShelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));

        SHELF_JUNGLE = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_jungle"), () -> new Shelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
        FLOOR_SHELF_JUNGLE = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_jungle"), () -> new FloorShelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));

        SHELF_MANGROVE = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_mangrove"), () -> new Shelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
        FLOOR_SHELF_MANGROVE = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_mangrove"), () -> new FloorShelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));

        SHELF_BAMBOO = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_bamboo"), () -> new Shelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
        FLOOR_SHELF_BAMBOO = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_bamboo"), () -> new FloorShelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));

        SHELF_WARPED = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_warped"), () -> new Shelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
        FLOOR_SHELF_WARPED = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_warped"), () -> new FloorShelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));

        SHELF_CHERRY = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_cherry"), () -> new Shelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
        FLOOR_SHELF_CHERRY = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_cherry"), () -> new FloorShelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));

        SHELF_CRIMSON = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_crimson"), () -> new Shelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));
        FLOOR_SHELF_CRIMSON = blocks.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_crimson"), () -> new FloorShelf(AbstractBlock.Settings.copy(Blocks.OAK_PLANKS).nonOpaque()));


        SHELF_ITEM_OAK = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_item_oak"), () -> new BlockItem(SHELF_OAK.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));
        FLOOR_SHELF_ITEM_OAK = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_item_oak"), () -> new BlockItem(FLOOR_SHELF_OAK.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));

        SHELF_ITEM_ACACIA = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_item_acacia"), () -> new BlockItem(SHELF_ACACIA.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));
        FLOOR_SHELF_ITEM_ACACIA = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_item_acacia"), () -> new BlockItem(FLOOR_SHELF_ACACIA.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));

        SHELF_ITEM_BIRCH = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_item_birch"), () -> new BlockItem(SHELF_BIRCH.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));
        FLOOR_SHELF_ITEM_BIRCH = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_item_birch"), () -> new BlockItem(FLOOR_SHELF_BIRCH.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));

        SHELF_ITEM_DARK_OAK = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_item_dark_oak"), () -> new BlockItem(SHELF_DARK_OAK.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));
        FLOOR_SHELF_ITEM_DARK_OAK = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_item_dark_oak"), () -> new BlockItem(FLOOR_SHELF_DARK_OAK.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));

        SHELF_ITEM_SPRUCE = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_item_spruce"), () -> new BlockItem(SHELF_SPRUCE.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));
        FLOOR_SHELF_ITEM_SPRUCE = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_item_spruce"), () -> new BlockItem(FLOOR_SHELF_SPRUCE.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));

        SHELF_ITEM_JUNGLE = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_item_jungle"), () -> new BlockItem(SHELF_JUNGLE.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));
        FLOOR_SHELF_ITEM_JUNGLE = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_item_jungle"), () -> new BlockItem(FLOOR_SHELF_JUNGLE.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));

        SHELF_ITEM_MANGROVE = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_item_mangrove"), () -> new BlockItem(SHELF_MANGROVE.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));
        FLOOR_SHELF_ITEM_MANGROVE = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_item_mangrove"), () -> new BlockItem(FLOOR_SHELF_MANGROVE.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));

        SHELF_ITEM_BAMBOO = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_item_bamboo"), () -> new BlockItem(SHELF_BAMBOO.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));
        FLOOR_SHELF_ITEM_BAMBOO = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_item_bamboo"), () -> new BlockItem(FLOOR_SHELF_BAMBOO.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));

        SHELF_ITEM_WARPED = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_item_warped"), () -> new BlockItem(SHELF_WARPED.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));
        FLOOR_SHELF_ITEM_WARPED = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_item_warped"), () -> new BlockItem(FLOOR_SHELF_WARPED.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));

        SHELF_ITEM_CHERRY = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_item_cherry"), () -> new BlockItem(SHELF_CHERRY.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));
        FLOOR_SHELF_ITEM_CHERRY = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_item_cherry"), () -> new BlockItem(FLOOR_SHELF_CHERRY.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));

        SHELF_ITEM_CRIMSON = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_item_crimson"), () -> new BlockItem(SHELF_CRIMSON.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));
        FLOOR_SHELF_ITEM_CRIMSON = items.register(#if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_item_crimson"), () -> new BlockItem(FLOOR_SHELF_CRIMSON.get(), new Item.Settings()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB)#endif));


        SHELF_BLOCK_ENTITY = blockEntities.register(
                #if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "shelf_block_entity"),
                () -> BlockEntityType.Builder.create(
                        ShelfBlockEntity::new,
                        SHELF_OAK.get(), SHELF_ACACIA.get(), SHELF_BIRCH.get(), SHELF_DARK_OAK.get(), SHELF_SPRUCE.get(), SHELF_JUNGLE.get(), SHELF_MANGROVE.get(), SHELF_BAMBOO.get(), SHELF_WARPED.get(), SHELF_CHERRY.get(), SHELF_CRIMSON.get()
                ).build(null)
        );

        FLOOR_SHELF_BLOCK_ENTITY = blockEntities.register(
                #if MC_VER >= V1_19_4 Identifier.of #else new Identifier #endif(MODID, "floor_shelf_block_entity"),
                () -> BlockEntityType.Builder.create(
                        FloorShelfBlockEntity::new,
                        FLOOR_SHELF_OAK.get(), FLOOR_SHELF_ACACIA.get(), FLOOR_SHELF_BIRCH.get(), FLOOR_SHELF_DARK_OAK.get(), FLOOR_SHELF_SPRUCE.get(), FLOOR_SHELF_JUNGLE.get(), FLOOR_SHELF_MANGROVE.get(), FLOOR_SHELF_BAMBOO.get(), FLOOR_SHELF_WARPED.get(), FLOOR_SHELF_CHERRY.get(), FLOOR_SHELF_CRIMSON.get()
                ).build(null)
        );
    }
}
