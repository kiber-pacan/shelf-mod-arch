package com.akicater.quilt.client.ber;

import com.akciater.blocks.ShelfBlockEntity;
import com.akciater.client.ber.ShelfBER;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class ShelfBER_Quilt extends ShelfBER {
    public ShelfBER_Quilt(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(ShelfBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        render(entity, tickDelta, matrices, vertexConsumers, light, overlay, true);
    }
}
