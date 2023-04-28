package me.delta.mc.marker.api.markers;


import me.delta.mc.marker.api.holders.MarkerCache;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.Player;
import org.bukkit.util.Consumer;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.Nullable;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class Marker<T extends Marker<T>> {

    private final Set<Player> players = new HashSet<>();
    private final Set<UUID> activeMarkers = new HashSet<>();
    private Color glowColor = Color.LIME;
    private boolean globalVisibility = false;
    private BlockData markerMaterial = Material.LIME_STAINED_GLASS.createBlockData();
    private MarkerCache markerCache;
    private boolean initialGlow = true;
    private int maxElements = 1;
    private World world;
    private Player owner;

    public Marker(@Nullable Player owner, MarkerCache cache, World world) {
        this.markerCache = cache;
        this.world = world;
        this.owner = owner;
        this.players.add(owner);
    }

    public Color getGlowColor() {
        return glowColor;
    }

    public T setGlowColor(Color glowColor) {
        this.glowColor = glowColor;
        return (T) this;
    }

    protected BlockDisplay spawnMarker(Location location, Consumer<BlockDisplay> displayConsumer) {

        if (this.activeMarkers.size() > this.maxElements)
            this.removeMarker();

        this.markerCache.addMarker(this);

        return location.getWorld().spawn(location, BlockDisplay.class, display -> {

            display.setVisibleByDefault(this.globalVisibility);
            if (!this.globalVisibility) {
                this.players.forEach(player -> player.showEntity(this.markerCache.getPlugin(), display));
            }

            display.setGlowColorOverride(this.glowColor);
            display.setGlowing(this.initialGlow);
            display.setBlock(this.markerMaterial);
            display.setGravity(false);
            display.setInvulnerable(true);
            display.setPersistent(true);
            display.setBrightness(new Display.Brightness(15, 15));
            display.setShadowStrength(0);
            display.setShadowRadius(0);

            displayConsumer.accept(display);
            this.activeMarkers.add(display.getUniqueId());
        });

    }

    public abstract void mark();

    public T removeMarker() {
        this.activeMarkers.forEach(uuid -> {
            Bukkit.getEntity(uuid).remove();
        });
        this.activeMarkers.clear();
        this.markerCache.removeMarker(this);
        return (T) this;
    }

    public abstract Marker<?> updateMarker();

    public boolean isGlobalVisibility() {
        return this.globalVisibility;
    }

    public T setGlobalVisibility(boolean globalVisibility) {
        this.globalVisibility = globalVisibility;
        return (T) this;
    }

    protected int getMaxElements() {
        return maxElements;
    }

    protected T setMaxElements(int maxElements) {
        this.maxElements = maxElements;
        return (T) this;
    }

    public World getWorld() {
        return world;
    }

    public T setWorld(World world) {
        this.world = world;
        return (T) this;
    }

    public Player getOwner() {
        return owner;
    }

    public T setOwner(Player owner) {
        this.owner = owner;
        return (T) this;
    }

    protected Transformation scale(Vector3f vector3f) {
        return new Transformation(new Vector3f(), new AxisAngle4f(), vector3f, new AxisAngle4f());
    }

    public Set<Player> getPlayers() {
        return this.players;
    }

    public T addPlayer(Player player) {
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
