package com.akciater.forge.client.config;

import com.akciater.forge.client.ber.FloorShelfBER_Forge;
import com.akciater.forge.client.ber.ShelfBER_Forge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.akciater.ShelfModCommon.*;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ShelfModEventHandler {
    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(SHELF_BLOCK_ENTITY.get(), ShelfBER_Forge::new);
        event.registerBlockEntityRenderer(FLOOR_SHELF_BLOCK_ENTITY.get(), FloorShelfBER_Forge::new);
    }
}