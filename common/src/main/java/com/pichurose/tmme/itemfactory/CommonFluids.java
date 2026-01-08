package com.pichurose.tmme.itemfactory;

import com.pichurose.tmme.Constants;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class CommonFluids {

    // List of all fluids to register
    private static final List<FluidInfo> FLUIDS_TO_REGISTER = new ArrayList<>();

    static {
        // Saliva Fluid (Clear/white water-like)
        FLUIDS_TO_REGISTER.add(new FluidInfo("saliva", 1000, 1000, false, 0,
                new float[]{0.95f, 0.95f, 1.0f})); // Very light blue-white tint for clarity

        // Digestive Fluid (Transparent green water)
        FLUIDS_TO_REGISTER.add(new FluidInfo("digestive_fluid", 1000, 1000, false, 0,
                new float[]{0.3f, 0.9f, 0.3f})); // Bright green water tint

        // Wet Genital Fluid (Clear/white water-like)
        FLUIDS_TO_REGISTER.add(new FluidInfo("wet_genital_fluid", 1000, 1000, false, 0,
                new float[]{1.0f, 1.0f, 1.0f})); // Pure white tint

        // Urine (Yellow water)
        FLUIDS_TO_REGISTER.add(new FluidInfo("urine", 1000, 1000, false, 0,
                new float[]{1.0f, 0.95f, 0.2f})); // Yellow tint
    }

    public static void register(FluidRegistrar registrar) {
        for (FluidInfo info : FLUIDS_TO_REGISTER) {
            registrar.registerFluid(info);
        }
    }

    public static List<FluidInfo> getAllFluids() {
        return FLUIDS_TO_REGISTER;
    }

    // Fluid information holder
    public static class FluidInfo {
        public final String name;
        public final int viscosity;
        public final int density;
        public final boolean canExtinguish;
        public final int lightLevel;
        public final float[] tintColor; // RGB 0-1

        public FluidInfo(String name, int viscosity, int density, boolean canExtinguish,
                        int lightLevel, float[] tintColor) {
            this.name = name;
            this.viscosity = viscosity;
            this.density = density;
            this.canExtinguish = canExtinguish;
            this.lightLevel = lightLevel;
            this.tintColor = tintColor;
        }

        public ResourceLocation getId() {
            return new ResourceLocation(Constants.MOD_ID, name);
        }
    }

    // Platform-specific fluid registrar interface
    public interface FluidRegistrar {
        void registerFluid(FluidInfo info);
    }
}

