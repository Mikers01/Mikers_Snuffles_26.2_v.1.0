package com.mikers.snuffles.ai;

import com.mikers.snuffles.entity.SnufflesEntity;
import com.mikers.snuffles.entity.SnufflesState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import java.util.EnumSet;

public class SnufflesSeekShelterGoal extends Goal {
    private final SnufflesEntity mob;
    private BlockPos destination;
    
    public SnufflesSeekShelterGoal(SnufflesEntity mob) { 
        this.mob = mob; 
        setFlags(EnumSet.of(Flag.MOVE)); 
    }
    
    @Override
    public boolean canUse() {
        if (mob.level().isDay() || mob.getTarget() != null || mob.getSnufflesState() == SnufflesState.SLEEP || !mob.level().canSeeSky(mob.blockPosition())) 
            return false;
        
        BlockPos origin = mob.blockPosition();
        for (int r = 1; r <= 16; r++) 
            for (int dx = -r; dx <= r; dx++) 
                for (int dz = -r; dz <= r; dz++) {
                    BlockPos p = origin.offset(dx, 0, dz);
                    if (!mob.level().canSeeSky(p) && mob.level().getBlockState(p).isAir()) { 
                        destination = p; 
                        return true; 
                    }
                }
        return false;
    }
    
    @Override
    public boolean canContinueToUse() { 
        return destination != null && mob.level().isDay() && mob.getTarget() == null && mob.distanceToSqr(destination.getX() + 0.5, destination.getY(), destination.getZ() + 0.5) > 1.0; 
    }
    
    @Override
    public void start() { 
        if (destination != null) mob.getNavigation().moveTo(destination.getX() + 0.5, destination.getY(), destination.getZ() + 0.5, 1.0); 
    }
    
    @Override
    public void stop() { 
        destination = null; 
        mob.getNavigation().stop(); 
        if (!mob.level().canSeeSky(mob.blockPosition())) mob.setState(SnufflesState.SLEEP); 
    }
}
