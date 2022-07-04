package maxdevos.maxraid.mobs.base;

import maxdevos.maxraid.goals.MoveTowardsPointGoal;
import maxdevos.maxraid.goals.targets.NearestAttackableMaxRaidTargetGoal;
import maxdevos.maxraid.mobs.MobStats;
import maxdevos.maxraid.mobs.RaidMob;
import maxdevos.maxraid.raid.MaxRaid;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import org.bukkit.ChatColor;

public class NMSZombie extends Zombie implements RaidMob {
    MaxRaid raid;

    public NMSZombie(MaxRaid raid) {
        super(EntityType.ZOMBIE, raid.getHandle().serverLevel);
        this.raid = raid;
    }

    public void registerRaidGoals() {
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(2, new MeleeAttackGoal(this, 2.0, true));
        goalSelector.addGoal(3, new MoveTowardsPointGoal(this, raid.getVillageCenter(), 1.5));

        targetSelector.addGoal(1, new HurtByTargetGoal(this));
        targetSelector.addGoal(2, new NearestAttackableMaxRaidTargetGoal<>(this, Player.class, false));
        targetSelector.addGoal(3, new NearestAttackableMaxRaidTargetGoal<>(this, AbstractVillager.class, false));
    }

    /** Sunscreen */
    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

}