package com.pichurose.tmme.itemfactory;

import com.pichurose.tmme.Constants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class CommonItems {
    public static void register(PlatformRegistrar registrar) {
        //registerItem(registrar, "soft_flesh_block");
    }

    public static void registerItem(PlatformRegistrar registrar, String path){
        registrar.registerItem(
                new ResourceLocation(Constants.MOD_ID, path),
                () -> new Item(new Item.Properties())
        );
    }
}
