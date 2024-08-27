package com.akicater.quilt.client.ber;

import com.akciater.blocks.FloorShelfBlockEntity;
import com.akciater.client.ber.FloorShelfBER;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class FloorShelfBER_Quilt extends FloorShelfBER {
    public FloorShelfBER_Quilt(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(FloorShelfBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        render(entity, tickDelta, matrices, vertexConsumers, light, overlay, true);
    }
}