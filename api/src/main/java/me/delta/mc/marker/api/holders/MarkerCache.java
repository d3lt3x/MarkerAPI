package me.delta.mc.marker.api.holders;

import me.delta.mc.marker.api.markers.Marker;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MarkerCache {

    private final Plugin plugin;
    private final Map<Player, Set<Marker<?>>> markerMap = new HashMap<>();

    public MarkerCache(Plugin plugin) {
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public Set<Marker<?>> getMarkers(Player player) {
        return new HashSet<>(this.markerMap.get(player));
    }

    public void addMarker(Marker<?> marker) {
        this.markerMap.putIfAbsent(marker.getOwner(), new HashSet<>());
        this.markerMap.get(marker.getOwner()).add(marker);
    }

    public void removeMarkers(Player player) {
        this.markerMap.remove(player);
    }

    public void removeMarker(Marker<?> marker) {
        this.markerMap.get(marker.getOwner()).remove(marker);
    }


}
