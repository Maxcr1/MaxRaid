package maxdevos.maxraid.mobs.temp;

import maxdevos.maxraid.goals.MaxRaidBlazeAttackGoal;
import maxdevos.maxraid.goals.MoveTowardsPointGoal;
import maxdevos.maxraid.goals.targets.NearestAttackableMaxRaidTargetGoal;
import maxdevos.maxraid.mobs.Spawnable;
import maxdevos.maxraid.raid.MaxRaid;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftBlaze;
import org.bukkit.util.BlockVector;

public class RaidBlaze extends CraftBlaze implements Spawnable {
    static MaxRaid maxRaid;

    public RaidBlaze(MaxRaid maxRaid) {
        super(maxRaid.getHandle().getLevel().getCraftServer(), new NMSBlaze(maxRaid));
        this.setPersistent(true);
        RaidBlaze.maxRaid = maxRaid;
        setPersistent(true);
        setCustomName(ChatColor.DARK_RED + "RAID Blaze");
    }

    public RaidBlaze(MaxRaid maxRaid, BlockVector loc) {
        this(maxRaid);
        int y = maxRaid.getHandle().getLevel().getHeight(Heightmap.Types.MOTION_BLOCKING, loc.getBlockX(), loc.getBlockZ());
        loc = new BlockVector(loc.getX(), y, loc.getZ());
        spawn(loc);
    }

    public void spawn(BlockVector loc) {
        this.getHandle().setPos(loc.getX(), loc.getY(), loc.getZ());
        maxRaid.addMob(this);
    }

    public static class NMSBlaze extends Blaze {
        MaxRaid raid;

        public NMSBlaze(MaxRaid raid) {
            super(EntityType.BLAZE, raid.getHandle().serverLevel);
            this.raid = raid;
            registerRaidGoals();
        }

        @Override
        protected void registerGoals() {
            goalSelector.removeAllGoals();
            targetSelector.removeAllGoals();
        }

        protected void registerRaidGoals() {

            //TODO this shit doesn't work
            goalSelector.addGoal(1, new MaxRaidBlazeAttackGoal(this));
            goalSelector.addGoal(2, new MoveTowardsPointGoal(this, raid.getVillageCenter(), 2.0));

            targetSelector.addGoal(1, new HurtByTargetGoal(this));
            targetSelector.addGoal(2, new NearestAttackableMaxRaidTargetGoal<>(this, Player.class, false));
            targetSelector.addGoal(3, new NearestAttackableMaxRaidTargetGoal<>(this, AbstractVillager.class, false));

        }

        public void setCharged(boolean var0) {
            EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Blaze.class, EntityDataSerializers.BYTE);
            byte var1 = this.entityData.get(DATA_FLAGS_ID);
            if (var0) {
                var1 = (byte) (var1 | 1);
            } else {
                var1 &= -2;
            }

            this.entityData.set(DATA_FLAGS_ID, var1);
        }
    }

}
