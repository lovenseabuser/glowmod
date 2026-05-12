package net.glowmod.client.mixin;

import net.glowmod.client.GlowMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityGlowMixin {

    @Inject(method = "isGlowing", at = @At("RETURN"), cancellable = true)
    private void forceGlow(CallbackInfoReturnable<Boolean> cir) {
        Entity self = (Entity)(Object)this;
        MinecraftClient client = MinecraftClient.getInstance();
        if (GlowMod.glowEnabled && self instanceof PlayerEntity && self != client.player) {
            cir.setReturnValue(true);
        }
    }
}
