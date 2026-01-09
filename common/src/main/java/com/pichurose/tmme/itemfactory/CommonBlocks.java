package com.pichurose.tmme.itemfactory;

import com.pichurose.tmme.Constants;
import com.pichurose.tmme.blocks.BouncyBlock;
import com.pichurose.tmme.blocks.SlimyBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class CommonBlocks {
    // Hold the first block item for the creative tab icon
    private static Supplier<Item> firstBlockItem = null;

    public static void register(PlatformRegistrar registrar) {
        registerBlock(registrar, "soft_flesh_block");
        registerBlock(registrar, "hard_flesh_block");
        registerSlimyBlock(registrar, "sturdy_genital_fluid");
        registerBlock(registrar, "poop");
        registerBouncyBlock(registrar, "soft_fabric", 0.8D);
    }

    public static Supplier<Item> getFirstBlockItem() {
        return firstBlockItem;
    }

    public static void registerBlock(PlatformRegistrar registrar, String path){
        ResourceLocation id = new ResourceLocation(Constants.MOD_ID, path);

        // Register the block and get a supplier to it
        Supplier<Block> blockSupplier = () -> new Block(BlockBehaviour.Properties.of());
        Supplier<Block> registeredBlock = registrar.registerBlock(id, blockSupplier);

        // Register the BlockItem using the supplier (for deferred registration compatibility)
        Supplier<Item> blockItem = registrar.registerItem(id, () -> new BlockItem(registeredBlock.get(), new Item.Properties()));

        // Store the first block item for the creative tab icon
        if (firstBlockItem == null) {
            firstBlockItem = blockItem;
        }

        // Add to creative tab
        ModCreativeTabs.addToTab(() -> new net.minecraft.world.item.ItemStack(blockItem.get()));
    }

    public static void registerSlimyBlock(PlatformRegistrar registrar, String path) {
        ResourceLocation id = new ResourceLocation(Constants.MOD_ID, path);

        // Register the slime block with slime properties
        Supplier<Block> blockSupplier = () -> new SlimyBlock(BlockBehaviour.Properties.of().friction(0.8F).sound(SoundType.SLIME_BLOCK));
        Supplier<Block> registeredBlock = registrar.registerBlock(id, blockSupplier);

        // Register the BlockItem using the supplier
        Supplier<Item> blockItem = registrar.registerItem(id, () -> new BlockItem(registeredBlock.get(), new Item.Properties()));

        // Store the first block item for the creative tab icon
        if (firstBlockItem == null) {
            firstBlockItem = blockItem;
        }

        // Add to creative tab
        ModCreativeTabs.addToTab(() -> new net.minecraft.world.item.ItemStack(blockItem.get()));
    }

    public static void registerBouncyBlock(PlatformRegistrar registrar, String path, double bounceMultiplier) {
        ResourceLocation id = new ResourceLocation(Constants.MOD_ID, path);

        // Register the bouncy block
        Supplier<Block> blockSupplier = () -> new BouncyBlock(BlockBehaviour.Properties.of(), bounceMultiplier);
        Supplier<Block> registeredBlock = registrar.registerBlock(id, blockSupplier);

        // Register the BlockItem using the supplier
        Supplier<Item> blockItem = registrar.registerItem(id, () -> new BlockItem(registeredBlock.get(), new Item.Properties()));

        // Store the first block item for the creative tab icon
        if (firstBlockItem == null) {
            firstBlockItem = blockItem;
        }

        // Add to creative tab
        ModCreativeTabs.addToTab(() -> new net.minecraft.world.item.ItemStack(blockItem.get()));
    }
}
