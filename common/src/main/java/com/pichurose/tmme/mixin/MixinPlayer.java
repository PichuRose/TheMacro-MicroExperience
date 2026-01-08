package com.pichurose.tmme.mixin;

import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class MixinPlayer {

    @Shadow protected FoodData foodData;

    @Shadow @Final private Abilities abilities;

    @Shadow public abstract boolean isLocalPlayer();
/*
    @Inject(at = @At("HEAD"), method = "causeFoodExhaustion", cancellable = true)
    public void causeFoodExhaustion(float exhaustion, CallbackInfo ci) {
        if (!abilities.invulnerable && !isLocalPlayer()) {
            foodData.addExhaustion(exhaustion);
        }
    }*/
}
