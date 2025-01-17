package maxdevos.maxraid.mobs.temp;

import maxdevos.maxraid.mobs.Spawnable;
import maxdevos.maxraid.raid.MaxRaid;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Illusioner;
import net.minecraft.world.level.levelgen.Heightmap;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftIllusioner;
import org.bukkit.util.BlockVector;

public class RaidIllusioner extends CraftIllusioner implements Spawnable {

    static MaxRaid maxRaid;

    public RaidIllusioner(MaxRaid raid) {
        super(raid.getHandle().getLevel().getCraftServer(), new NMSIllusioner(raid));
        RaidIllusioner.maxRaid = raid;
        setPersistent(true);
        setCustomName(ChatColor.DARK_RED + "RAID Illusioner");
    }

    public RaidIllusioner(MaxRaid raid, BlockVector loc) {
        this(raid);
                int y = maxRaid.getHandle().getLevel().getHeight(Heightmap.Types.MOTION_BLOCKING, loc.getBlockX(), loc.getBlockZ());
        loc = new BlockVector(loc.getX(), y, loc.getZ());
        spawn(loc);
    }

    public void spawn(BlockVector loc) {
        this.getHandle().setPos(loc.getX(), loc.getY(), loc.getZ());
        maxRaid.addMob(this);
    }

    private static class NMSIllusioner extends Illusioner {
        MaxRaid raid;

        public NMSIllusioner(MaxRaid raid) {
            super(EntityType.ILLUSIONER, raid.getHandle().serverLevel);
            this.raid = raid;
            registerRaidGoals();
        }

//        @Override
//        protected void registerGoals(){
//            goalSelector.removeAllGoals();
//            targetSelector.removeAllGoals();
//        }

        protected void registerRaidGoals() {
        }
    }

}
