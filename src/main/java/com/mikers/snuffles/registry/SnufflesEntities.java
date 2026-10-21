package com.mikers.snuffles.registry;

import com.mikers.snuffles.MikersSnuffles;
import com.mikers.snuffles.entity.SnufflesEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class SnufflesEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, MikersSnuffles.MOD_ID);
    public static final RegistryObject<EntityType<SnufflesEntity>> SNUFFLES = ENTITY_TYPES.register("snuffles", () ->
        EntityType.Builder.of(SnufflesEntity::new, MobCategory.MONSTER)
            .sized(0.8F, 1.8F).clientTrackingRange(8).build("mikers_snuffles:snuffles"));

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(SNUFFLES.get(), AttributeSupplier.builder()
            .add(Attributes.MAX_HEALTH, 24.0)
            .add(Attributes.ATTACK_DAMAGE, 13.0)
            .add(Attributes.MOVEMENT_SPEED, 0.3)
            .add(Attributes.FOLLOW_RANGE, 32.0).build());
    }
    private SnufflesEntities() {}
}
