package com.pichurose.tmme;

import com.pichurose.tmme.itemfactory.CommonItems;
import com.pichurose.tmme.itemfactory.CommonBlocks;
import com.pichurose.tmme.itemfactory.CommonFluids;
import com.pichurose.tmme.itemfactory.ForgeCreativeTabs;
import com.pichurose.tmme.itemfactory.ForgeFluids;
import com.pichurose.tmme.itemfactory.ForgePlatformRegistrar;
import com.pichurose.tmme.utils.PehkuiSupport;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MOD_ID)
public class TheMacroMicroExperience {

    IEventBus modEventBus;

    public TheMacroMicroExperience() {
        CommonClass.init();
        modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);

        // Register creative tabs first
        ForgeCreativeTabs.register(modEventBus);

        // Register fluids
        ForgeFluids.register(modEventBus);
        CommonFluids.register(new ForgeFluids());

        // Register items during construction, not in RegisterEvent
        ForgePlatformRegistrar registrar = new ForgePlatformRegistrar(modEventBus);
        CommonItems.register(registrar);
        CommonBlocks.register(registrar);

        Constants.LOG.info("Hello Forge world!");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        PehkuiSupport.setup();
    }
}