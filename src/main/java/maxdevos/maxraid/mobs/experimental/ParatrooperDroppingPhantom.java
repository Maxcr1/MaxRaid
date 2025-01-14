package maxdevos.maxraid.mobs.experimental;

import maxdevos.maxraid.goals.DropParatroopers;
import maxdevos.maxraid.goals.PhantomMoveToPoint;
import maxdevos.maxraid.mobs.Spawnable;
import maxdevos.maxraid.mobs.fleets.ParatrooperFleet;
import maxdevos.maxraid.raid.MaxRaid;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.phys.Vec3;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPhantom;
import org.bukkit.util.BlockVector;

import java.util.LinkedList;
import java.util.Queue;

public class ParatrooperDroppingPhantom extends CraftPhantom implements Spawnable {

    public static MaxRaid maxRaid;
    public Queue<ParatrooperFleet> fleets;
    public BlockVector terminalLocation;

    public ParatrooperDroppingPhantom(MaxRaid maxRaid, BlockVector loc) {
        super(maxRaid.getHandle().getLevel().getCraftServer(), new NMSBomberPhantom(maxRaid));
        ParatrooperDroppingPhantom.maxRaid = maxRaid;
        setCustomName(ChatColor.DARK_RED + "Paratrooper Shuttle");
        this.setSize(64);
        this.setInvulnerable(true);
        terminalLocation = loc;
        this.getHandle().setPos(loc.getX(), loc.getY(), loc.getZ());
        fleets = new LinkedList<>();
        getHandle().goalSelector.addGoal(1, new DropParatroopers(this, fleets));
    }

    public void addFleet(ParatrooperFleet fleet) {
        fleets.add(fleet);
    }

    public void spawn() {
        maxRaid.getHandle().addMob(this.getHandle(), false);
    }

    public void spawn(BlockVector loc) {
        maxRaid.getHandle().addMob(this.getHandle(), false);
    }

    private static class NMSBomberPhantom extends Phantom {
        MaxRaid raid;

        public NMSBomberPhantom(MaxRaid raid) {
            super(EntityType.PHANTOM, raid.getHandle().serverLevel);
            this.raid = raid;
            registerRaidGoals();
            this.moveControl = new PhantomMoveControl(this);
        }

        @Override
        protected void registerGoals() {
            goalSelector.removeAllGoals();
            targetSelector.removeAllGoals();
        }

        protected void registerRaidGoals() {
        }

        /**
         * Make immune from sunburn
         */
        @Override
        protected boolean isSunBurnTick() {
            return false;
        }

        private class PhantomMoveControl extends MoveControl {

            public PhantomMoveControl(Mob entityinsentient) {
                super(entityinsentient);
            }

            public void tick() {
                if (ParatrooperDroppingPhantom.NMSBomberPhantom.this.horizontalCollision) {
                    ParatrooperDroppingPhantom.NMSBomberPhantom.this.setYRot(ParatrooperDroppingPhantom.NMSBomberPhantom.this.getYRot() + 180.0F);
                }

                double d0 = this.getWantedX() - ParatrooperDroppingPhantom.NMSBomberPhantom.this.getX();
                double d1 = this.getWantedY() - ParatrooperDroppingPhantom.NMSBomberPhantom.this.getY();
                double d2 = this.getWantedZ() - ParatrooperDroppingPhantom.NMSBomberPhantom.this.getZ();
                double d3 = Math.sqrt(d0 * d0 + d2 * d2);
                if (Math.abs(d3) > 9.999999747378752E-6) {
                    double d4 = 1.0 - Math.abs(d1 * 0.699999988079071) / d3;
                    d0 *= d4;
                    d2 *= d4;
                    d3 = Math.sqrt(d0 * d0 + d2 * d2);
                    double d5 = Math.sqrt(d0 * d0 + d2 * d2 + d1 * d1);
                    float f = ParatrooperDroppingPhantom.NMSBomberPhantom.this.getYRot();
                    float f1 = (float) Mth.atan2(d2, d0);
                    float f2 = Mth.wrapDegrees(ParatrooperDroppingPhantom.NMSBomberPhantom.this.getYRot() + 90.0F);
                    float f3 = Mth.wrapDegrees(f1 * 57.295776F);
                    ParatrooperDroppingPhantom.NMSBomberPhantom.this.setYRot(Mth.approachDegrees(f2, f3, 4.0F) - 90.0F);
                    ParatrooperDroppingPhantom.NMSBomberPhantom.this.yBodyRot = ParatrooperDroppingPhantom.NMSBomberPhantom.this.getYRot();
                    if (Mth.degreesDifferenceAbs(f, ParatrooperDroppingPhantom.NMSBomberPhantom.this.getYRot()) < 3.0F) {
                        this.speedModifier = Mth.approach((float) this.getSpeedModifier(), 1.8F, 0.005F * (1.8F / (float) this.getSpeedModifier()));
                    } else {
                        this.speedModifier = Mth.approach((float) this.getSpeedModifier(), 0.2F, 0.025F);
                    }

                    float f4 = (float) (-(Mth.atan2(-d1, d3) * 57.2957763671875));
                    ParatrooperDroppingPhantom.NMSBomberPhantom.this.setXRot(f4);
                    float f5 = ParatrooperDroppingPhantom.NMSBomberPhantom.this.getYRot() + 90.0F;
                    double d6 = (this.speedModifier * Mth.cos(f5 * 0.017453292F)) * Math.abs(d0 / d5);
                    double d7 = (this.speedModifier * Mth.sin(f5 * 0.017453292F)) * Math.abs(d2 / d5);
                    double d8 = (this.speedModifier * Mth.sin(f4 * 0.017453292F)) * Math.abs(d1 / d5);
                    Vec3 vec3d = ParatrooperDroppingPhantom.NMSBomberPhantom.this.getDeltaMovement();
                    ParatrooperDroppingPhantom.NMSBomberPhantom.this.setDeltaMovement(vec3d.add((new Vec3(d6, d8, d7)).subtract(vec3d).scale(0.2)));
                }

            }
        }

    }

}
