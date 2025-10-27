package com.akciater.client.ber;

#if MC_VER >= V1_19_4
import com.akciater.blocks.FloorShelf;
import com.akciater.blocks.FloorShelfBlockEntity;
import com.akciater.blocks.ShelfBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
    #if MC_VER >= V1_21_9
    import net.minecraft.client.renderer.SubmitNodeCollector;
    import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
    import net.minecraft.client.renderer.item.ItemModelResolver;
    import net.minecraft.client.renderer.item.ItemStackRenderState;
    import net.minecraft.client.renderer.state.CameraRenderState;
    #endif

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.List;

import static com.akciater.blocks.Shelf.FACING;
#else
    import com.akciater.blocks.FloorShelfBlockEntity;
    import com.akciater.blocks.ShelfBlockEntity;
    import com.mojang.blaze3d.vertex.PoseStack;
    import com.mojang.math.Quaternion;
    import com.mojang.math.Vector3f;
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



public class #if MC_VER >= V1_21_9 ShelfBER implements BlockEntityRenderer<ShelfBlockEntity, ShelfBERS> #else ShelfBER implements BlockEntityRenderer<ShelfBlockEntity> #endif {
    public List<Vec3> itemPositions = new ArrayList<>(
            List.of(
                    new Vec3(0.265625F, 0.265625F, 0.5625F),
                    new Vec3(0.734375F, 0.265625F, 0.5625F),
                    new Vec3(0.265625F, 0.734375F, 0.5625F),
                    new Vec3(0.734375F, 0.734375F, 0.5625F)
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
            case NORTH -> Vector3f.YP.rotationDegrees(0);
            case EAST -> Vector3f.YP.rotationDegrees(270);
            case SOUTH -> Vector3f.YP.rotationDegrees(180);
            case WEST -> Vector3f.YP.rotationDegrees(90);
            #endif
        };
    }

    #if MC_VER >= V1_21_9
    private final ItemModelResolver itemModelResolver;


    @Override
    public @NotNull ShelfBERS createRenderState() {
        return new ShelfBERS();
    }

    @Override
    public void extractRenderState(ShelfBlockEntity blockEntity, ShelfBERS renderState, float partialTick, Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress); // ВАЖНО
        renderState.facing = blockEntity.getBlockState().getValue(FloorShelf.FACING);
        renderState.items = new ArrayList<>(blockEntity.inv.size());
        renderState.fullBlock = new ArrayList<>(blockEntity.inv.size());

        int j = (int)blockEntity.getBlockPos().asLong();

        for(int i = 0; i < blockEntity.inv.size(); ++i) {
            renderState.fullBlock.add(blockEntity.inv.get(i).getItem() instanceof BlockItem && ((BlockItem) blockEntity.inv.get(i).getItem()).getBlock().defaultBlockState().isCollisionShapeFullBlock(blockEntity.getLevel(), blockEntity.getBlockPos()));
            ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
            this.itemModelResolver.updateForTopItem(itemStackRenderState, blockEntity.inv.get(i), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, i + j);
            renderState.items.add(itemStackRenderState);
        }

    }
    #endif

    public ShelfBER(BlockEntityRendererProvider.Context ctx) {
        #if MC_VER >= V1_21_9 this.itemModelResolver = ctx.itemModelResolver(); #endif
    }

    #if MC_VER >= V1_21_9
    @Override
    public void submit(ShelfBERS renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState)
    #else
        public void render(#if MC_VER < V1_21_5 ShelfBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay #else ShelfBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, Vec3 cameraPos #endif)
    #endif
    {
        #if MC_VER < V1_21_9
        BlockState state;
        Level world = entity.getLevel();
        if ((state = world.getBlockState(entity.getBlockPos())).getBlock() == Blocks.AIR) return;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        #endif

        #if MC_VER >= V1_19_4
            Quaternionf quaternionf = getRotation(#if MC_VER >= V1_21_9 renderState.facing #else state.getValue(FACING) #endif);
        #else
            Quaternion quaternionf = getRotation(state.getValue(FACING));
        #endif

        int blockSize = 1;
        int itemSize = 1;

        for (int i = 0; i < 4; i++) {
            #if MC_VER >= V1_21_9
            ItemStackRenderState irs = renderState.items.get(i); // HAHA IRS
            #else
            ItemStack stack = entity.inv.get(i);
            #endif
            if(#if MC_VER >= V1_21_9 !irs #else !stack #endif .isEmpty() ) {
                Vec3 pos;
                if (#if MC_VER >= V1_21_9 renderState.fullBlock.get(i) #else stack.getItem() instanceof BlockItem #endif) {
                    pos = blockPositions.get(i);
                } else {
                    pos = itemPositions.get(i);
                }

                poseStack.pushPose();

                poseStack.translate(0.5, 0, 0.5);
                poseStack.mulPose(quaternionf);

                poseStack.translate(-0.5, 0, -0.5);
                poseStack.translate(pos.x, pos.y - (0.40625 * (1 - blockSize)) / 2, pos.z);

                boolean fullBlock = #if MC_VER >= V1_21_9 renderState.fullBlock.get(i); #else stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock().defaultBlockState().isCollisionShapeFullBlock(entity.getLevel(), entity.getBlockPos()); #endif
                float scale = (fullBlock) ? 0.40625f * 2 : 0.40625f;
                poseStack.scale(scale * blockSize, scale * blockSize, scale * blockSize);

                #if MC_VER >= V1_21_9
                    irs.submit(poseStack, nodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                #else
                    itemRenderer.renderStatic(stack, #if MC_VER >= V1_19_4 ItemDisplayContext.FIXED #else ItemTransforms.TransformType.FIXED #endif, packedLight, packedOverlay, poseStack, buffer #if MC_VER >= V1_19_4, entity.getLevel() #endif, 1);
                #endif
                poseStack.popPose();
            }
        }
    }
}