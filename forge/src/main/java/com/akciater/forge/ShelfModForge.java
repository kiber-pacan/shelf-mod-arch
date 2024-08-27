package com.akciater.forge;

import com.akciater.forge.client.config.ShelfModConfigForge;
import dev.architectury.platform.forge.EventBuses;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.akciater.ShelfModCommon;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(ShelfModCommon.MODID)
public final class ShelfModForge {
    public ShelfModForge() {
        EventBuses.registerModEventBus(ShelfModCommon.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        ShelfModCommon.initializeServer();
        if (FMLLoader.getDist() == Dist.CLIENT) {
            AutoConfig.register(ShelfModConfigForge.class, Toml4jConfigSerializer::new);
        }
    }
}
