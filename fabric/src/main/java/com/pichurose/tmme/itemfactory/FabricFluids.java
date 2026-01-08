package com.pichurose.tmme.itemfactory;

import com.pichurose.tmme.Constants;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class FabricFluids implements CommonFluids.FluidRegistrar {

    @Override
    public void registerFluid(CommonFluids.FluidInfo info) {
        String name = info.name;

        // For now, just register bucket items as placeholders
        // Full fluid implementation on Fabric requires additional complex setup

        // Register placeholder bucket item
        Item bucketItem = Registry.register(
                BuiltInRegistries.ITEM,
                new ResourceLocation(Constants.MOD_ID, name + "_bucket"),
                new Item(new Item.Properties()
                        .craftRemainder(Items.BUCKET)
                        .stacksTo(1))
        );

        // Add bucket to creative tab
        ModCreativeTabs.addToTab(() -> new net.minecraft.world.item.ItemStack(bucketItem));

        Constants.LOG.info("Registered fluid bucket: {}", name);
    }
}

