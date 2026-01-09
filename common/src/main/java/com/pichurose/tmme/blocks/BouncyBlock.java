package com.pichurose.tmme.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BouncyBlock extends Block {
    private final double bounceMultiplier;

    public BouncyBlock(BlockBehaviour.Properties properties, double bounceMultiplier) {
        super(properties);
        this.bounceMultiplier = bounceMultiplier;
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity.isSuppressingBounce()) {
            super.fallOn(level, state, pos, entity, fallDistance);
        } else {
            // No fall damage like slime block
            entity.causeFallDamage(fallDistance, 0.0F, level.damageSources().fall());

            // Calculate bounce velocity from fall distance
            if (fallDistance > 0.0F) {
                double bounceFactor = entity instanceof LivingEntity ? bounceMultiplier : bounceMultiplier * 0.8D;

                // Calculate upward velocity based on fall distance
                // v^2 = 2 * g * h, so v = sqrt(2 * g * h)
                // In Minecraft, effective gravity acceleration is about 0.08 per tick
                double bounceVelocity = Math.sqrt(fallDistance * 2.0D * 0.08D) * bounceFactor;

                Vec3 vec3 = entity.getDeltaMovement();
                entity.setDeltaMovement(vec3.x, bounceVelocity, vec3.z);

                // Lift entity slightly off ground and mark for velocity update
                entity.setPos(entity.getX(), entity.getY() + 0.01D, entity.getZ());
                entity.setOnGround(false);
                entity.hasImpulse = true;
                entity.hurtMarked = true;
            }
        }
    }
}

