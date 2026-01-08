package com.pichurose.tmme.itemfactory;

import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public interface PlatformRegistrar {
    Supplier<Item> registerItem(ResourceLocation id, Supplier<Item> itemSupplier);
    Supplier<Block> registerBlock(ResourceLocation id, Supplier<Block> blockSupplier);

}
