package maxdevos.maxraid.mobs.experimental;

import maxdevos.maxraid.goals.*;
import maxdevos.maxraid.mobs.Spawnable;
import maxdevos.maxraid.raid.MaxRaid;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.SwellGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.Heightmap;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftCreeper;
import org.bukkit.util.BlockVector;

public class WallSeekingCreeper extends CraftCreeper implements Spawnable {

    static MaxRaid maxRaid;

    public WallSeekingCreeper(MaxRaid maxRaid) {
        super(maxRaid.getHandle().getLevel().getCraftServer(), new NMSCreeper(maxRaid));
        WallSeekingCreeper.maxRaid = maxRaid;
        setCustomName(ChatColor.DARK_RED + "SUICIDE CREEPER");
        getHandle().getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier("raid bonus", 30f, AttributeModifier.Operation.ADDITION));
        getHandle().setHealth(30f);
    }

    public WallSeekingCreeper(MaxRaid maxRaid, BlockVector loc) {
        this(maxRaid);
        int y = maxRaid.getHandle().getLevel().getHeight(Heightmap.Types.MOTION_BLOCKING, loc.getBlockX(), loc.getBlockZ());
        loc = new BlockVector(loc.getX(), y, loc.getZ());
        spawn(loc);
    }

    public void spawn(BlockVector loc) {
        this.getHandle().setPos(loc.getX(), loc.getY(), loc.getZ());
        maxRaid.addMob(this);
    }

    private static class NMSCreeper extends Creeper {
        MaxRaid raid;
        int timer = 0;

        public NMSCreeper(MaxRaid raid) {
            super(EntityType.CREEPER, raid.getHandle().serverLevel);
            this.raid = raid;
            this.explosionRadius = 5;
            registerRaidGoals();
        }

        @Override
        protected void registerGoals() {
            goalSelector.removeAllGoals();
            targetSelector.removeAllGoals();
        }

        protected void registerRaidGoals() {

            goalSelector.addGoal(2, new CreeperExplodeAgainstWallGoal((CraftCreeper) this.getBukkitEntity()));
            goalSelector.addGoal(3, new CreeperPathfindToRaidWall(this, this.raid));
            //            goalSelector.addGoal(4, new MoveTowardsPointGoal(this, raid.getVillageCenter(), 2.0));
//            goalSelector.addGoal(5, new LookAtPointGoal(this, raid.getVillageCenter()));

//            targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
//            targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, AbstractVillager.class, true));
//            targetSelector.addGoal(3, new HurtByTargetGoal(this));
        }

        @Override
        public void tick(){
            super.tick();
            if(timer > 20 * 15){
                explodeCreeper();
            }
            timer++;
        }

    }

}
