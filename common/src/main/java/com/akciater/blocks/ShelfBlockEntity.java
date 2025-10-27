
package com.akciater.blocks;



import net.minecraft.core.BlockPos;
#if MC_VER >= V1_21 import net.minecraft.core.HolderLookup; #endif
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
#if MC_VER >= V1_21_5 import net.minecraft.world.Containers; #endif
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
#if MC_VER >= V1_21_6
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
#endif
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import static com.akciater.ShelfModCommon.SHELF_BLOCK_ENTITY;

public class ShelfBlockEntity extends BlockEntity {
    public NonNullList<ItemStack> inv;

    public ShelfBlockEntity(BlockPos pos, BlockState blockState) {
        super(SHELF_BLOCK_ENTITY #if MC_VER < V1_21_3 .get() #endif, pos, blockState);
        inv = NonNullList.withSize(4, ItemStack.EMPTY);
    }

    // Load nbt data shit
    #if MC_VER >= V1_21 protected #else public #endif void #if MC_VER >= V1_21 loadAdditional #else load #endif(#if MC_VER <= V1_21_5 CompoundTag compoundTag #if MC_VER >= V1_21 , HolderLookup.Provider provider#endif #else ValueInput input #endif) {
        #if MC_VER >= V1_21 super.loadAdditional(#if MC_VER <= V1_21_5 compoundTag, provider #else input #endif); #else super.load(compoundTag); #endif
        ContainerHelper.loadAllItems(#if MC_VER <= V1_21_5 compoundTag, this.inv #if MC_VER >= V1_21, provider #endif #else input, this.inv #endif);
    }

    // Save nbt data
    #if MC_VER >= V1_21 protected #else public @NotNull #endif  #if MC_VER < V1_18_2 CompoundTag save #else void saveAdditional #endif (#if MC_VER <= V1_21_5 CompoundTag compoundTag#if MC_VER >= V1_21 , HolderLookup.Provider provider #endif #else ValueOutput output #endif) {
        super.#if MC_VER < V1_18_2 save #else saveAdditional #endif(#if MC_VER <= V1_21_5 compoundTag #if MC_VER >= V1_21 , provider #endif #else output #endif);

        ContainerHelper.saveAllItems(#if MC_VER <= V1_21_5 compoundTag, this.inv #if MC_VER >= V1_21 , provider #endif #else output, this.inv #endif);

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

    #if MC_VER >= V1_21_5
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
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

                level.updateNeighbourForOutputSignal(pos, state.getBlock());
            }
        }

        super.preRemoveSideEffects(pos, state);
    }
    #endif
}
