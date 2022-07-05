package maxdevos.maxraid.mobs.base;

import maxdevos.maxraid.mobs.RaidMob;
import maxdevos.maxraid.raid.MaxRaid;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Ghast;
import org.bukkit.ChatColor;

public class NMSGhast extends Ghast implements RaidMob {
    MaxRaid raid;

    public NMSGhast(MaxRaid raid) {
        super(EntityType.GHAST, raid.getHandle().serverLevel);
        this.raid = raid;
        this.getBukkitEntity().setCustomName(ChatColor.RED + "Raid Ghast");
    }

    //TODO spotlight uh
    public void registerRaidGoals() {
    }
}