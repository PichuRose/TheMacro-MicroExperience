package com.pichurose.tmme.itemfactory;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;

public class FabricCreativeTabs {

    public static void register() {
        CreativeModeTab tab = ModCreativeTabs.createTabBuilder()
                .displayItems((parameters, output) -> {
                    // Add all registered items to the tab
                    ModCreativeTabs.getTabItems().forEach(supplier -> output.accept(supplier.get()));
                })
                .build();

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ModCreativeTabs.TAB_ID, tab);
    }
}

