package com.akicater.neoforge.client;

import com.akciater.ShelfModCommon;
import com.akciater.client.ber.FloorShelfBER;
import com.akciater.client.ber.ShelfBER;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
#if MC_VER >= V1_21
    import net.neoforged.fml.common.EventBusSubscriber;
#else
    import net.neoforged.fml.common.Mod;
#endif

import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import static com.akciater.ShelfModCommon.MODID;

#if MC_VER >= V1_21
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
#else
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
#endif
public class ShelfModEventHandler {
    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ShelfModCommon.FLOOR_SHELF_BLOCK_ENTITY.get(), FloorShelfBER::new);
        event.registerBlockEntityRenderer(ShelfModCommon.SHELF_BLOCK_ENTITY.get(), ShelfBER::new);
    }
}