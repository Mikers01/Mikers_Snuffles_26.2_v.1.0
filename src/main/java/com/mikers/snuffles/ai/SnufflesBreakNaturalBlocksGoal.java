package com.mikers.snuffles.ai;
import com.mikers.snuffles.entity.SnufflesEntity;
import com.mikers.snuffles.entity.SnufflesState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import java.util.EnumSet;

public class SnufflesBreakNaturalBlocksGoal extends Goal {
    private final SnufflesEntity mob;
    private int cooldown;
    public SnufflesBreakNaturalBlocksGoal(SnufflesEntity mob) { this.mob=mob; setFlags(EnumSet.of(Flag.MOVE)); }
    @Override public boolean canUse() { return mob.getSnufflesState()!=SnufflesState.SLEEP && mob.getTarget()==null && !mob.getNavigation().isDone(); }
    @Override public boolean canContinueToUse() { return canUse(); }
    @Override public void tick() {
        if(cooldown-->0) return;
        cooldown=12;
        BlockPos feet=mob.blockPosition();
        for(int dy=0;dy<2;dy++) for(BlockPos p : BlockPos.betweenClosed(feet.offset(-1,dy,-1), feet.offset(1,dy,1))) {
            BlockState s=mob.level().getBlockState(p);
            if(isNaturalBreakable(s.getBlock()) && s.getDestroySpeed(mob.level(),p)>=0 && s.getDestroySpeed(mob.level(),p)<=8) {
                if(!mob.level().isClientSide) mob.level().destroyBlock(p,true,mob);
                return;
            }
        }
    }
    private boolean isNaturalBreakable(Block b) {
        return b==Blocks.STONE || b==Blocks.DEEPSLATE || b==Blocks.DIRT || b==Blocks.COARSE_DIRT || b==Blocks.ROOTED_DIRT || b==Blocks.GRAVEL || b==Blocks.SAND || b==Blocks.RED_SAND || b==Blocks.COBBLESTONE || b==Blocks.ANDESITE || b==Blocks.DIORITE || b==Blocks.GRANITE || b==Blocks.TUFF || b==Blocks.CALCITE || b==Blocks.NETHERRACK || b==Blocks.END_STONE || b==Blocks.OAK_LOG || b==Blocks.SPRUCE_LOG || b==Blocks.BIRCH_LOG || b==Blocks.JUNGLE_LOG || b==Blocks.ACACIA_LOG || b==Blocks.DARK_OAK_LOG || b==Blocks.MANGROVE_LOG || b==Blocks.CHERRY_LOG || b==Blocks.COAL_ORE || b==Blocks.DEEPSLATE_COAL_ORE || b==Blocks.IRON_ORE || b==Blocks.DEEPSLATE_IRON_ORE || b==Blocks.COPPER_ORE || b==Blocks.DEEPSLATE_COPPER_ORE || b==Blocks.GOLD_ORE || b==Blocks.DEEPSLATE_GOLD_ORE || b==Blocks.REDSTONE_ORE || b==Blocks.DEEPSLATE_REDSTONE_ORE || b==Blocks.LAPIS_ORE || b==Blocks.DEEPSLATE_LAPIS_ORE || b==Blocks.DIAMOND_ORE || b==Blocks.DEEPSLATE_DIAMOND_ORE || b==Blocks.EMERALD_ORE || b==Blocks.DEEPSLATE_EMERALD_ORE;
    }
}
