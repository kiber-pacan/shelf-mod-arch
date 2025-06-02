package com.akicater.neoforgeShelfMod.client;

import com.akciater.ShelfModCommon;
import com.akciater.client.ber.FloorShelfBER;
import com.akciater.client.ber.ShelfBER;
import com.akicater.neoforgeShelfMod.ShelfModNeoForge;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
#if MC_VER >= V1_21
    import net.neoforged.fml.common.EventBusSubscriber;
#else
    import net.neoforged.fml.common.Mod;
#endif

import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import static com.akciater.ShelfModCommon.MODID;

@#if MC_VER < V1_21 Mod. #endif EventBusSubscriber(bus = #if MC_VER >= V1_21 EventBusSubscriber #else Mod.EventBusSubscriber #endif.Bus.MOD, modid = MODID)
public class ShelfModEventHandler {
    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(#if MC_VER >= V1_21_3 ShelfModNeoForge.floorShelfBlockEntity.get() #else ShelfModCommon.FLOOR_SHELF_BLOCK_ENTITY.get() #endif, FloorShelfBER::new);
        event.registerBlockEntityRenderer(#if MC_VER >= V1_21_3 ShelfModNeoForge.shelfBlockEntity.get() #else ShelfModCommon.SHELF_BLOCK_ENTITY.get() #endif, ShelfBER::new);
    }
}