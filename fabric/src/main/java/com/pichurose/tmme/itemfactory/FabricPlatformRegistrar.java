package com.pichurose.tmme.itemfactory;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class FabricPlatformRegistrar implements PlatformRegistrar {
    @Override
    public Supplier<Item> registerItem(ResourceLocation id, Supplier<Item> itemSupplier) {
        Item item = itemSupplier.get();
        Registry.register(BuiltInRegistries.ITEM, id, item);
        return () -> item;
    }

    @Override
    public Supplier<Block> registerBlock(ResourceLocation id, Supplier<Block> blockSupplier) {
        Block block = blockSupplier.get();
        Registry.register(BuiltInRegistries.BLOCK, id, block);
        return () -> block;
    }
}
