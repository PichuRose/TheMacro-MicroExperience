package com.pichurose.tmme.itemfactory;

import com.pichurose.tmme.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModCreativeTabs {
    // Store items to be added to the creative tab
    private static final List<Supplier<ItemStack>> TAB_ITEMS = new ArrayList<>();

    public static final ResourceLocation TAB_ID = new ResourceLocation(Constants.MOD_ID, "main_tab");

    // Add an item to the creative tab
    public static void addToTab(Supplier<ItemStack> itemStack) {
        TAB_ITEMS.add(itemStack);
    }

    // Get all items for the tab
    public static List<Supplier<ItemStack>> getTabItems() {
        return TAB_ITEMS;
    }

    // Create the creative tab (platform-specific implementations will use this)
    public static CreativeModeTab.Builder createTabBuilder() {
        return CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                .title(Component.translatable("itemGroup." + Constants.MOD_ID + ".main_tab"))
                .icon(() -> {
                    // Use the first block as the icon, or fallback to pink wool if not available
                    if (CommonBlocks.getFirstBlockItem() != null) {
                        return new ItemStack(CommonBlocks.getFirstBlockItem().get());
                    }
                    return new ItemStack(Blocks.PINK_WOOL);
                });
    }
}

