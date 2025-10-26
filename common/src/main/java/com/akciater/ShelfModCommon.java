package com.akciater;

import com.akciater.blocks.FloorShelf;
import com.akciater.blocks.FloorShelfBlockEntity;
import com.akciater.blocks.Shelf;
import com.akciater.blocks.ShelfBlockEntity;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


#if MC_VER >= V1_18_2 import com.mojang.logging.LogUtils; #endif
import dev.architectury.platform.Platform;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.ReloadListenerRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;





import org.apache.logging.log4j.LogManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;

#if MC_VER <= V1_19_4
import net.minecraft.world.level.material.Material;
#endif

#if MC_VER > V1_19_4
import net.minecraft.world.level.material.MapColor;
#endif

#if MC_VER >= V1_19_4
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.core.registries.Registries;
import org.spongepowered.include.com.google.common.io.Resources;
#else
import dev.architectury.registry.registries.Registries;
import net.minecraft.core.Registry;
#endif

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import java.util.logging.Logger;

public final class ShelfModCommon {
    public static final String MODID = "shelfmod";


    #if MC_VER >= V1_19_4
        public static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(MODID));
        public static final Registrar<Block> blocks = MANAGER.get().get(Registries.BLOCK);
        public static final Registrar<Item> items = MANAGER.get().get(Registries.ITEM);
        public static final Registrar<BlockEntityType<?>> blockEntities = MANAGER.get().get(Registries.BLOCK_ENTITY_TYPE);
        #if MC_VER >= V1_20_1
        public static final Registrar<CreativeModeTab> itemGroups = MANAGER.get().get(Registries.CREATIVE_MODE_TAB);
        #endif

    #else
        public static final Supplier<Registries> MANAGER = Suppliers.memoize(() -> Registries.get(MODID));
        public static final Registrar<Block> blocks = MANAGER.get().get(Registry.BLOCK_REGISTRY);
        public static final Registrar<Item> items = MANAGER.get().get(Registry.ITEM_REGISTRY);
        public static final Registrar<BlockEntityType<?>> blockEntities = MANAGER.get().get(Registry.BLOCK_ENTITY_TYPE_REGISTRY);
    #endif




    public static RegistrySupplier<Item> ICON;
    public static #if MC_VER >= V1_21_3 BlockEntityType<ShelfBlockEntity> #else RegistrySupplier<BlockEntityType<ShelfBlockEntity>> #endif SHELF_BLOCK_ENTITY;
    public static #if MC_VER >= V1_21_3 BlockEntityType<FloorShelfBlockEntity> #else RegistrySupplier<BlockEntityType<FloorShelfBlockEntity>> #endif FLOOR_SHELF_BLOCK_ENTITY;

    public static #if MC_VER == V1_19_4 CreativeTabRegistry.TabSupplier #elif MC_VER <= V1_19_2 CreativeModeTab #else RegistrySupplier<CreativeModeTab> #endif SHELF_MOD_TAB;

    public static List<RegistrySupplier<Block>> SHELVES_BLOCK = new ArrayList<>();
    public static List<RegistrySupplier<Item>> SHELVES_ITEM = new ArrayList<>();

    public static List<RegistrySupplier<Block>> FLOOR_SHELVES_BLOCK = new ArrayList<>();
    public static List<RegistrySupplier<Item>> FLOOR_SHELVES_ITEM = new ArrayList<>();

    public static List<JsonObject> SHELVES_JSON;

    public static boolean isShelf(Item item) {
        for (RegistrySupplier<Item> shelf : SHELVES_ITEM) {
            if (shelf.get() == item) return true;
        }

        for (RegistrySupplier<Item> shelf : FLOOR_SHELVES_ITEM) {
            if (shelf.get() == item) return true;
        }

        return false;
    }

    public static JsonObject createShapedRecipeJson(
            ArrayList<Character> keys,
            ArrayList<ResourceLocation> items,
            ArrayList<String> type,
            ArrayList<String> pattern,
            ResourceLocation output) {

        JsonObject json = new JsonObject();

        json.addProperty("type", "minecraft:crafting_shaped");

        json.addProperty("category", "misc");

        JsonArray jsonArray = new JsonArray();
        for (String line : pattern) {
            jsonArray.add(line);
        }
        json.add("pattern", jsonArray);

        JsonObject keyList = new JsonObject();
        for (int i = 0; i < keys.size(); i++) {
        #if MC_VER >= V1_21_3
            keyList.addProperty(keys.get(i).toString(), items.get(i).toString());
        #else
            JsonObject individualKey = new JsonObject();
            individualKey.addProperty(type.get(i), items.get(i).toString());
            keyList.add(keys.get(i).toString(), individualKey);
        #endif
        }
        json.add("key", keyList);

        JsonObject result = new JsonObject();

        result.addProperty(#if MC_VER > V1_20_4 "id" #else "item" #endif, output.toString());
        result.addProperty("count", 1);
        json.add("result", result);

        return json;
    }

    public static JsonObject createShelfRecipe(String material) {
        ResourceLocation shelf = #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_item_" + material);

        return createShapedRecipeJson(
                Lists.newArrayList(
                        '#',
                        '_'
                ), //The keys we are using for the input items/tags.
                Lists.newArrayList(#if MC_VER >= V1_21 ResourceLocation.parse #else new ResourceLocation #endif(material + "_fence"), #if MC_VER >= V1_21 ResourceLocation.parse #else new ResourceLocation #endif(material + "_slab")), //The items/tags we are using as input.
                Lists.newArrayList("item", "item"), //Whether the input we provided is a tag or an item.
                Lists.newArrayList(
                        "___",
                        "# #",
                        "___"
                ), //The crafting pattern.
                shelf
        );
    }

    public static JsonObject createFloorShelfRecipe(String material) {
        ResourceLocation shelf = #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_item_" + material);

        return createShapedRecipeJson(
                Lists.newArrayList(
                        '#',
                        '@'
                ), //The keys we are using for the input items/tags.
                Lists.newArrayList(#if MC_VER >= V1_21 ResourceLocation.parse #else new ResourceLocation #endif(material + "_fence"), #if MC_VER >= V1_21 ResourceLocation.parse #else new ResourceLocation #endif(material + "_planks")), //The items/tags we are using as input.
                Lists.newArrayList("item", "item"), //Whether the input we provided is a tag or an item.
                Lists.newArrayList(
                        "@#@",
                        "#@#",
                        "@#@"
                ), //The crafting pattern.
                shelf
        );
    }

    public static List<String> MATERIALS = List.of(
            "oak",
            "acacia",
            "birch",
            "dark_oak",
            "spruce",
            "jungle",
            #if MC_VER >= V1_19_2 "mangrove",#endif
            #if MC_VER >= V1_19_4 "bamboo", #endif
            "warped",
            #if MC_VER >= V1_19_4 "cherry", #endif
            #if MC_VER >= V1_21_4 "pale_oak", #endif
            "crimson"
    );

    public static void registerShelves() {
        for (int i = 0; i < MATERIALS.size(); ++i) {
            ResourceLocation location = #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_" + MATERIALS.get(i));
            #if MC_VER >= V1_21_3 ResourceKey<Block> shelf_key = ResourceKey.create(Registries.BLOCK, location); #endif

            RegistrySupplier<Block> shelf = blocks.register(
                    location,
                    () -> new Shelf(
                            BlockBehaviour.Properties.of(#if MC_VER <= V1_19_4 Material.WOOD #endif).strength(0.5F, 3.0F).sound(SoundType.WOOD)
                    )
            );

            SHELVES_BLOCK.add(shelf);

            ResourceLocation itemLocation = #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_item_" + MATERIALS.get(i));
            #if MC_VER >= V1_21_3 ResourceKey<Item> shelf_item_key = ResourceKey.create(Registries.ITEM, itemLocation); #endif

            RegistrySupplier<Item> shelf_item = items.register(
                    itemLocation,
                    () -> new BlockItem(
                            shelf.get(),
                            new Item.Properties()
                                    #if MC_VER >= V1_19_4
                                    .arch$tab(SHELF_MOD_TAB)
                                    #else
                                    .tab(SHELF_MOD_TAB)
                                    #endif
                                    #if MC_VER >= V1_21_3
                                    .setId(shelf_item_key)
                                    #endif
                    )
            );

            SHELVES_ITEM.add(shelf_item);
        }
    }

    public static void registerFloorShelves() {
        for (int i = 0; i < MATERIALS.size(); ++i) {
            ResourceLocation location = #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_" + MATERIALS.get(i));
            #if MC_VER >= V1_21_3 ResourceKey<Block> shelf_key = ResourceKey.create(Registries.BLOCK, location); #endif

            RegistrySupplier<Block> floor_shelf = blocks.register(
                    location,
                    () -> new FloorShelf(
                            BlockBehaviour.Properties.of(#if MC_VER <= V1_19_4 Material.WOOD #endif).strength(0.5F, 3.0F).sound(SoundType.WOOD)
                    )
            );

            FLOOR_SHELVES_BLOCK.add(floor_shelf);

            ResourceLocation itemLocation = #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_item_" + MATERIALS.get(i));
            #if MC_VER >= V1_21_3 ResourceKey<Item> floor_shelf_item_key = ResourceKey.create(Registries.ITEM, itemLocation); #endif

            RegistrySupplier<Item> floor_shelf_item = items.register(
                    itemLocation,
                    () -> new BlockItem(
                            floor_shelf.get(),
                            new Item.Properties()
                            #if MC_VER >= V1_19_4
                            .arch$tab(SHELF_MOD_TAB)
                            #else
                            .tab(SHELF_MOD_TAB)
                            #endif
                            #if MC_VER >= V1_21_3
                            .setId(floor_shelf_item_key)
                            #endif
                    )
            );

            FLOOR_SHELVES_ITEM.add(floor_shelf_item);
        }
    }


    public static void generateBlockItemModels(List<RegistrySupplier<Item>> items, List<RegistrySupplier<Block>> blocks) {
        #if MC_VER >= V1_21_4
        Path outputPath = Paths.get("resources/assets/" + MODID + "/items");
        #else
        Path outputPath = Paths.get("resources/assets/" + MODID + "/models/item");
        #endif

        try {
            Files.createDirectories(outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < blocks.size(); ++i) {
            String filename = items.get(i).get()#if MC_VER >= V1_18_2 .arch$registryName().getPath() #else .getDefaultInstance().toString() #endif;
            Path file = outputPath.resolve(filename + ".json");

            JsonObject parent = new JsonObject();
            #if MC_VER >= V1_21_4

            JsonObject info = new JsonObject();

            info.addProperty("type", "minecraft:model");
            info.addProperty("model", MODID + ":block/" + blocks.get(i).get().arch$registryName().getPath());

            parent.add("model", info);
            #else
            parent.addProperty("parent", MODID + ":block/" + blocks.get(i).get()#if MC_VER >= V1_18_2 .arch$registryName().getPath() #else .toString() #endif);
            #endif

            String modelPath = "resources/assets/" + ShelfModCommon.MODID + "/models/item/" + filename + ".json";
        }
    }


    public static void initializeServer() {
        #if MC_VER >= V1_21_3 ResourceKey<Item> iconKey = ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MODID, "icon")); #endif
        ICON = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "icon"), () -> new Item(new Item.Properties() #if MC_VER >= V1_21_3 .setId(iconKey) #endif));

        #if MC_VER >= V1_20_1
        SHELF_MOD_TAB = itemGroups.register(
                #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_mod_tab"),
                () -> CreativeTabRegistry.create(
                        Component.literal("Shelf mod"),
                        () -> ICON.get().asItem().getDefaultInstance()
                )
        );
        #else
        SHELF_MOD_TAB = CreativeTabRegistry.create(
                new ResourceLocation(MODID, "shelf_mod_tab"),
                () -> ICON.get().asItem().getDefaultInstance()
        );
        #endif


        registerShelves();
        registerFloorShelves();

        #if MC_VER < V1_21_3
        SHELF_BLOCK_ENTITY = blockEntities.register(
                #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_block_entity"),
                () -> BlockEntityType.Builder.of(
                        ShelfBlockEntity::new,
                        SHELVES_BLOCK.stream().map(RegistrySupplier::get).toArray(Block[]::new)
                ).build(null)
        );

        FLOOR_SHELF_BLOCK_ENTITY = blockEntities.register(
                #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_block_entity"),
                () -> BlockEntityType.Builder.of(
                        FloorShelfBlockEntity::new,
                        FLOOR_SHELVES_BLOCK.stream().map(RegistrySupplier::get).toArray(Block[]::new)
                ).build(null)
        );
        #endif

        SHELVES_JSON = new ArrayList<>();

        for (String material : MATERIALS) {
            SHELVES_JSON.add(createShelfRecipe(material));
            SHELVES_JSON.add(createFloorShelfRecipe(material));
        }
    }
}
