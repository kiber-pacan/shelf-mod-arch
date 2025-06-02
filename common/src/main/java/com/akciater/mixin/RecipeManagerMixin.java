package com.akciater.mixin;

import com.akciater.ShelfModCommon;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.Minecraft;
#if MC_VER >= V1_19_4
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
#endif
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
#if MC_VER >= V1_21_3
import net.minecraft.world.item.crafting.RecipeHolder;
#endif
import net.minecraft.world.item.crafting.RecipeManager;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.util.perf.Profiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.akciater.ShelfModCommon.*;

#if MC_VER >= V1_21_3
import net.minecraft.world.item.crafting.RecipeMap;
#endif

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    #if MC_VER >= V1_21_3
    @Shadow
    protected static RecipeHolder<?> fromJson(ResourceKey<Recipe<?>> recipe, JsonObject json, HolderLookup.Provider registries) {
        return null;
    }

    @Shadow
    protected void apply(RecipeMap object, ResourceManager resourceManager, ProfilerFiller profiler) {
        throw new UnsupportedOperationException("Shadow method");
    }
    @Final
    @Shadow
    private HolderLookup.Provider registries;

    private Boolean loaded = false;
    #endif

    private static final java.util.logging.Logger shelfmod$LOGGER = Logger.getLogger("ShelfMod");

    #if MC_VER >= V1_21_3
    @Inject(method = "apply(Lnet/minecraft/world/item/crafting/RecipeMap;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("TAIL"))
    public void onApply(RecipeMap object, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
    #else
    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("HEAD"))
    public void interceptApply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo info) {
    #endif
        #if MC_VER >= V1_21_3
        if (!loaded) {
            loaded = true;

            HolderLookup.Provider registries1;
            try {
                registries1 = registries;
            } catch (Throwable t) {
                registries1 = null;
            }
            if (registries1 == null) {
                // не меняем ничего, пропускаем дальше оригинал
                return;
            }

            List<RecipeHolder<?>> allRecipes = new ArrayList<>(object.values());

            for (int i = 0; i < 11; i++) {
                ResourceLocation shelfId = #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(ShelfModCommon.MODID, "shelf_item_" + ShelfModCommon.MATERIALS.get(i));
                ResourceLocation floorShelfId = #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(ShelfModCommon.MODID, "floor_shelf_item_" + ShelfModCommon.MATERIALS.get(i));

                JsonObject shelfJSON = ShelfModCommon.SHELVES_JSON.get(i * 2);
                JsonObject floorShelfJSON = ShelfModCommon.SHELVES_JSON.get(i * 2 + 1);

                ResourceKey<Recipe<?>> shelfKey = ResourceKey.create(Registries.RECIPE, shelfId);
                ResourceKey<Recipe<?>> floorShelfKey = ResourceKey.create(Registries.RECIPE, floorShelfId);

                RecipeHolder<?> shelfRecipe = fromJson(shelfKey, shelfJSON, registries);
                RecipeHolder<?> floorShelfRecipe = fromJson(floorShelfKey, floorShelfJSON, registries);

                allRecipes.add(shelfRecipe);
                allRecipes.add(floorShelfRecipe);
            }

            for (RecipeHolder<?> recipe : allRecipes) {
                ResourceKey<Recipe<?>> key = recipe.id();
                if (key != null && key.location().getNamespace().equals(ShelfModCommon.MODID)) {
                    System.out.println("Loaded mod recipe: " + key.location());
                }
            }

            RecipeMap newMap = RecipeMap.create(allRecipes);
            apply(newMap, resourceManager, profiler);
        }
        #else
        // Добавляем свои рецепты из JSON
        for (int i = 0; i < MATERIALS.size(); i++) {
            ResourceLocation shelfId = #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(ShelfModCommon.MODID, "shelf_item_" + ShelfModCommon.MATERIALS.get(i));
            ResourceLocation floorShelfId = #if MC_VER >= V1_21 ResourceLocation.fromNamespaceAndPath #else new ResourceLocation #endif(ShelfModCommon.MODID, "floor_shelf_item_" + ShelfModCommon.MATERIALS.get(i));

            JsonObject shelfJSON = ShelfModCommon.SHELVES_JSON.get(i * 2);
            JsonObject floorShelfJSON = ShelfModCommon.SHELVES_JSON.get(i * 2 + 1);

            map.put(shelfId, shelfJSON);
            map.put(floorShelfId, floorShelfJSON);
        }
        #endif

        shelfmod$LOGGER.info("Loaded shelfmod recipes");
    }
}
