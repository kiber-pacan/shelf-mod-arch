package com.akciater.blocks;

import com.akciater.ShelfModCommon;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

#if MC_VER > V1_19_2
import net.minecraft.util.RandomSource;
#endif
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
#if MC_VER < V1_21_3
    import net.minecraft.world.level.block.state.properties.DirectionProperty;
#endif
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FloorShelf extends BaseEntityBlock implements SimpleWaterloggedBlock {
    #if MC_VER > V1_20_1 public static final MapCodec<Shelf> CODEC = simpleCodec(Shelf::new); #endif
    public static BooleanProperty WATERLOGGED = BooleanProperty.create("waterlogged");
    #if MC_VER >= V1_21_3
        public static EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    #else
        public static DirectionProperty FACING = DirectionProperty.create("facing");
    #endif


    public FloorShelf(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    public FloorShelfBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FloorShelfBlockEntity(pos, state);
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
        FloorShelfBlockEntity blockEntity = (FloorShelfBlockEntity) level.getChunk(pos).getBlockEntity(pos);
        if (blockEntity != null) {
            ItemStack stack = player.getMainHandItem();
            if (ShelfModCommon.isShelf(stack.getItem())) return InteractionResult.FAIL;

            int slot = getSlot(hit, state.getValue(FACING));

            if (!stack.isEmpty() && blockEntity.inv.get(slot).isEmpty()) {
                blockEntity.inv.set(slot, stack);
                player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            } else if (stack.isEmpty()) {
                player.setItemInHand(InteractionHand.MAIN_HAND, blockEntity.inv.get(slot));
                blockEntity.inv.set(slot, ItemStack.EMPTY);
            }

            blockEntity.markDirty();

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    public static int getSlot(BlockHitResult hit, Direction dir) {
        Vec3 pos = hit.getLocation();
        BlockPos blockPos = hit.getBlockPos();
        double x = Math.abs(pos.x - blockPos.getX());
        double z = Math.abs(pos.z - blockPos.getZ());
        int slot = 0;

        if (x > 0.5 && z > 0.5) slot = 3;
        else if (z > 0.5) slot += 2;
        else if (x > 0.5) slot += 1;

        return slot;
    }

    @Override
    #if MC_VER >= V1_21 protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) #else public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)  #endif {
        Direction dir = state.getValue(FACING);
        switch(dir) {
            case NORTH, SOUTH:
                return Shapes.or(
                        Shapes.box(0.0f, 0.0f, 0.0f, 1f, 0.3125f, 1f),
                        Shapes.box(0.0f, 0.3125f, 0.3125f, 0.0625f, 0.5f, 0.6875f),
                        Shapes.box(0.9375f, 0.3125f, 0.3125f, 1.0f, 0.5f, 0.6875f)
                );
            case EAST, WEST:
                return Shapes.or(
                        Shapes.box(0.0f, 0.0f, 0.0f, 1f, 0.3125f, 1f),
                        Shapes.box(0.3125f, 0.3125f, 0.0f, 0.6875f, 0.5f, 0.0625f),
                        Shapes.box(0.3125f, 0.3125f, 0.9375f, 0.6875f, 0.5f, 1.0f)

                );
            default:
                return Shapes.block();
        }
    }

    // Drop items on break
    #if MC_VER < V1_21_5
    @Override
    #if MC_VER >= V1_21  protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) #else public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) #endif {
        if (!state.is(newState.getBlock())) {
            FloorShelfBlockEntity entity = (FloorShelfBlockEntity) level#if MC_VER < V1_21 .getChunk(pos) #endif.getBlockEntity(pos);
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
    #endif

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

    #if MC_VER < V1_21_3
    @Override
    public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos)  {
        if (state.getValue(WATERLOGGED)) {
            level #if MC_VER <= V1_17_1 .getLiquidTicks() #endif.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, direction, neighborState, level, pos,  neighborPos);
    }
    #else
    @Override
    public @NotNull BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos arg6, BlockState arg7, RandomSource arg8) {
        if (blockState.getValue(WATERLOGGED)) {
            scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
        }

        return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, arg6, arg7, arg8);
    }
    #endif
}
