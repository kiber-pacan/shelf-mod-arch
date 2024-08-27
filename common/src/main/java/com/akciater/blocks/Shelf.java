package com.akciater.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class Shelf extends HorizontalFacingBlock implements Waterloggable, BlockEntityProvider {
    public static BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public Shelf(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false).with(Properties.HORIZONTAL_FACING, Direction.NORTH));
    }

    #if MC_VER >= V1_20_4
    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return null;
    }
    #endif


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player #if MC_VER < V1_21 , Hand hand  #endif, BlockHitResult hit) {
        ShelfBlockEntity blockEntity = (ShelfBlockEntity)world.getChunk(pos).getBlockEntity(pos);
        if (blockEntity != null) {
            ItemStack stack = player.getMainHandStack();
            int slot = getSlot(hit, state.get(Properties.HORIZONTAL_FACING));
            if (!stack.isEmpty() && blockEntity.inventory.get(slot).isEmpty()) {
                blockEntity.inventory.set(slot, stack);
                player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
            } else if (stack.isEmpty()) {
                player.setStackInHand(Hand.MAIN_HAND, blockEntity.inventory.get(slot));
                blockEntity.inventory.set(slot, ItemStack.EMPTY);
            }
            world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            blockEntity.markDirty();
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    public static int getSlot(BlockHitResult hit, Direction dir) {
        Vec3d pos = hit.getPos();
        BlockPos blockPos = hit.getBlockPos();

        double x = Math.abs(pos.x - blockPos.getX());
        double y = Math.abs(pos.y - blockPos.getY());
        double z = Math.abs(pos.z - blockPos.getZ());

        int slot = 0;

        if (dir == Direction.EAST)
            if (z > 0.5) slot += 1;

        if (dir == Direction.WEST)
            if (z < 0.5) slot += 1;

        if (dir == Direction.NORTH)
            if (x > 0.5) slot += 1;

        if (dir == Direction.SOUTH)
            if (x < 0.5) slot += 1;

        if (y > 0.5) slot += 2;

        return slot;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            if (world.getBlockEntity(pos) instanceof ShelfBlockEntity entity) {
                for (int i = 0; i < entity.inventory.size(); i++) {

                    ItemStack itemStack = entity.inventory.get(i);

                    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);

                    world.updateComparators(pos, this);
                }
                entity.clear();
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView blockView, BlockPos pos, ShapeContext context) {
        Direction dir = state.get(FACING);
        switch(dir) {
            case NORTH:
                return VoxelShapes.cuboid(0.0f, 0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
            case SOUTH:
                return VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f);
            case EAST:
                return VoxelShapes.cuboid(0.0f, 0.0f, 0.0f, 0.5f, 1.0f, 1.0f);
            case WEST:
                return VoxelShapes.cuboid(0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            default:
                return VoxelShapes.fullCube();
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        #if MC_VER >= V1_19_4
        return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
        #else
        return super.getPlacementState(ctx).with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite());
        #endif
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED).add(Properties.HORIZONTAL_FACING);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        #if MC_VER >= V1_19_4
        if (state.get(WATERLOGGED)) world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        #endif

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShelfBlockEntity(pos, state);
    }
}
