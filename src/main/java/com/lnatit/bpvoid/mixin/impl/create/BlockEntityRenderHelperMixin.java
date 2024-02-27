package com.lnatit.bpvoid.mixin.impl.create;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.render.BlockEntityRenderHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockEntityRenderHelper.class)
public class BlockEntityRenderHelperMixin
{
//    @Redirect(
//            method = "renderBlockEntities(Lnet/minecraft/world/level/Level;Lcom/jozufozu/flywheel/core/virtual/VirtualRenderWorld;Ljava/lang/Iterable;Lcom/mojang/blaze3d/vertex/PoseStack;Lorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;F)V",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lcom/jozufozu/flywheel/util/transform/TransformStack;cast(Lcom/mojang/blaze3d/vertex/PoseStack;)Lcom/jozufozu/flywheel/util/transform/TransformStack;"
//            )
//    )
//    private static TransformStack bpvoid$renderBlockEntities(PoseStack ms)
//    {
//        // Maybe useless
//        return TransformStack.cast(ms).scale(1.00390625f);
//    }
}
