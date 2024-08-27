package com.akicater.neoforge.client.ber;

import com.akciater.client.ber.FloorShelfBER;
import com.akciater.blocks.FloorShelfBlockEntity;
import com.akicater.neoforge.client.config.ShelfModConfigNeoForge;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class FloorShelfBER_NeoForge extends FloorShelfBER {
    ShelfModConfigNeoForge config = AutoConfig.getConfigHolder(ShelfModConfigNeoForge.class).getConfig();

    public FloorShelfBER_NeoForge(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(FloorShelfBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        render(entity, tickDelta, matrices, vertexConsumers, light, overlay, config.size);
    }
}