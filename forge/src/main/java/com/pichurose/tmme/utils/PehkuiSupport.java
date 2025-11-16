package com.pichurose.tmme.utils;

import com.pichurose.tmme.CommonClass;
import net.minecraft.resources.ResourceLocation;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleModifier;
import virtuoel.pehkui.api.ScaleRegistries;
import virtuoel.pehkui.api.ScaleType;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class PehkuiSupport {
    public static final AtomicReference<ScaleModifier> TMMEScaleModifier = new AtomicReference<>();
    public static final AtomicReference<ScaleType> TMMEScaleType = new AtomicReference<>();

    public static void setup() {
        //Loggers.SAC_LOGGER.info("Pehkui detected; enabling support");

        ScaleModifier modifier = new ScaleModifier() {
            @Override
            public float modifyScale(ScaleData scaleData, float modifiedScale, float delta) {
                return TMMEScaleType.get().getScaleData(scaleData.getEntity()).getScale(delta) * modifiedScale;
            }
        };
        ScaleRegistries.SCALE_MODIFIERS.put(new ResourceLocation(CommonClass.MODID+":resize"), modifier);
        TMMEScaleModifier.set(modifier);
        ScaleType tmmeScaleType = ScaleType.Builder.create()
                .affectsDimensions()
                .addDependentModifier(TMMEScaleModifier.get())
                .build();
        ScaleRegistries.SCALE_TYPES.put(new ResourceLocation(CommonClass.MODID+":resize"), tmmeScaleType);
        Optional<ScaleType> baseType = getType("base");
        //Optional<ScaleType> attackType = getType("attack");
        //Optional<ScaleType> defenseType = getType("defense");
        Optional<ScaleType> miningspeedType = getType("mining_speed");
        Optional<ScaleType> knockbackType = getType("knockback");
        //noinspection OptionalIsPresent
        if (baseType.isPresent())
            baseType.get().getDefaultBaseValueModifiers().add(modifier);
        //noinspection OptionalIsPresent
        if (miningspeedType.isPresent()){
            miningspeedType.get().getDefaultBaseValueModifiers().add(modifier);
        }
        //noinspection OptionalIsPresent
        if (knockbackType.isPresent()){
            knockbackType.get().getDefaultBaseValueModifiers().add(modifier);
        }
        TMMEScaleType.set(tmmeScaleType);
    }

    // using optional to prevent accidental class loading
    public static Optional<ScaleType> getType(String name) {
        return Optional.of(ScaleRegistries.getEntry(ScaleRegistries.SCALE_TYPES, new ResourceLocation("pehkui", name)));
    }
}