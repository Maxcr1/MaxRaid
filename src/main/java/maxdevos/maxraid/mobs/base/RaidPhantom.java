package maxdevos.maxraid.mobs.base;

import maxdevos.maxraid.raid.MaxRaid;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPhantom;
import org.bukkit.util.BlockVector;

public class RaidPhantom extends CraftPhantom {

    static MaxRaid maxRaid;
    public RaidPhantom(MaxRaid maxRaid, BlockVector loc) {
        super(maxRaid.getHandle().getLevel().getCraftServer(), new NMSPhantom(maxRaid));
        RaidPhantom.maxRaid = maxRaid;
        setCustomName(ChatColor.DARK_RED + "RAID Phantom");
        this.getHandle().setPos(loc.getX(), loc.getY(), loc.getZ());
        maxRaid.getHandle().addMob(this.getHandle());
    }

    private static class NMSPhantom extends Phantom {
        MaxRaid raid;
        public NMSPhantom(MaxRaid raid) {
            super(EntityType.PHANTOM, raid.getHandle().serverLevel);
            this.raid = raid;
            registerRaidGoals();
        }

        @Override
        protected void registerGoals(){
//            goalSelector.removeAllGoals();
            targetSelector.removeAllGoals();
        }

        protected void registerRaidGoals() {
            //TODO hard
//            goalSelector.addGoal(1, new FloatGoal(this));
//            goalSelector.addGoal(2, new ZombieAttackGoal(this, 2.0, true));
//            goalSelector.addGoal(3, new MoveTowardsPointGoal(this, raid.getVillageCenter(), 1.0));
//            goalSelector.addGoal(4, new LookAtPointGoal(this, raid.getVillageCenter()));

//            targetSelector.addGoal(1, new HurtByTargetGoal(this));
            targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false));
            targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        }

        /** Make immune from sunburn */
        @Override
        protected boolean isSunBurnTick() {
            return false;
        }
    }

}
