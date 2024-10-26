
package com.akciater.blocks;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

#if MC_VER >= V1_19_4

#else
    import net.minecraft.network.Packet;
    import net.minecraft.network.listener.ClientPlayPacketListener;
    import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
#endif

import static com.akciater.ShelfModCommon.SHELF_BLOCK_ENTITY;

public class ShelfBlockEntity extends BlockEntity{
    public NonNullList<ItemStack> inv;

    public ShelfBlockEntity(BlockPos pos, BlockState blockState) {
        super(SHELF_BLOCK_ENTITY.get(), pos, blockState);
        inv = NonNullList.withSize(4, ItemStack.EMPTY);
    }

    // Load nbt data shit
    #if MC_VER >= V1_21 protected #else public #endif void #if MC_VER >= V1_21 loadAdditional #else load #endif(CompoundTag compoundTag#if MC_VER >= V1_21 , HolderLookup.Provider provider#endif) {
        #if MC_VER >= V1_21 super.loadAdditional(compoundTag, provider); #else super.load(compoundTag); #endif
        ContainerHelper.loadAllItems(compoundTag, this.inv #if MC_VER >= V1_21 , provider #endif);
    }

    // Save nbt data
    #if MC_VER >= V1_21 protected #else public @NotNull #endif  #if MC_VER < V1_18_2 CompoundTag save #else void saveAdditional #endif (CompoundTag compoundTag#if MC_VER >= V1_21 , HolderLookup.Provider provider#endif) {
        super.#if MC_VER < V1_18_2 save #else saveAdditional #endif(compoundTag #if MC_VER >= V1_21 , provider #endif);

        ContainerHelper.saveAllItems(compoundTag, this.inv #if MC_VER >= V1_21 , provider #endif);

        #if MC_VER < V1_18_2
        return compoundTag;
        #endif
    }

    /* At this point i just wanna fucking kill myself
    for god's sake don't ever ever ever forget to add these stupid methods*/
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        #if MC_VER < V1_18_2
        return new ClientboundBlockEntityDataPacket(this.worldPosition, -1, this.save(new CompoundTag()));
        #else
        return ClientboundBlockEntityDataPacket.create(this);
        #endif
    }

    public @NotNull CompoundTag getUpdateTag(#if MC_VER >= V1_21 HolderLookup.Provider provider #endif) {
        return #if MC_VER < V1_18_2 this.save(new CompoundTag()) #elif MC_VER >= V1_21 this.saveCustomOnly(provider) #else this.saveWithoutMetadata() #endif;
    }

    // Mark dirty so client get synced with server
    public void markDirty() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public boolean isEmpty() {
        for (ItemStack itemStack : this.inv) {
            if (!itemStack.isEmpty()) return false;
        }

        return true;
    }
}
