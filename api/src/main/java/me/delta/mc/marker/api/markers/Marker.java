package me.delta.mc.marker.api.markers;


import me.delta.mc.marker.api.controllers.Controller;
import me.delta.mc.marker.api.holders.MarkerCache;
import me.delta.mc.marker.api.holders.MarkingPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class Marker<T extends Marker<T>> {

    private final Set<MarkingPlayer> players = new HashSet<>();
    private final Set<UUID> activeMarkers = new HashSet<>();
    private Color glowColor = Color.LIME;
    private boolean globalVisibility = false;
    private BlockData markerMaterial = Material.LIME_STAINED_GLASS.createBlockData();
    private MarkerCache markerCache;
    private boolean initialGlow = true;

    private Set<Controller> controllers = new HashSet<>();

    public Marker(MarkerCache cache) {
        this.markerCache = cache;
    }

    public Color getGlowColor() {
        return glowColor;
    }

    public T setGlowColor(Color glowColor) {
        this.glowColor = glowColor;
        return (T) this;
    }

    public T createMarker(Location location) {
        location.getWorld().spawn(location, BlockDisplay.class, display -> {

            display.setGlowColorOverride(this.glowColor);
            display.setGlowing(this.initialGlow);

            display.setBlock(this.markerMaterial);
            display.setGravity(false);
            display.setInvulnerable(true);
            display.setPersistent(true);
            display.setBrightness(new Display.Brightness(15, 15));
            display.setShadowStrength(0);
            display.setShadowRadius(0);

            this.createMarker(display);

            this.activeMarkers.add(display.getUniqueId());
        });
        return (T) this;
    }

    public abstract T createMarker(BlockDisplay display);

    public T removeMarker() {
        this.activeMarkers.forEach(uuid -> {
            Bukkit.getEntity(uuid).remove();
        });
        this.activeMarkers.clear();
        return (T) this;
    }

    public T updateMarker() {
        final Set<UUID> cache = new HashSet<>(this.activeMarkers);
        this.removeMarker();
        cache.forEach(uuid -> {
            this.createMarker(Bukkit.getEntity(uuid).getLocation());
        });
        return (T) this;
    }

    public boolean isGlobalVisibility() {
        return this.globalVisibility;
    }

    public T setGlobalVisibility(boolean globalVisibility) {
        this.globalVisibility = globalVisibility;
        return (T) this;
    }

    public Set<MarkingPlayer> getPlayers() {
        return this.players;
    }

    public T addPlayer(MarkingPlayer player) {
        this.players.add(player);
        return (T) this;
    }

    public BlockData getMarkerMaterial() {
        return this.markerMaterial;
    }

    public T setMarkerMaterial(BlockData markerMaterial) {
        this.markerMaterial = markerMaterial;
        return (T) this;
    }

    public Set<UUID> getActiveMarkers() {
        return new HashSet<>(this.activeMarkers);
    }

    public MarkerCache getMarkerCache() {
        return this.markerCache;
    }

    public T setMarkerCache(MarkerCache markerCache) {
        this.markerCache = markerCache;
        return (T) this;
    }

    public T setInitialGlow(boolean initialGlow) {
        this.initialGlow = initialGlow;
        return (T) this;
    }

    public boolean isInitialGlow() {
        return initialGlow;
    }



}
