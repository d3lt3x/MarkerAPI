package me.delta.mc.marker.api.holders;

import me.delta.mc.marker.api.markers.Marker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MarkerCache implements Listener {

    private final Plugin plugin;
    private final Map<Player, Set<Marker<?>>> markerMap = new HashMap<>();

    public MarkerCache(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
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

        this.markerMap.get(player).forEach(marker -> marker.removeMarker(false));
        this.markerMap.remove(player);
    }

    public void removeMarker(Marker<?> marker) {
        marker.removeMarker(false);
        if (this.markerMap.get(marker.getOwner()) != null)
            this.markerMap.get(marker.getOwner()).remove(marker);
    }

    public void purgeMarkers() {

        this.markerMap.values().forEach(markers -> markers.forEach(marker -> marker.removeMarker(false)));
        this.markerMap.clear();
    }

    @EventHandler
    private void onPluginUnload(PluginDisableEvent event) {
        if (event.getPlugin().equals(this.plugin))
            this.purgeMarkers();
    }

    @EventHandler
    private void onPlayerLeave(PlayerQuitEvent event) {
        this.removeMarkers(event.getPlayer());
    }

}
