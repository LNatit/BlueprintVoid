package com.lnatit.bpvoid.mixin.impl.create;

import com.lnatit.bpvoid.mixin.api.minecraft.BPVoidModelBlockRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.content.schematics.client.SchematicRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SchematicRenderer.class)
public class SchematicRendererMixin
{
    @Redirect(
            method = "drawLayer",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/block/ModelBlockRenderer;tesselateBlock(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;JILnet/minecraftforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;)V"
            ),
            remap = false
    )
    private void bpvoid$drawLayer(
            ModelBlockRenderer renderer,
            BlockAndTintGetter pLevel,
            BakedModel pModel,
            BlockState pState,
            BlockPos pPos,
            PoseStack pPoseStack,
            VertexConsumer pConsumer,
            boolean pCheckSides,
            RandomSource pRandom,
            long pSeed,
            int pPackedOverlay,
            ModelData modelData,
            RenderType renderType
    )
    {
        // 1.00390625f
        ((BPVoidModelBlockRenderer) renderer).tesselateBlockScaled(
                pLevel,
                pModel,
                pState,
                pPos,
                pPoseStack,
                pConsumer,
                pCheckSides,
                pRandom,
                pSeed,
                pPackedOverlay,
                modelData,
                renderType,
                1.0009765625f
        );
    }
}
