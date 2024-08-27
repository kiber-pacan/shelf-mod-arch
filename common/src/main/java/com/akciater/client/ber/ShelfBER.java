package com.akciater.client.ber;

import com.akciater.blocks.ShelfBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

#if MC_VER >= V1_19_4
    import net.minecraft.util.math.RotationAxis;
    import net.minecraft.client.render.model.json.ModelTransformationMode;
    import org.joml.Quaternionf;
#else
    import net.minecraft.util.math.Quaternion;
#endif

import java.util.ArrayList;
import java.util.List;

public abstract class ShelfBER implements BlockEntityRenderer<ShelfBlockEntity> {
    public List<Vec3d> itemPositions = new ArrayList<>(
            List.of(
                    new Vec3d(0.265625F, 0.265625F, 0.75F),
                    new Vec3d(0.734375F, 0.265625F, 0.75F),
                    new Vec3d(0.265625F, 0.734375F, 0.75F),
                    new Vec3d(0.734375F, 0.734375F, 0.75F)
            )
    );

    public List<Vec3d> blockPositions = new ArrayList<>(
            List.of(
                    new Vec3d(0.278325F, 0.2627F, 0.75F),
                    new Vec3d(0.721675F, 0.2627F, 0.75F),
                    new Vec3d(0.278325F, 0.7373F, 0.75F),
                    new Vec3d(0.721675F, 0.7373F, 0.75F)
            )
    );

    public #if MC_VER >= V1_19_4 Quaternionf #else Quaternion #endif getRotation(Direction direction) {
        return switch (direction) {
            #if MC_VER >= V1_19_4
            case NORTH -> RotationAxis.POSITIVE_Y.rotationDegrees(0);
            case EAST -> RotationAxis.POSITIVE_Y.rotationDegrees(270);
            case SOUTH -> RotationAxis.POSITIVE_Y.rotationDegrees(180);
            case WEST -> RotationAxis.POSITIVE_Y.rotationDegrees(90);
            default -> null;
            #else
            case DOWN -> null;
            case UP -> null;
            case NORTH -> new Quaternion(0,(float) Math.toRadians(0),0,0);
            case EAST -> new Quaternion(0,(float) Math.toRadians(180),0,0);
            case SOUTH -> new Quaternion(0,(float) Math.toRadians(270),0,0);
            case WEST -> new Quaternion(0,(float) Math.toRadians(90),0,0);
            #endif
        };
    }

    public ShelfBER(BlockEntityRendererFactory.Context ctx) {}

    static int getLight(World world, BlockPos pos){
        return LightmapTextureManager.pack(world.getLightLevel(LightType.BLOCK, pos), world.getLightLevel(LightType.SKY, pos));
    }

    public void render(ShelfBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, float blockSize) {
        BlockState state;
        World world = entity.getWorld();
        if ((state = world.getBlockState(entity.getPos())).getBlock() == Blocks.AIR) return;
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        #if MC_VER >= V1_19_4
            Quaternionf quaternionf = getRotation(state.get(Properties.HORIZONTAL_FACING));
        #else
        Quaternion quaternionf = getRotation(state.get(Properties.HORIZONTAL_FACING));
        #endif
        int x = getLight(world, entity.getPos());

        for (int i = 0; i < 4; i++) {
            ItemStack stack = entity.inventory.get(i);
            if(!stack.isEmpty()) {
                Vec3d pos;
                if (stack.getItem() instanceof BlockItem) {
                    pos = blockPositions.get(i);
                } else {
                    pos = itemPositions.get(i);
                }

                matrices.push();

                matrices.translate(0.5, 0, 0.5);
                matrices.multiply(quaternionf);

                matrices.translate(-0.5, 0, -0.5);
                matrices.translate(pos.x, pos.y - (0.40625 * (1 - blockSize)) / 2, pos.z);

                float scale = (MinecraftClient.getInstance().getItemRenderer()#if MC_VER >= V1_19_4 .getModel #else .getHeldItemModel #endif(stack, world, null, 1).hasDepth()) ? 0.40625f * 2 : 0.40625f;
                matrices.scale(scale * blockSize, scale * blockSize, scale * blockSize);

                #if MC_VER >= V1_19_4
                    itemRenderer.renderItem(stack, ModelTransformationMode.FIXED, x, overlay, matrices, vertexConsumers, entity.getWorld(), 1);
                #else
                itemRenderer.renderItem(stack, ModelTransformation.Mode.FIXED, x, overlay, matrices, vertexConsumers, 1);
                #endif

                matrices.pop();
            }
        }
    }
}