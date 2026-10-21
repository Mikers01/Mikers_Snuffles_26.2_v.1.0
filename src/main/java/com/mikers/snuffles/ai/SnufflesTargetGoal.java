package com.mikers.snuffles.ai;

import com.mikers.snuffles.entity.SnufflesEntity;
import com.mikers.snuffles.entity.SnufflesState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class SnufflesTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final SnufflesEntity snuffles;
    
    public SnufflesTargetGoal(SnufflesEntity mob, Class<T> target) { 
        super(mob, target, true); 
        snuffles = mob; 
    }
    
    @Override
    public boolean canUse() { 
        return snuffles.getSnufflesState() != SnufflesState.SLEEP && super.canUse(); 
    }
    
    @Override
    public void start() { 
        super.start(); 
        if (snuffles.getTarget() != null) snuffles.setState(SnufflesState.ATTACK); 
    }
}
