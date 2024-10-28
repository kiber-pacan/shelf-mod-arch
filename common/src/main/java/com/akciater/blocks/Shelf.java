package com.akciater.blocks;

import com.akciater.ShelfModCommon;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class Shelf extends BaseEntityBlock implements SimpleWaterloggedBlock {
    #if MC_VER > V1_20_1 public static final MapCodec<Shelf> CODEC = simpleCodec(Shelf::new); #endif
    public static BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");
    public static DirectionProperty FACING = DirectionProperty.create("facing");

    public Shelf(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    public ShelfBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ShelfBlockEntity(pos, state);
    }

    #if MC_VER > V1_20_1
    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
    #endif

    // FUCK MOJANG
    #if MC_VER >= V1_21 protected #else public #endif @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    #if MC_VER >= V1_21 protected @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) #else public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) #endif {
        ShelfBlockEntity entity = (ShelfBlockEntity)level.getChunk(pos).getBlockEntity(pos);
        if (entity != null) {
            ItemStack stack = player.getMainHandItem();
            if (ShelfModCommon.isShelf(stack.getItem())) return InteractionResult.FAIL;

            int slot = getSlot(hit, state.getValue(FACING));

            if (!stack.isEmpty() && entity.inv.get(slot).isEmpty()) {
                entity.inv.set(slot, stack);
                player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            } else if (stack.isEmpty()) {
                player.setItemInHand(InteractionHand.MAIN_HAND, entity.inv.get(slot));
                entity.inv.set(slot, ItemStack.EMPTY);
            } else if (!stack.isEmpty() && !entity.inv.get(slot).isEmpty()) {
                ItemStack temp = stack.copy();
                player.setItemInHand(InteractionHand.MAIN_HAND, entity.inv.get(slot));
                entity.inv.set(slot, temp);
            }

            entity.markDirty();

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    public static int getSlot(BlockHitResult hit, Direction dir) {
        Vec3 pos = hit.getLocation();
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
    #if MC_VER >= V1_21 protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) #else public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) #endif {
        if (!state.is(newState.getBlock())) {
            ShelfBlockEntity entity = (ShelfBlockEntity) level#if MC_VER < V1_21 .getChunk(pos) #endif.getBlockEntity(pos);
            if (entity != null) {
                if (!entity.isEmpty()) {
                    for(int i = 0; i < 4; ++i) {
                        ItemStack itemStack = entity.inv.get(i);
                        if (!itemStack.isEmpty()) {
                            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                        }
                    }

                    entity.inv.clear();
                    entity.setRemoved();

                    level.updateNeighbourForOutputSignal(pos, this);
                }
            }

            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), state.getBlock().asItem().getDefaultInstance());
            super.onRemove(state, level, pos, newState, movedByPiston);
        }
    }

    @Override
    #if MC_VER >= V1_21 protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) #else public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)  #endif {
        Direction dir = state.getValue(FACING);
        switch(dir) {
            case NORTH:
                return Shapes.box(0.0f, 0.0f, 0.5f, 1.0f, 1.0f, 1.0f);
            case SOUTH:
                return Shapes.box(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.5f);
            case EAST:
                return Shapes.box(0.0f, 0.0f, 0.0f, 0.5f, 1.0f, 1.0f);
            case WEST:
                return Shapes.box(0.5f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            default:
                return Shapes.block();
        }
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        FluidState fluidState = context.getLevel().getFluidState(blockPos);
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public @NotNull FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED).add(FACING);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        #if MC_VER >= V1_19_2
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        #endif

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }
}
