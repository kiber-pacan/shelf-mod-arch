package com.akciater.forgeShelfMod;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.akciater.ShelfModCommon;

@Mod(ShelfModCommon.MODID)
public final class ShelfModForge {

    public ShelfModForge() {
        EventBuses.registerModEventBus(ShelfModCommon.MODID, FMLJavaModLoadingContext.get().getModEventBus());

        ShelfModCommon.initializeServer();
    }
}
