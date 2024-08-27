package com.akciater.fabric.client.ber;

import com.akciater.client.ber.FloorShelfBER;
import com.akciater.blocks.FloorShelfBlockEntity;
import com.akciater.fabric.client.config.ShelfModConfigFabric;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class FloorShelfBER_Fabric extends FloorShelfBER {
    ShelfModConfigFabric config = AutoConfig.getConfigHolder(ShelfModConfigFabric.class).getConfig();

    public FloorShelfBER_Fabric(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(FloorShelfBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        render(entity, tickDelta, matrices, vertexConsumers, light, overlay, config.size);
    }
}