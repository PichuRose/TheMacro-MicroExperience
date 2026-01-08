package com.pichurose.tmme.itemfactory;

import com.pichurose.tmme.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ForgePlatformRegistrar implements PlatformRegistrar {
    private final DeferredRegister<Item> ITEMS;
    private final DeferredRegister<Block> BLOCKS;

    public ForgePlatformRegistrar(IEventBus modEventBus) {
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MOD_ID);
        BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MOD_ID);

        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
    }

    @Override
    public Supplier<Item> registerItem(ResourceLocation id, Supplier<Item> itemSupplier) {
        return ITEMS.register(id.getPath(), itemSupplier);
    }

    @Override
    public Supplier<Block> registerBlock(ResourceLocation id, Supplier<Block> blockSupplier) {
        return BLOCKS.register(id.getPath(), blockSupplier);
    }
}
