package maxdevos.maxraid.mobs.nmsMobs;

import maxdevos.maxraid.raid.MaxRaid;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftCreeper;
import org.bukkit.util.BlockVector;

public class RaidCreeper extends CraftCreeper {

    static MaxRaid raid;
    public RaidCreeper(MaxRaid raid, BlockVector loc) {
        super(raid.getHandle().getLevel().getCraftServer(), new NMSCreeper(raid));
        RaidCreeper.raid = raid;
        setCustomName(ChatColor.DARK_RED + "RAID CREEPER");
        this.getHandle().setPos(loc.getX(), loc.getY(), loc.getZ());
        this.getHandle().goalSelector.removeAllGoals();
        this.getHandle().getLevel().addFreshEntity(this.getHandle());
        raid.getHandle().addMob(this.getHandle());
    }

    private static class NMSCreeper extends Creeper {
        public NMSCreeper(MaxRaid raid) {
            super(EntityType.CREEPER, raid.getHandle().serverLevel);
        }

        @Override
        public boolean hurt(DamageSource damagesource, float f) {
            raid.getHandle().updateBossbar();
            return super.hurt(damagesource, f);
        }

        @Override
        public void die(DamageSource damagesource) {
            raid.getHandle().updateBossbar();
            super.die(damagesource);
        }
    }

}
