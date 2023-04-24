package me.delta.mc.marker.apiv2;

import me.delta.mc.marker.api.RGBController;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class SimpleDisplay {

    private Color glowColor = Color.LIME;
    private boolean globalVisibility = false;
    private final Set<MarkingPlayer> players = new HashSet<>();
    private RGBController rgbController;
    private boolean multiMarking = false;
    private BlockData markerMaterial = Material.LIME_STAINED_GLASS.createBlockData();
    private final Set<UUID> activeMarkers = new HashSet<>();
    private MarkerCache markerCache;


    public SimpleDisplay(MarkerCache cache){
        this.markerCache = cache;
    }

    public Color getGlowColor() {
        return glowColor;
    }

    public SimpleDisplay createMarker(Location location){
        location.getWorld().spawn(location, BlockDisplay.class, display ->{
            if (this.rgbController != null) this.rgbController.addDisplay(display);
            else {
                display.setGlowColorOverride(this.glowColor);
                display.setGlowing(true);
            }
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
        return this;
    }

    public abstract SimpleDisplay createMarker(BlockDisplay display);

    public SimpleDisplay removeMarker(){
        this.activeMarkers.forEach(uuid -> {
            Bukkit.getEntity(uuid).remove();
        });
        this.activeMarkers.clear();
        return this;
    }

    public SimpleDisplay updateMarker(){
        final Set<UUID> cache = new HashSet<>(this.activeMarkers);
        this.removeMarker();
        cache.forEach(uuid -> {
            this.createMarker(Bukkit.getEntity(uuid).getLocation());
        });
        return this;
    }

    public SimpleDisplay setGlowColor(Color glowColor) {
        this.glowColor = glowColor;
        return this;
    }

    public boolean isGlobalVisibility() {
        return globalVisibility;
    }

    public SimpleDisplay setGlobalVisibility(boolean globalVisibility) {
        this.globalVisibility = globalVisibility;
        return this;
    }

    public Set<MarkingPlayer> getPlayers() {
        return players;
    }

    public SimpleDisplay addPlayer(MarkingPlayer player){
        this.players.add(player);
        return this;
    }

    public SimpleDisplay removePlayer(MarkingPlayer player){
        this.players.remove(player);
        return this;
    }

    public RGBController getRgbController() {
        return rgbController;
    }

    public SimpleDisplay setRgbController(RGBController rgbController) {
        this.rgbController = rgbController;
        return this;
    }

    public boolean isMultiMarking() {
        return multiMarking;
    }

    public SimpleDisplay setMultiMarking(boolean multiMarking) {
        this.multiMarking = multiMarking;
        return this;
    }

    public BlockData getMarkerMaterial() {
        return markerMaterial;
    }

    public SimpleDisplay setMarkerMaterial(BlockData markerMaterial) {
        this.markerMaterial = markerMaterial;
        return this;
    }

    public Set<UUID> getActiveMarkers() {
        return activeMarkers;
    }


    public MarkerCache getMarkerCache() {
        return markerCache;
    }

    public SimpleDisplay setMarkerCache(MarkerCache markerCache) {
        this.markerCache = markerCache;
        return this;
    }
}
