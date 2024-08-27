package com.akciater.forge.client.ber;

import com.akciater.blocks.ShelfBlockEntity;
import com.akciater.client.ber.ShelfBER;
import com.akciater.forge.client.config.ShelfModConfigForge;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class ShelfBER_Forge extends ShelfBER {
    ShelfModConfigForge config = AutoConfig.getConfigHolder(ShelfModConfigForge.class).getConfig();

    public ShelfBER_Forge(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(ShelfBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        render(entity, tickDelta, matrices, vertexConsumers, light, overlay, config.size);
    }
}
