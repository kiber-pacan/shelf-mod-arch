package com.akciater.client.ber;

#if MC_VER >= V1_19_4
    import com.akciater.blocks.FloorShelfBlockEntity;
    import com.akciater.blocks.ShelfBlockEntity;
    import com.mojang.blaze3d.vertex.PoseStack;
    import com.mojang.math.Axis;
    import net.minecraft.client.Minecraft;
    import net.minecraft.client.renderer.MultiBufferSource;
    import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
    import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
    import net.minecraft.client.renderer.entity.ItemRenderer;
    import net.minecraft.core.Direction;
    import net.minecraft.world.item.BlockItem;
    import net.minecraft.world.item.ItemDisplayContext;
    import net.minecraft.world.item.ItemStack;
    import net.minecraft.world.level.Level;
    import net.minecraft.world.level.block.Blocks;
    import net.minecraft.world.level.block.state.BlockState;
    import net.minecraft.world.phys.Vec3;
    import org.joml.Quaternionf;

    import java.util.ArrayList;
    import java.util.List;

    import static com.akciater.blocks.FloorShelf.FACING;
#else
    import com.akciater.blocks.FloorShelfBlockEntity;
    import com.mojang.blaze3d.vertex.PoseStack;
    import com.mojang.math.Quaternion;
    import net.minecraft.client.Minecraft;
    import net.minecraft.client.renderer.MultiBufferSource;
    import net.minecraft.client.renderer.block.model.ItemTransforms;
    import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
    import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
    import net.minecraft.client.renderer.entity.ItemRenderer;
    import net.minecraft.core.Direction;
    import net.minecraft.world.item.BlockItem;
    import net.minecraft.world.item.ItemStack;
    import net.minecraft.world.level.Level;
    import net.minecraft.world.level.block.Blocks;
    import net.minecraft.world.level.block.state.BlockState;
    import net.minecraft.world.phys.Vec3;

    import java.util.ArrayList;
    import java.util.List;

    import static com.akciater.blocks.FloorShelf.FACING;
#endif

public class FloorShelfBER implements BlockEntityRenderer<FloorShelfBlockEntity> {
    public List<Vec3> positions = new ArrayList<>(
            List.of(
                    new Vec3(0.265625F, 0.275F, 0.265625F),
                    new Vec3(0.734375F, 0.275F, 0.265625F),
                    new Vec3(0.265625F, 0.275F, 0.734375F),
                    new Vec3(0.734375F, 0.275F, 0.734375F)
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
            case NORTH -> new Quaternion(0,(float) Math.toRadians(0),0,0);
            case EAST -> new Quaternion(0,(float) Math.toRadians(180),0,0);
            case SOUTH -> new Quaternion(0,(float) Math.toRadians(270),0,0);
            case WEST -> new Quaternion(0,(float) Math.toRadians(90),0,0);
            #endif
        };
    }

    public FloorShelfBER(BlockEntityRendererProvider.Context ctx) {}

    public void render(FloorShelfBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockState state;
        Level world = entity.getLevel();

        if ((state = world.getBlockState(entity.getBlockPos())).getBlock() == Blocks.AIR) return;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        #if MC_VER >= V1_19_4
            Quaternionf quaternionf = getRotation(state.getValue(FACING));
        #else
            Quaternion quaternionf = getRotation(state.getValue(FACING));
        #endif

        float blockSize = 1;

        for (int i = 0; i < 4; i++) {
            ItemStack stack = entity.inv.get(i);
            if(!stack.isEmpty()) {
                Vec3 pos = positions.get(i);

                poseStack.pushPose();

                poseStack.translate(pos.x, pos.y - (0.40625 * (1 - blockSize)) / 2, pos.z);

                boolean fullBlock = stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock().defaultBlockState().isCollisionShapeFullBlock(entity.getLevel(), entity.getBlockPos());
                float scale = (fullBlock) ? 0.40625f * 2 : 0.40625f;

                poseStack.scale(scale * blockSize, scale * blockSize, scale * blockSize);

                poseStack.mulPose(quaternionf);

                #if MC_VER >= V1_19_4
                    poseStack.mulPose(Axis.XP.rotationDegrees(90));
                    itemRenderer.renderStatic(stack, #if MC_VER >= V1_19_4 ItemDisplayContext.FIXED #else ItemTransforms.TransformType.FIXED #endif, packedLight, packedOverlay, poseStack, buffer #if MC_VER >= V1_19_4, entity.getLevel() #endif, 1);
                #else
                    poseStack.mulPose(new Quaternion((float) Math.toRadians(90), 0, 0, 0));
                    itemRenderer.renderStatic(stack, #if MC_VER >= V1_19_4 ItemDisplayContext.FIXED #else ItemTransforms.TransformType.FIXED #endif, packedLight, packedOverlay, poseStack, buffer #if MC_VER >= V1_19_4, entity.getLevel() #endif, 1);
                #endif

                poseStack.popPose();
            }
        }
    }
}