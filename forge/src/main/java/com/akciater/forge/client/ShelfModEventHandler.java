package com.akciater.forge.client;

import com.akciater.ShelfModCommon;
import com.akciater.client.ber.FloorShelfBER;
import com.akciater.client.ber.ShelfBER;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


import static com.akciater.ShelfModCommon.MODID;


@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ShelfModEventHandler {
    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ShelfModCommon.FLOOR_SHELF_BLOCK_ENTITY.get(), FloorShelfBER::new);
        event.registerBlockEntityRenderer(ShelfModCommon.SHELF_BLOCK_ENTITY.get(), ShelfBER::new);
    }
}