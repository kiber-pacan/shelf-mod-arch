
package com.akciater.blocks;

import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

#if MC_VER >= V1_19_4
    import net.minecraft.registry.RegistryWrapper;
    import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
    import net.minecraft.network.packet.Packet;
    import net.minecraft.network.listener.ClientPlayPacketListener;
#else
    import net.minecraft.network.Packet;
    import net.minecraft.network.listener.ClientPlayPacketListener;
    import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
#endif

import static com.akciater.ShelfModCommon.SHELF_BLOCK_ENTITY;

public class ShelfBlockEntity extends BlockEntity implements BlockEntityProvider {
    public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);

    public ShelfBlockEntity(BlockPos pos, BlockState state) {
        super(SHELF_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(#if MC_VER >= V1_21 RegistryWrapper.WrapperLookup registryLookup #endif ) {
        return createNbt(
            #if MC_VER >= V1_21
                registryLookup
            #endif
        );
    }

    @Override
    public void readNbt(
            NbtCompound nbt
            #if MC_VER >= V1_21
            , RegistryWrapper.WrapperLookup registryLookup
            #endif
    ) {
        super.readNbt(
                nbt
                #if MC_VER >= V1_21
                , registryLookup
                #endif
        );
        inventory.clear();
        Inventories.readNbt(
                nbt,
                inventory
                #if MC_VER >= V1_21
                , registryLookup
                #endif
        );
        markDirty();
    }



    @Override
    public void writeNbt(
            NbtCompound nbt
            #if MC_VER >= V1_21
            , RegistryWrapper.WrapperLookup registryLookup
            #endif
    ) {
        super.writeNbt(
                nbt
                #if MC_VER >= V1_21
                , registryLookup
                #endif
        );
        Inventories.writeNbt(
                nbt,
                inventory
                #if MC_VER >= V1_21
                , registryLookup
                #endif
        );
    }

    @Override
    public void markDirty() {
        if (this.world != null) {
            markDirtyInWorld(this.world, this.pos, this.getCachedState());
        }
    }

    protected void markDirtyInWorld(World world, BlockPos pos, BlockState state) {
        world.markDirty(pos);

        if (!world.isClient()) {
            ((ServerWorld) world).getChunkManager().markForUpdate(pos);
        }
    }

    public void clear() {
        inventory.clear();
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShelfBlockEntity(pos, state);
    }
}
