package com.lnatit.bpvoid;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import javax.annotation.ParametersAreNonnullByDefault;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BPVoidBlock extends Block implements IWrenchable
{
    public BPVoidBlock()
    {
        super(
                Properties.of()
                          .mapColor(MapColor.NONE)
                          .replaceable()
                          .noCollission()
//                            .sound(SoundType.SCAFFOLDING)
//                            .dynamicShape()
                          .isValidSpawn((state, getter, pos, entityType) -> false)
                          .pushReaction(PushReaction.DESTROY)
                          .isRedstoneConductor((state, getter, pos) -> false)
        );
    }

    @Override
    public float getShadeBrightness(BlockState pState, BlockGetter pLevel, BlockPos pPos)
    {
        return 1.0F;
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context)
    {
        return InteractionResult.FAIL;
    }

    @FieldsAreNonnullByDefault
    @ParametersAreNonnullByDefault
    @MethodsReturnNonnullByDefault
    public static class BPVoidItem extends BlockItem
    {
        public BPVoidItem() {
            super(BlueprintVoid.BLUEPRINT_VOID.get(), new Properties().stacksTo(1));
        }

        @Override
        public InteractionResult place(BlockPlaceContext pContext) {
            if (!this.getBlock().isEnabled(pContext.getLevel().enabledFeatures())) {
                return InteractionResult.FAIL;
            } else if (!pContext.canPlace()) {
                return InteractionResult.FAIL;
            } else {
                BlockPlaceContext blockplacecontext = this.updatePlacementContext(pContext);
                if (blockplacecontext == null) {
                    return InteractionResult.FAIL;
                } else {
                    BlockState blockstate = this.getPlacementState(blockplacecontext);
                    if (blockstate == null) {
                        return InteractionResult.FAIL;
                    } else if (!this.placeBlock(blockplacecontext, blockstate)) {
                        return InteractionResult.FAIL;
                    } else {
                        BlockPos blockpos = blockplacecontext.getClickedPos();
                        Level level = blockplacecontext.getLevel();
                        Player player = blockplacecontext.getPlayer();
                        ItemStack itemstack = blockplacecontext.getItemInHand();
                        BlockState blockstate1 = level.getBlockState(blockpos);
                        if (blockstate1.is(blockstate.getBlock()) && player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, blockpos, itemstack);
                        }

                        SoundType soundtype = blockstate1.getSoundType(level, blockpos, pContext.getPlayer());
                        level.playSound(player, blockpos, this.getPlaceSound(blockstate1, level, blockpos, pContext.getPlayer()), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                        level.gameEvent(GameEvent.BLOCK_PLACE, blockpos, GameEvent.Context.of(player, blockstate1));
                        // won't shrink
//                        if (player == null || !player.getAbilities().instabuild) {
//                            itemstack.shrink(1);
//                        }

                        return InteractionResult.sidedSuccess(level.isClientSide);
                    }
                }
            }
        }
    }
}
