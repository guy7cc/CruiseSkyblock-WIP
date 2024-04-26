package io.github.guy7cc.cruiseskyblock.core.player;

import io.github.guy7cc.cruiseskyblock.core.system.Tickable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerStatusManager implements Tickable {
    private final Map<Player, PlayerStatus> map = new HashMap<>();

    public PlayerStatusManager(){
        for(Player player : Bukkit.getOnlinePlayers()){
            addPlayer(player);
        }
    }

    @Override
    public void tick(int globalTick) {
        for(PlayerStatus status : map.values()){
            status.tick(globalTick);
        }
    }

    public void addPlayer(Player player){
        map.put(player, new PlayerStatus(player));
    }

    public void removePlayer(Player player){
        map.remove(player);
    }

    public PlayerStatus get(Player player){
        return map.get(player);
    }

    public void onPlayerJoin(Player player){
        addPlayer(player);
    }

    public void onPlayerQuit(Player player){
        removePlayer(player);
    }
}
