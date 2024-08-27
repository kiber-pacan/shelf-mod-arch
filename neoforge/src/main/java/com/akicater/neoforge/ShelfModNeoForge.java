package com.akicater.neoforge;

import com.akciater.ShelfModCommon;
import com.akicater.neoforge.client.config.ClothConfigImpl;
import com.akicater.neoforge.client.config.ShelfModConfigNeoForge;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

import static com.akciater.ShelfModCommon.*;

@Mod(MODID)
public final class ShelfModNeoForge {
    public static RegistryKey<ItemGroup> EXAMPLE_TAB;

    public ShelfModNeoForge() {
        // Initialization
        ShelfModCommon.initializeServer();

        if (FMLLoader.getDist() == net.neoforged.api.distmarker.Dist.CLIENT) {
            AutoConfig.register(ShelfModConfigNeoForge.class, Toml4jConfigSerializer::new);
            ClothConfigImpl.registerModsPage();
        }
    }
}
