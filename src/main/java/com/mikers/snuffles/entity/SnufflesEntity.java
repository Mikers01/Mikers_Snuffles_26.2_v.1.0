package com.mikers.snuffles.entity;

import com.mikers.snuffles.ai.SnufflesSeekShelterGoal;
import com.mikers.snuffles.ai.SnufflesBreakNaturalBlocksGoal;
import com.mikers.snuffles.ai.SnufflesSleepGoal;
import com.mikers.snuffles.ai.SnufflesTargetGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Mob;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.animal.Animal;
import java.util.UUID;

public class SnufflesEntity extends Monster implements GeoEntity {
    private static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(SnufflesEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(SnufflesEntity.class, EntityDataSerializers.INT);
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private int sleepTicks;

    public SnufflesEntity(EntityType<? extends Monster> type, Level level) { super(type, level); }

    @Override protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(STATE, SnufflesState.SLEEP.ordinal());
        builder.define(VARIANT, 0);
    }

    @Override protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new SnufflesSleepGoal(this));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.15D, true) {
            @Override public boolean canUse() { return getTarget() != null && getTarget().isAlive() && getSnufflesState() == SnufflesState.ATTACK; }
            @Override public boolean canContinueToUse() { return getTarget() != null && getTarget().isAlive() && getSnufflesState() == SnufflesState.ATTACK; }
            @Override protected void checkAndPerformAttack(LivingEntity target) {
                if (getTicksUntilNextAttack() <= 0 && this.getAttackReachSqr(target) >= this.mob.distanceToSqr(target)) {
                    resetAttackCooldown();
                    this.mob.doHurtTarget((ServerLevel) this.mob.level(), target);
                }
            }
        });
        goalSelector.addGoal(3, new SnufflesBreakNaturalBlocksGoal(this));
        goalSelector.addGoal(4, new SnufflesSeekShelterGoal(this));
        goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new SnufflesTargetGoal<>(this, Player.class));
        targetSelector.addGoal(2, new SnufflesTargetGoal<>(this, Villager.class));
        targetSelector.addGoal(3, new SnufflesTargetGoal<>(this, Animal.class));
    }

    @Override public void tick() {
        super.tick();
        if (level().isClientSide) return;
        if (getTarget() != null && getTarget().isAlive()) setState(SnufflesState.ATTACK);
        else if (getSnufflesState() == SnufflesState.ATTACK) setState(level().isNight() ? SnufflesState.PANIC : SnufflesState.SLEEP);
        if (level().isDay() && !isUnderground()) {
            setState(SnufflesState.PANIC);
            if (++sleepTicks > 20 && !canSeeSky()) { setState(SnufflesState.SLEEP); sleepTicks = 0; }
        } else if (level().isNight() && getSnufflesState() == SnufflesState.SLEEP) setState(SnufflesState.PANIC);
    }

    private boolean isUnderground() { return !level().canSeeSky(blockPosition()); }
    @Override public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        boolean hurt = super.hurtServer(level, source, amount);
        if (hurt && !level().isClientSide) { setState(SnufflesState.PANIC); sleepTicks = 0; }
        return hurt;
    }

    public SnufflesState getSnufflesState() { return SnufflesState.values()[Math.floorMod(entityData.get(STATE), SnufflesState.values().length)]; }
    public void setState(SnufflesState state) { entityData.set(STATE, state.ordinal()); }
    public int getVariant() { return entityData.get(VARIANT); }
    public void setVariant(int value) { entityData.set(VARIANT, Math.floorMod(value, 4)); }
    public boolean isSleeping() { return getSnufflesState() == SnufflesState.SLEEP; }
    @Override public void travel(net.minecraft.world.phys.Vec3 input) { if (isSleeping()) super.travel(net.minecraft.world.phys.Vec3.ZERO); else super.travel(input); }
    @Override public void addAdditionalSaveData(CompoundTag tag) { super.addAdditionalSaveData(tag); tag.putInt("Variant", getVariant()); tag.putInt("State", getSnufflesState().ordinal()); }
    @Override public void readAdditionalSaveData(CompoundTag tag) { super.readAdditionalSaveData(tag); setVariant(tag.getInt("Variant")); setState(SnufflesState.values()[Math.floorMod(tag.getInt("State"), 3)]); }
    @Override public void finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, net.minecraft.world.entity.SpawnGroupData data) {
        super.finalizeSpawn(level, difficulty, reason, data);
        setVariant(random.nextInt(4)); setState(level.getLevel().isDay() ? SnufflesState.SLEEP : SnufflesState.PANIC);
    }

    private static final RawAnimation SLEEP = RawAnimation.begin().thenLoop("animation.mikers_snuffles.Sleeping");
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.mikers_snuffles.Idle");
    private static final RawAnimation RUN = RawAnimation.begin().thenLoop("animation.mikers_snuffles.Running");
    private static final RawAnimation SPRINT = RawAnimation.begin().thenLoop("animation.mikers_snuffles.Sprinting");
    private static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("animation.mikers_snuffles.Attack");

    @Override public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "main", 3, state -> {
            if (isSleeping()) return state.setAndContinue(SLEEP);
            if (state.isMoving()) return state.setAndContinue(getSnufflesState() == SnufflesState.ATTACK ? SPRINT : RUN);
            return state.setAndContinue(IDLE);
        }).triggerableAnim("attack", ATTACK));
    }
    public void triggerAttackAnimation() { triggerAnim("main", "attack"); }
    @Override public AnimatableInstanceCache getAnimatableInstanceCache() { return cache; }
}
