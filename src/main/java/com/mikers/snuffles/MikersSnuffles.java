package com.mikers.snuffles;

import com.mikers.snuffles.registry.SnufflesEntities;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MikersSnuffles.MOD_ID)
public class MikersSnuffles {
    public static final String MOD_ID = "mikers_snuffles";

    public MikersSnuffles() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        SnufflesEntities.ENTITY_TYPES.register(modEventBus);
    }
}
