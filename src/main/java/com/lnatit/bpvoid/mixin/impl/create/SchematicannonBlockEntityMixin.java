package com.lnatit.bpvoid.mixin.impl.create;

import com.lnatit.bpvoid.BlueprintVoid;
import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.lnatit.bpvoid.BlueprintVoid.LOGGER;

@Mixin(SchematicannonBlockEntity.class)
public class SchematicannonBlockEntityMixin {
    @Inject(
            method = "shouldPlace",
            at = @At("RETURN"),
            cancellable = true
    )
    private void bpvoid$shouldPlace(BlockPos pos, BlockState state, BlockEntity be, BlockState toReplace, BlockState toReplaceOther, boolean isNormalCube, CallbackInfoReturnable<Boolean> cir) {
        Block block = state.getBlock();
        if (cir.getReturnValue() && (
                // the structure void block has already been
                // replaced by air when the schematic is saved
//                block.equals(Blocks.STRUCTURE_VOID) ||
                block.equals(BlueprintVoid.BLUEPRINT_VOID.get())
        )) {
            LOGGER.info("找到你了，美味的小孩~");
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
