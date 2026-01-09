package com.pichurose.tmme.mixin;

import com.pichurose.tmme.blocks.BouncyBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Shadow public abstract Vec3 getDeltaMovement();
    @Shadow public abstract void setDeltaMovement(Vec3 vec3);
    @Shadow public abstract boolean isSuppressingBounce();
    @Shadow public abstract Level level();
    @Shadow public abstract BlockPos getOnPos();
    @Shadow private float fallDistance;

    private boolean wasOnGround = false;
    private double lastYVelocity = 0.0D;

    @Inject(method = "move", at = @At("HEAD"))
    private void captureVelocityBeforeMove(CallbackInfo ci) {
        lastYVelocity = getDeltaMovement().y;
    }

    @Inject(method = "checkFallDamage", at = @At("HEAD"))
    private void handleBouncyBlockBounce(double y, boolean onGround, BlockState state, BlockPos pos, CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;

        if (onGround && !wasOnGround && !isSuppressingBounce()) {
            BlockState blockBelow = level().getBlockState(pos);

            if (blockBelow.getBlock() instanceof BouncyBlock bouncyBlock) {
                // Calculate bounce based on the velocity we had when landing
                if (lastYVelocity < -0.1D) {
                    double bounceFactor = 1.0D; // Will be adjusted by the block
                    double bounceVelocity = -lastYVelocity * bounceFactor;

                    Vec3 currentVelocity = getDeltaMovement();
                    setDeltaMovement(new Vec3(currentVelocity.x, bounceVelocity, currentVelocity.z));
                    entity.hasImpulse = true;
                }
            }
        }

        wasOnGround = onGround;
    }
}

