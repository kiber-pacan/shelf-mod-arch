package com.akciater.client.ber;

import com.akciater.blocks.ShelfBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Rotations;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

#if MC_VER >= V1_19_4

#else
    import net.minecraft.util.math.Quaternion;
#endif

import java.util.ArrayList;
import java.util.List;

import static com.akciater.blocks.Shelf.FACING;

public class ShelfBER implements BlockEntityRenderer<ShelfBlockEntity> {
    public List<Vec3> itemPositions = new ArrayList<>(
            List.of(
                    new Vec3(0.265625F, 0.265625F, 0.75F),
                    new Vec3(0.734375F, 0.265625F, 0.75F),
                    new Vec3(0.265625F, 0.734375F, 0.75F),
                    new Vec3(0.734375F, 0.734375F, 0.75F)
            )
    );

    public List<Vec3> blockPositions = new ArrayList<>(
            List.of(
                    new Vec3(0.278325F, 0.2627F, 0.75F),
                    new Vec3(0.721675F, 0.2627F, 0.75F),
                    new Vec3(0.278325F, 0.7373F, 0.75F),
                    new Vec3(0.721675F, 0.7373F, 0.75F)
            )
    );

    public #if MC_VER >= V1_19_4 Quaternionf #else Quaternion #endif getRotation(Direction direction) {
        return switch (direction) {
            #if MC_VER >= V1_19_4
            case NORTH -> Axis.YP.rotationDegrees(0);
            case EAST -> Axis.YP.rotationDegrees(270);
            case SOUTH -> Axis.YP.rotationDegrees(180);
            case WEST -> Axis.YP.rotationDegrees(90);
            default -> null;
            #else
            case DOWN -> null;
            case UP -> null;
            case NORTH -> Vector3f.XP.rotationDegrees(0);
            case EAST -> Vector3f.XP.rotationDegrees(270);
            case SOUTH -> Vector3f.YP.rotationDegrees(180);
            case WEST -> Vector3f.YP.rotationDegrees(90);
            #endif
        };
    }

    public ShelfBER(BlockEntityRendererProvider.Context ctx) {}

    public void render(ShelfBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        int blockSize = 1;
        int itemSize = 1;
        BlockState state;
        Level world = entity.getLevel();
        if ((state = world.getBlockState(entity.getBlockPos())).getBlock() == Blocks.AIR) return;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        #if MC_VER >= V1_19_4
            Quaternionf quaternionf = getRotation(state.getValue(FACING));
        #else
        Quaternion quaternionf = getRotation(state.get(Properties.HORIZONTAL_FACING));
        #endif

        for (int i = 0; i < 4; i++) {
            ItemStack stack = entity.inv.get(i);
            if(!stack.isEmpty()) {
                Vec3 pos;
                if (stack.getItem() instanceof BlockItem) {
                    pos = blockPositions.get(i);
                } else {
                    pos = itemPositions.get(i);
                }

                poseStack.pushPose();

                poseStack.translate(0.5, 0, 0.5);
                poseStack.mulPose(quaternionf);

                poseStack.translate(-0.5, 0, -0.5);
                poseStack.translate(pos.x, pos.y - (0.40625 * (1 - blockSize)) / 2, pos.z);

                boolean fullBlock = stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock().defaultBlockState().isCollisionShapeFullBlock(entity.getLevel(), entity.getBlockPos());
                float scale = (fullBlock) ? 0.40625f * 2 : 0.40625f;
                poseStack.scale(scale * blockSize, scale * blockSize, scale * blockSize);

                itemRenderer.renderStatic(stack, #if MC_VER >= V1_19_4 ItemDisplayContext.FIXED #else ItemTransforms.TransformType.FIXED #endif, packedLight, packedOverlay, poseStack, buffer #if MC_VER >= V1_19_4, entity.getLevel() #endif, 1);

                poseStack.popPose();
            }
        }
    }
}