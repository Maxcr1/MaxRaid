package maxdevos.maxcraft.newRaids.raidEvents;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KillWaveEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}
