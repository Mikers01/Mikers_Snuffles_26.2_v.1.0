package com.mikers.snuffles.ai;

import com.mikers.snuffles.entity.SnufflesEntity;
import com.mikers.snuffles.entity.SnufflesState;
import net.minecraft.world.entity.ai.goal.Goal;
import java.util.EnumSet;

public class SnufflesSleepGoal extends Goal {
    private final SnufflesEntity mob;
    
    public SnufflesSleepGoal(SnufflesEntity mob) { 
        this.mob = mob; 
        setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP)); 
    }
    
    @Override
    public boolean canUse() { 
        return mob.getState() == SnufflesState.SLEEP; 
    }
    
    @Override
    public boolean canContinueToUse() { 
        return canUse(); 
    }
    
    @Override
    public void start() { 
        mob.getNavigation().stop(); 
        mob.setDeltaMovement(0, 0, 0); 
    }
    
    @Override
    public void tick() { 
        mob.getNavigation().stop(); 
        mob.setDeltaMovement(0, mob.getDeltaMovement().y, 0); 
    }
}
