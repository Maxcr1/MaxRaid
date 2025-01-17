package maxdevos.maxraid.goals;

import maxdevos.maxraid.mobs.experimental.ParatrooperDroppingPhantom;
import maxdevos.maxraid.mobs.fleets.ParatrooperFleet;
import maxdevos.maxraid.util.VecUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Phantom;

import java.util.Queue;

public class DropParatroopers extends Goal {

    ParatrooperDroppingPhantom phantomMob;
    Phantom mob;
    Queue<ParatrooperFleet> fleets;
    ParatrooperFleet currentFleet;
    boolean goingToTerminalPoint;

    public DropParatroopers(ParatrooperDroppingPhantom mob, Queue<ParatrooperFleet> fleets){
        this.phantomMob = mob;
        this.mob = mob.getHandle();
        this.fleets = fleets;
    }


    @Override
    public boolean canUse() {
        return true;
    }


    @Override
    public void tick() {

        if(mob.position().distanceTo(VecUtil.bVecToVec3(currentFleet.dropLocation)) <= 3.0 && !goingToTerminalPoint){
            currentFleet.dropAll();
            if(fleets.peek() != null){
                currentFleet = fleets.remove();
                mob.getMoveControl().setWantedPosition(currentFleet.dropLocation.getX(), currentFleet.dropLocation.getY() + 2, currentFleet.dropLocation.getZ(), 1f);
            }
            else{
                goingToTerminalPoint = true;
                mob.getMoveControl().setWantedPosition(phantomMob.terminalLocation.getX(), phantomMob.terminalLocation.getY(), phantomMob.terminalLocation.getZ(), 1f);
            }
        }

        if(goingToTerminalPoint && mob.position().distanceTo(VecUtil.bVecToVec3(phantomMob.terminalLocation)) <= 3.0){
            mob.remove(Entity.RemovalReason.DISCARDED);
        }

    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void start(){
        currentFleet = fleets.remove();
        mob.getMoveControl().setWantedPosition(currentFleet.dropLocation.getX(), currentFleet.dropLocation.getY(), currentFleet.dropLocation.getZ(), 1f);
    }
    public void stop(){
        currentFleet = null;
    }

}
