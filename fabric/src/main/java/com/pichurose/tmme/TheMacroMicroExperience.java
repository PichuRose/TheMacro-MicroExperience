package com.pichurose.tmme;

import com.pichurose.tmme.itemfactory.FabricCreativeTabs;
import com.pichurose.tmme.itemfactory.FabricFluids;
import com.pichurose.tmme.itemfactory.FabricPlatformRegistrar;
import com.pichurose.tmme.utils.PehkuiSupport;
import net.fabricmc.api.ModInitializer;
import com.pichurose.tmme.itemfactory.CommonItems;
import com.pichurose.tmme.itemfactory.CommonBlocks;
import com.pichurose.tmme.itemfactory.CommonFluids;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class TheMacroMicroExperience implements ModInitializer {

    @Override
    public void onInitialize() {
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        FabricPlatformRegistrar registrar = new FabricPlatformRegistrar();

        // Register fluids first
        CommonFluids.register(new FabricFluids());

        CommonItems.register(registrar);
        CommonBlocks.register(registrar);

        // Register creative tab after items/blocks
        FabricCreativeTabs.register();

        // Move Pehkui setup to after server starts (when all registries are populated)
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            PehkuiSupport.setup();
        });
    }



}
