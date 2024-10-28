package com.akciater;

import com.akciater.blocks.FloorShelf;
import com.akciater.blocks.FloorShelfBlockEntity;
import com.akciater.blocks.Shelf;
import com.akciater.blocks.ShelfBlockEntity;
import com.google.common.base.Suppliers;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

#if MC_VER >= V1_21
import dev.architectury.registry.registries.RegistrarManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
#endif

#if MC_VER >= V1_19_4
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.core.registries.Registries;
#else
import dev.architectury.registry.registries.Registries;
import net.minecraft.core.Registry;
#endif

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

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

    public static RegistrySupplier<CreativeModeTab> SHELF_MOD_TAB;


    public static List<RegistrySupplier<Item>> SHELVES;
    public static List<JsonObject> SHELVES_JSON;
    public static List<String> MATERIALS = List.of(
            "oak",
            "acacia",
            "birch",
            "dark_oak",
            "spruce",
            "jungle",
            "mangrove",
            "bamboo",
            "warped",
            "cherry",
            "crimson"
    );


    public static boolean isShelf(Item item) {
        for (RegistrySupplier<Item> shelf : SHELVES) {
            if (shelf.get() == item) return true;
        }
        return false;
    }

    public static JsonObject createShapedRecipeJson(ArrayList<Character> keys, ArrayList<ResourceLocation> items, ArrayList<String> type, ArrayList<String> pattern, ResourceLocation output) {
        //Creating a new json object, where we will store our recipe.
        JsonObject json = new JsonObject();
        //The "type" of the recipe we are creating. In this case, a shaped recipe.
        json.addProperty("type", "minecraft:crafting_shaped");
        //This creates:
        //"type": "minecraft:crafting_shaped"

        //We create a new Json Element, and add our crafting pattern to it.
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(pattern.get(0));
        jsonArray.add(pattern.get(1));
        jsonArray.add(pattern.get(2));
        //Then we add the pattern to our json object.
        json.add("pattern", jsonArray);
        //This creates:
        //"pattern": [
        //  "###",
        //  " | ",
        //  " | "
        //]

        //Next we need to define what the keys in the pattern are. For this we need different JsonObjects per key definition, and one main JsonObject that will contain all of the defined keys.
        JsonObject individualKey; //Individual key
        JsonObject keyList = new JsonObject(); //The main key object, containing all the keys

        for (int i = 0; i < keys.size(); ++i) {
            individualKey = new JsonObject();
            individualKey.addProperty(type.get(i), items.get(i).toString()); //This will create a key in the form "type": "input", where type is either "item" or "tag", and input is our input item.
            keyList.add(keys.get(i) + "", individualKey); //Then we add this key to the main key object.
            //This will add:
            //"#": { "tag": "c:copper_ingots" }
            //and after that
            //"|": { "item": "minecraft:sticks" }
            //and so on.
        }

        json.add("key", keyList);
        //And so we get:
        //"key": {
        //  "#": {
        //    "tag": "c:copper_ingots"
        //  },
        //  "|": {
        //    "item": "minecraft:stick"
        //  }
        //},

        //Finally, we define our result object
        JsonObject result = new JsonObject();
        result.addProperty(#if MC_VER < V1_21 "item" #else "id" #endif, output.toString());
        result.addProperty("count", 1);
        json.add("result", result);
        //This creates:
        //"result": {
        //  "item": "modid:copper_pickaxe",
        //  "count": 1
        //}

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


    public static void initializeServer() {
        ICON = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "icon"), () -> new Item(new Item.Properties()));

        #if MC_VER >= V1_20_1
        SHELF_MOD_TAB = itemGroups.register(
                #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_mod_tab"),
                () -> CreativeTabRegistry.create(
                        Component.literal("Shelf mod"),
                        () -> ICON.get().asItem().getDefaultInstance()
                )
        );
        #else
        #if MC_VER == V1_19_4 CreativeTabRegistry.TabSupplier #else CreativeModeTab #endif
        SHELF_MOD_TAB = CreativeTabRegistry.create(
                new ResourceLocation(MODID, "shelf_mod_tab"),
                () -> ICON.get().asItem().getDefaultInstance()
        );
        #endif

        SHELF_OAK = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_oak"), () -> new Shelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));
        FLOOR_SHELF_OAK = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_oak"), () -> new FloorShelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));

        SHELF_ACACIA = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_acacia"), () -> new Shelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));
        FLOOR_SHELF_ACACIA = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_acacia"), () -> new FloorShelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));

        SHELF_BIRCH = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_birch"), () -> new Shelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));
        FLOOR_SHELF_BIRCH = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_birch"), () -> new FloorShelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));

        SHELF_DARK_OAK = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_dark_oak"), () -> new Shelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));
        FLOOR_SHELF_DARK_OAK = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_dark_oak"), () -> new FloorShelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));

        SHELF_SPRUCE = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_spruce"), () -> new Shelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));
        FLOOR_SHELF_SPRUCE = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_spruce"), () -> new FloorShelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));

        SHELF_JUNGLE = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_jungle"), () -> new Shelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));
        FLOOR_SHELF_JUNGLE = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_jungle"), () -> new FloorShelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));

        SHELF_MANGROVE = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_mangrove"), () -> new Shelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));
        FLOOR_SHELF_MANGROVE = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_mangrove"), () -> new FloorShelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));

        SHELF_BAMBOO = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_bamboo"), () -> new Shelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));
        FLOOR_SHELF_BAMBOO = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_bamboo"), () -> new FloorShelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));

        SHELF_WARPED = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_warped"), () -> new Shelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));
        FLOOR_SHELF_WARPED = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_warped"), () -> new FloorShelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));

        SHELF_CHERRY = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_cherry"), () -> new Shelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));
        FLOOR_SHELF_CHERRY = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_cherry"), () -> new FloorShelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));

        SHELF_CRIMSON = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_crimson"), () -> new Shelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));
        FLOOR_SHELF_CRIMSON = blocks.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_crimson"), () -> new FloorShelf(BlockBehaviour.Properties#if MC_VER <= V1_20_1 .copy #else .ofFullCopy #endif(Blocks.OAK_PLANKS)));


        SHELF_ITEM_OAK = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_item_oak"), () -> new BlockItem(SHELF_OAK.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));
        FLOOR_SHELF_ITEM_OAK = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_item_oak"), () -> new BlockItem(FLOOR_SHELF_OAK.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));

        SHELF_ITEM_ACACIA = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_item_acacia"), () -> new BlockItem(SHELF_ACACIA.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));
        FLOOR_SHELF_ITEM_ACACIA = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_item_acacia"), () -> new BlockItem(FLOOR_SHELF_ACACIA.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));

        SHELF_ITEM_BIRCH = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_item_birch"), () -> new BlockItem(SHELF_BIRCH.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));
        FLOOR_SHELF_ITEM_BIRCH = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_item_birch"), () -> new BlockItem(FLOOR_SHELF_BIRCH.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));

        SHELF_ITEM_DARK_OAK = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_item_dark_oak"), () -> new BlockItem(SHELF_DARK_OAK.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));
        FLOOR_SHELF_ITEM_DARK_OAK = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_item_dark_oak"), () -> new BlockItem(FLOOR_SHELF_DARK_OAK.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));

        SHELF_ITEM_SPRUCE = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_item_spruce"), () -> new BlockItem(SHELF_SPRUCE.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));
        FLOOR_SHELF_ITEM_SPRUCE = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_item_spruce"), () -> new BlockItem(FLOOR_SHELF_SPRUCE.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));

        SHELF_ITEM_JUNGLE = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_item_jungle"), () -> new BlockItem(SHELF_JUNGLE.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));
        FLOOR_SHELF_ITEM_JUNGLE = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_item_jungle"), () -> new BlockItem(FLOOR_SHELF_JUNGLE.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));

        SHELF_ITEM_MANGROVE = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_item_mangrove"), () -> new BlockItem(SHELF_MANGROVE.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));
        FLOOR_SHELF_ITEM_MANGROVE = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_item_mangrove"), () -> new BlockItem(FLOOR_SHELF_MANGROVE.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));

        SHELF_ITEM_BAMBOO = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_item_bamboo"), () -> new BlockItem(SHELF_BAMBOO.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));
        FLOOR_SHELF_ITEM_BAMBOO = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_item_bamboo"), () -> new BlockItem(FLOOR_SHELF_BAMBOO.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));

        SHELF_ITEM_WARPED = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_item_warped"), () -> new BlockItem(SHELF_WARPED.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));
        FLOOR_SHELF_ITEM_WARPED = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_item_warped"), () -> new BlockItem(FLOOR_SHELF_WARPED.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));

        SHELF_ITEM_CHERRY = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_item_cherry"), () -> new BlockItem(SHELF_CHERRY.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));
        FLOOR_SHELF_ITEM_CHERRY = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_item_cherry"), () -> new BlockItem(FLOOR_SHELF_CHERRY.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));

        SHELF_ITEM_CRIMSON = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_item_crimson"), () -> new BlockItem(SHELF_CRIMSON.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));
        FLOOR_SHELF_ITEM_CRIMSON = items.register(#if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_item_crimson"), () -> new BlockItem(FLOOR_SHELF_CRIMSON.get(), new Item.Properties()#if MC_VER >= V1_19_4 .arch$tab(SHELF_MOD_TAB) #else .tab(SHELF_MOD_TAB) #endif));


        SHELF_BLOCK_ENTITY = blockEntities.register(
                #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "shelf_block_entity"),
                () -> BlockEntityType.Builder.of(
                        ShelfBlockEntity::new,
                        SHELF_OAK.get(), SHELF_ACACIA.get(), SHELF_BIRCH.get(), SHELF_DARK_OAK.get(), SHELF_SPRUCE.get(), SHELF_JUNGLE.get(), SHELF_MANGROVE.get(), SHELF_BAMBOO.get(), SHELF_WARPED.get(), SHELF_CHERRY.get(), SHELF_CRIMSON.get()
                ).build(null)
        );

        FLOOR_SHELF_BLOCK_ENTITY = blockEntities.register(
                #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(MODID, "floor_shelf_block_entity"),
                () -> BlockEntityType.Builder.of(
                        FloorShelfBlockEntity::new,
                        FLOOR_SHELF_OAK.get(), FLOOR_SHELF_ACACIA.get(), FLOOR_SHELF_BIRCH.get(), FLOOR_SHELF_DARK_OAK.get(), FLOOR_SHELF_SPRUCE.get(), FLOOR_SHELF_JUNGLE.get(), FLOOR_SHELF_MANGROVE.get(), FLOOR_SHELF_BAMBOO.get(), FLOOR_SHELF_WARPED.get(), FLOOR_SHELF_CHERRY.get(), FLOOR_SHELF_CRIMSON.get()
                ).build(null)
        );

        SHELVES = List.of(
                SHELF_ITEM_OAK,
                FLOOR_SHELF_ITEM_OAK,
                SHELF_ITEM_ACACIA,
                FLOOR_SHELF_ITEM_ACACIA,
                SHELF_ITEM_BIRCH,
                FLOOR_SHELF_ITEM_BIRCH,
                SHELF_ITEM_DARK_OAK,
                FLOOR_SHELF_ITEM_DARK_OAK,
                SHELF_ITEM_SPRUCE,
                FLOOR_SHELF_ITEM_SPRUCE,
                SHELF_ITEM_JUNGLE,
                FLOOR_SHELF_ITEM_JUNGLE,
                SHELF_ITEM_MANGROVE,
                FLOOR_SHELF_ITEM_MANGROVE,
                SHELF_ITEM_BAMBOO,
                FLOOR_SHELF_ITEM_BAMBOO,
                SHELF_ITEM_WARPED,
                FLOOR_SHELF_ITEM_WARPED,
                SHELF_ITEM_CHERRY,
                FLOOR_SHELF_ITEM_CHERRY,
                SHELF_ITEM_CRIMSON,
                FLOOR_SHELF_ITEM_CRIMSON
        );

        SHELVES_JSON = new ArrayList<>();

        for (String material : MATERIALS) {
            SHELVES_JSON.add(createShelfRecipe(material));
            SHELVES_JSON.add(createFloorShelfRecipe(material));
        }
    }
}
