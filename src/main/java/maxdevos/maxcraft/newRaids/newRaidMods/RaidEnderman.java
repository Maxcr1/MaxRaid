package maxdevos.maxcraft.newRaids.newRaidMods;

import net.minecraft.server.v1_16_R1.Raid;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.event.raid.RaidSpawnWaveEvent;

@SuppressWarnings({"deprecation", "unused"})
public class RaidEnderman extends RaidMob{


    public RaidEnderman(Player target, RaidSpawnWaveEvent w) {
        super(target, w, EntityType.ENDERMAN);
    }

    public RaidEnderman(Player target) {
        super(target, EntityType.ENDERMAN);
    }

    @SuppressWarnings("unused")
    public RaidEnderman(Player target, Location spawnLocation) {
        super(target, spawnLocation, EntityType.ENDERMAN);
    }

    public void setParams(LivingEntity e){
        Enderman z = (Enderman)e;
        z.setCustomName("§4Raid Enderman");
        z.setTarget(target);
        z.setMaxHealth(20);
        z.setHealth(20);
    }

}
