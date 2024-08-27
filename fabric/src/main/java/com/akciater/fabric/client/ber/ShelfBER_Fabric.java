package com.akciater.fabric.client.ber;

import com.akciater.client.ber.ShelfBER;
import com.akciater.blocks.ShelfBlockEntity;
import com.akciater.fabric.client.config.ShelfModConfigFabric;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class ShelfBER_Fabric extends ShelfBER {
    ShelfModConfigFabric config = AutoConfig.getConfigHolder(ShelfModConfigFabric.class).getConfig();

    public ShelfBER_Fabric(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(ShelfBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        render(entity, tickDelta, matrices, vertexConsumers, light, overlay, config.size);
    }
}
