package com.akciater.forge.client.ber;


import com.akciater.blocks.FloorShelfBlockEntity;
import com.akciater.client.ber.FloorShelfBER;
import com.akciater.forge.client.config.ShelfModConfigForge;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

public class FloorShelfBER_Forge extends FloorShelfBER {
    ShelfModConfigForge config = AutoConfig.getConfigHolder(ShelfModConfigForge.class).getConfig();

    public FloorShelfBER_Forge(BlockEntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(FloorShelfBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        render(entity, tickDelta, matrices, vertexConsumers, light, overlay, config.size);
    }
}