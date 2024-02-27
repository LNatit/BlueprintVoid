package com.lnatit.bpvoid.mixin.impl.minecraft;

import com.lnatit.bpvoid.mixin.api.minecraft.BPVoidModelBlockRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.ModelData;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ModelBlockRenderer.class)
public class ModelBlockRendererMixin implements BPVoidModelBlockRenderer
{
    @Override
    public void tesselateBlockScaled(BlockAndTintGetter pLevel, BakedModel pModel, BlockState pState, BlockPos pPos, PoseStack pPoseStack, VertexConsumer pConsumer, boolean pCheckSides, RandomSource pRandom, long pSeed, int pPackedOverlay, ModelData modelData, RenderType renderType, float scale)
    {
        boolean flag = Minecraft.useAmbientOcclusion() && pState.getLightEmission(pLevel, pPos) == 0 && pModel.useAmbientOcclusion(pState, renderType);
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        pPoseStack.translate(vec3.x, vec3.y, vec3.z);
        // scale & transform
        pPoseStack.scale(scale, scale, scale);
        float d = (1.0f - scale) / (2.0f * scale);
        pPoseStack.translate(d, d, d);

        try {
            if (flag) {
                ((ModelBlockRenderer) (Object) this).tesselateWithAO(pLevel, pModel, pState, pPos, pPoseStack, pConsumer, pCheckSides, pRandom, pSeed, pPackedOverlay, modelData, renderType);
            } else {
                ((ModelBlockRenderer) (Object) this).tesselateWithoutAO(pLevel, pModel, pState, pPos, pPoseStack, pConsumer, pCheckSides, pRandom, pSeed, pPackedOverlay, modelData, renderType);
            }

        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.forThrowable(throwable, "Tesselating block model scaled (Overwritten by bpvoid)");
            CrashReportCategory crashreportcategory = crashreport.addCategory("Block model being tesselated");
            CrashReportCategory.populateBlockDetails(crashreportcategory, pLevel, pPos, pState);
            crashreportcategory.setDetail("Using AO", flag);
            throw new ReportedException(crashreport);
        }
    }
}
