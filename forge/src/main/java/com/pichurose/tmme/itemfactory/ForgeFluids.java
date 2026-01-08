package com.pichurose.tmme.itemfactory;

import com.pichurose.tmme.Constants;
import com.pichurose.tmme.fluid.DigestiveFluidBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ForgeFluids implements CommonFluids.FluidRegistrar {
    private static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Constants.MOD_ID);

    private static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, Constants.MOD_ID);

    private static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);

    // Store registry objects for cross-referencing
    private static final Map<String, RegistryObject<FlowingFluid>> STILL_FLUIDS = new HashMap<>();
    private static final Map<String, RegistryObject<FlowingFluid>> FLOWING_FLUIDS = new HashMap<>();
    private static final Map<String, RegistryObject<Block>> FLUID_BLOCKS = new HashMap<>();
    private static final Map<String, RegistryObject<Item>> FLUID_BUCKETS = new HashMap<>();

    public static void register(IEventBus modEventBus) {
        FLUID_TYPES.register(modEventBus);
        FLUIDS.register(modEventBus);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
    }

    @Override
    public void registerFluid(CommonFluids.FluidInfo info) {
        String name = info.name;

        // All fluids use water textures for transparency
        ResourceLocation stillTexture = new ResourceLocation("minecraft", "block/water_still");
        ResourceLocation flowingTexture = new ResourceLocation("minecraft", "block/water_flow");

        // Register fluid type with color tint
        RegistryObject<FluidType> fluidType = FLUID_TYPES.register(name, () ->
            new FluidType(FluidType.Properties.create()
                    .density(info.density)
                    .viscosity(info.viscosity)
                    .lightLevel(info.lightLevel)
                    .canExtinguish(info.canExtinguish)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)) {
                @Override
                public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
                    final ResourceLocation stillTex = stillTexture;
                    final ResourceLocation flowingTex = flowingTexture;
                    final float[] color = info.tintColor;

                    consumer.accept(new IClientFluidTypeExtensions() {
                        @Override
                        public ResourceLocation getStillTexture() {
                            return stillTex;
                        }

                        @Override
                        public ResourceLocation getFlowingTexture() {
                            return flowingTex;
                        }

                        @Override
                        public int getTintColor() {
                            return 0xFF000000 |
                                   ((int)(color[0] * 255) << 16) |
                                   ((int)(color[1] * 255) << 8) |
                                   (int)(color[2] * 255);
                        }
                    });
                }
            }
        );

        // Register still and flowing fluids
        RegistryObject<FlowingFluid> stillFluid = FLUIDS.register(name, () ->
            new ForgeFlowingFluid.Source(makeFluidProperties(name, fluidType))
        );
        STILL_FLUIDS.put(name, stillFluid);

        RegistryObject<FlowingFluid> flowingFluid = FLUIDS.register("flowing_" + name, () ->
            new ForgeFlowingFluid.Flowing(makeFluidProperties(name, fluidType))
        );
        FLOWING_FLUIDS.put(name, flowingFluid);

        // Register fluid block (use custom block for digestive fluid to add wither effect)
        RegistryObject<Block> fluidBlock;
        if (name.equals("digestive_fluid")) {
            fluidBlock = BLOCKS.register(name, () ->
                new DigestiveFluidBlock(stillFluid.get(), BlockBehaviour.Properties.of()
                        .noCollission()
                        .strength(100.0F)
                        .noLootTable()
                        .lightLevel(state -> info.lightLevel))
            );
        } else {
            fluidBlock = BLOCKS.register(name, () ->
                new LiquidBlock(stillFluid, BlockBehaviour.Properties.of()
                        .noCollission()
                        .strength(100.0F)
                        .noLootTable()
                        .lightLevel(state -> info.lightLevel))
            );
        }
        FLUID_BLOCKS.put(name, fluidBlock);

        // Register bucket item
        RegistryObject<Item> bucketItem = ITEMS.register(name + "_bucket", () ->
            new BucketItem(stillFluid, new Item.Properties()
                    .craftRemainder(Items.BUCKET)
                    .stacksTo(1))
        );
        FLUID_BUCKETS.put(name, bucketItem);

        // Add bucket to creative tab
        ModCreativeTabs.addToTab(() -> new net.minecraft.world.item.ItemStack(bucketItem.get()));
    }

    private ForgeFlowingFluid.Properties makeFluidProperties(String name, RegistryObject<FluidType> fluidType) {
        return new ForgeFlowingFluid.Properties(
                fluidType,
                () -> STILL_FLUIDS.get(name).get(),
                () -> FLOWING_FLUIDS.get(name).get())
                .bucket(() -> FLUID_BUCKETS.get(name).get())
                .block(() -> (LiquidBlock) FLUID_BLOCKS.get(name).get());
    }
}

