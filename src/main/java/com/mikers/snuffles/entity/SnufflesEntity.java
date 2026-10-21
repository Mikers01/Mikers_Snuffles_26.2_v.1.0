package com.mikers.snuffles.entity;

import com.mikers.snuffles.ai.*;
import com.mikers.snuffles.registry.SnufflesEntities;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SnufflesEntity extends Monster implements GeoEntity {
    private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(SnufflesEntity.class, EntityDataSerializers.INT);
    
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public SnufflesEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 20; // Опыт за убийство
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.ATTACK_DAMAGE, 13.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STATE, 0); // 0 = SLEEP
    }

    public SnufflesState getState() {
        return SnufflesState.values()[this.entityData.get(STATE)];
    }

    public void setState(SnufflesState state) {
        this.entityData.set(STATE, state.ordinal());
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SnufflesSleepGoal(this));
        this.goalSelector.addGoal(2, new SnufflesSeekShelterGoal(this));
        this.goalSelector.addGoal(3, new SnufflesBreakNaturalBlocksGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, net.minecraft.world.entity.player.Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new SnufflesTargetGoal<>(this, net.minecraft.world.entity.player.Player.class));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> {
            SnufflesState state = getState();
            if (state == SnufflesState.SLEEP) return event.setAndContinue(RawAnimation.begin().thenLoop("sleeping"));
            if (state == SnufflesState.ATTACK) return event.setAndContinue(RawAnimation.begin().thenLoop("attack"));
            if (event.isMoving()) return event.setAndContinue(RawAnimation.begin().thenLoop("running"));
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENDERMAN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENDERMAN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENDERMAN_DEATH;
    }
}
