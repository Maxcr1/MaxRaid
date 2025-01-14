package maxdevos.maxraid.util;

import maxdevos.maxraid.RaidPlugin;
import maxdevos.maxraid.player.RaidPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

@SuppressWarnings("unused")
public class PlayerUtils {

    private static final RaidPlugin plugin = RaidPlugin.getInstance();

    public static Player getRandomPlayer(Set<UUID> uuids){
        Random r = new Random();
        ArrayList<Player> out = new ArrayList<>();
        for(UUID p:uuids){
            out.add(plugin.getServer().getPlayer(p));
        }
        int p = r.nextInt(out.size()-1);
        return out.get(p);
    }

    public static ServerPlayer getNMSPlayerFromBukkitPlayer(ServerLevel serverLevel, Player p){
        for(ServerPlayer nmsPlayer:serverLevel.players()){
            if(nmsPlayer.getStringUUID().equals(p.getUniqueId().toString())){
                return nmsPlayer;
            }
        }
        System.out.println("COULDN'T FIND PLAYER! HERE'S A RANDOM ONE!");
        return serverLevel.getRandomPlayer();
    }

    public static Player getRandomPlayer(ArrayList<Player> players) {
        Random r = new Random();
        if (players.size() > 1) {
            int p = r.nextInt(players.size() - 1);
            return players.get(p).getPlayer();
        }
        return players.get(0).getPlayer();
    }

    public static Player getRandomRaidPlayer(ArrayList<RaidPlayer> players) {
        Random r = new Random();
        if (players.size() > 1) {
            int p = r.nextInt(players.size() - 1);
            return players.get(p).getPlayer();
        }
        return players.get(0).getPlayer();
    }

    public static ArrayList<Player> getPlayersFromUUIDs(Set<UUID> ids){
        ArrayList<Player> players = new ArrayList<>();
        if(plugin.getServer().getOnlinePlayers().size() == 1){
            try {
                Player max = plugin.getServer().getPlayer("maxcr1");
                players.add(max);
                Objects.requireNonNull(max).sendMessage("Debug Mode.  Added you as target.");
            }
            catch(Exception ignored){

            }
        }

        for(UUID id:ids){
            players.add(plugin.getServer().getPlayer(id));
        }
        return players;
    }

    public static Player getHighestPlayer(ArrayList<Player> players){
        Player out = players.get(0);
        for(Player p:players){
            if(p.getLocation().getBlockY() > out.getLocation().getBlockY()){
                out = p;
            }
        }
        return out;
    }

    public static Location getAveragePlayerLocation(ArrayList<RaidPlayer> players){
        double x = 0, y = 0, z = 0;
        for(RaidPlayer p: players){
            x+=p.getPlayer().getLocation().getX();
            y+=p.getPlayer().getLocation().getY();
            z+=p.getPlayer().getLocation().getY();
        }
        double num = (double) players.size();
        return new Location(players.get(0).getPlayer().getWorld(), x/num, y/num, z/num);
    }

    public static void printKillOrder(ArrayList<RaidPlayer> players){
        Collections.sort(players);
        Collections.reverse(players);
        int i = 1;
        for(RaidPlayer p:players){
            RaidPlugin.getServerInstance().broadcastMessage(ChatFunctions.raidPrefix + "[" + i + "] " +
                    p.getPlayer().getDisplayName() + "  -  " + p.getKills() + " kills");
            i++;
        }
    }

}

