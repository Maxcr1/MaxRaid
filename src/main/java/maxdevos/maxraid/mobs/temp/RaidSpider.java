package maxdevos.maxraid.mobs.temp;

import maxdevos.maxraid.goals.MoveTowardsPointGoal;
import maxdevos.maxraid.goals.targets.NearestAttackableMaxRaidTargetGoal;
import maxdevos.maxraid.goals.SpiderSpeedAttackGoal;
import maxdevos.maxraid.mobs.Spawnable;
import maxdevos.maxraid.raid.MaxRaid;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftSpider;
import org.bukkit.util.BlockVector;

public class RaidSpider extends CraftSpider implements Spawnable {
    MaxRaid maxRaid;

    public RaidSpider(MaxRaid maxRaid) {
        super(maxRaid.getHandle().getLevel().getCraftServer(), new NMSSpider(maxRaid));
        this.maxRaid = maxRaid;
        setPersistent(true);
        setCustomName(ChatColor.DARK_RED + "RAID Spider");

        getHandle().getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier("raid bonus", 30f, AttributeModifier.Operation.ADDITION));
        getHandle().setHealth(30f);
    }

    public RaidSpider(MaxRaid maxRaid, BlockVector loc) {
        this(maxRaid);
        int y = maxRaid.getHandle().getLevel().getHeight(Heightmap.Types.MOTION_BLOCKING, loc.getBlockX(), loc.getBlockZ());
        loc = new BlockVector(loc.getX(), y, loc.getZ());
        spawn(loc);
    }

    public void spawn(BlockVector loc) {
        this.getHandle().setPos(loc.getX(), loc.getY(), loc.getZ());
        maxRaid.addMob(this);
    }

    private static class NMSSpider extends Spider {
        private final MaxRaid raid;

        public NMSSpider(MaxRaid raid) {
            super(EntityType.SPIDER, raid.getHandle().serverLevel);
            this.raid = raid;
            registerRaidGoals();
        }

        @Override
        protected void registerGoals() {
            goalSelector.removeAllGoals();
            targetSelector.removeAllGoals();
        }

        protected void registerRaidGoals() {
            goalSelector.addGoal(1, new FloatGoal(this));
            goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4F));
            goalSelector.addGoal(3, new SpiderSpeedAttackGoal(this, 2.0));
            goalSelector.addGoal(4, new MoveTowardsPointGoal(this, raid.getVillageCenter(), 1.0));

            targetSelector.addGoal(1, new HurtByTargetGoal(this));
            targetSelector.addGoal(2, new NearestAttackableMaxRaidTargetGoal<>(this, Player.class));
            targetSelector.addGoal(3, new NearestAttackableMaxRaidTargetGoal<>(this, AbstractVillager.class, false));
        }
    }
}
