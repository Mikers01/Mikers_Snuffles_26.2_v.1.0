package com.mikers.snuffles.registry;

import com.mikers.snuffles.MikersSnuffles;
import com.mikers.snuffles.entity.SnufflesEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = MikersSnuffles.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class SnufflesEntities {
    
    // В новых версиях ForgeRegistries.ENTITY_TYPES может требовать указания типа, 
    // но чаще всего DeferredRegister работает так:
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MikersSnuffles.MOD_ID);

    public static final RegistryObject<EntityType<SnufflesEntity>> SNUFFLES = ENTITY_TYPES.register("snuffles", () ->
        EntityType.Builder.of(SnufflesEntity::new, MobCategory.MONSTER)
            .sized(0.8f, 1.8f)
            .clientTrackingRange(8)
            // В версии 26.2 ID сущности задается через Identifier
            .build(Identifier.fromNamespaceAndPath(MikersSnuffles.MOD_ID, "snuffles").toString())
    );

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(SNUFFLES.get(), AttributeSupplier.builder()
            .add(Attributes.MAX_HEALTH, 24.0)
            .add(Attributes.ATTACK_DAMAGE, 13.0)
            .add(Attributes.MOVEMENT_SPEED, 0.3)
            .add(Attributes.FOLLOW_RANGE, 32.0)
            .build());
    }
    
    private SnufflesEntities() {}
}
