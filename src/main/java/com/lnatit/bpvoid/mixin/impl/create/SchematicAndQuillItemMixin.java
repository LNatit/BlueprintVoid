package com.lnatit.bpvoid.mixin.impl.create;

import com.lnatit.bpvoid.BlueprintVoid;
import com.simibubi.create.content.schematics.SchematicAndQuillItem;
import com.simibubi.create.foundation.utility.RegisteredObjects;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SchematicAndQuillItem.class)
public class SchematicAndQuillItemMixin {
    @Redirect(
            method = "lambda$replaceStructureVoidWithAir$0",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/nbt/CompoundTag;putString(Ljava/lang/String;Ljava/lang/String;)V"
            ),
            remap = false
    )
    private static void bpvoid$replaceStructureVoidWithAir(CompoundTag tag, String pKey, String pValue) {
        String bpvoid = RegisteredObjects.getKeyOrThrow(BlueprintVoid.BLUEPRINT_VOID.get()).toString();
        tag.putString(pKey, BlueprintVoid.Config.replaceVoid.get() ? bpvoid : pValue);
    }
}
