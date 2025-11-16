package com.pichurose.tmme;

import com.pichurose.tmme.utils.PehkuiSupport;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class TheMacroMicroExperience {
    public TheMacroMicroExperience() {
        // This method is invoked by the Forge mod loader when it is ready
        // to load your mod. You can access Forge and Common code in this
        // project.
        CommonClass.init();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        // Use Forge to bootstrap the Common mod.
        Constants.LOG.info("Hello Forge world!");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        PehkuiSupport.setup();
    }
}