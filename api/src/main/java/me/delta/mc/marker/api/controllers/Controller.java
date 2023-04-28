package me.delta.mc.marker.api.controllers;

import me.delta.mc.marker.api.markers.Marker;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

public abstract class Controller {

    private final Plugin plugin;
    private final Set<Marker<?>> markers = new HashSet<>();
    private boolean stopped = false;
    private BukkitTask task;

    public Controller(Plugin plugin) {
        this.plugin = plugin;
    }

    public void startTask() {
        if (this.task != null) return;
        this.stopped = false;
        this.task = this.run();
    }

    protected abstract BukkitTask run();

    public void stopTask() {
        if (task == null) return;
        this.task.cancel();
        this.task = null;
        this.stopped = true;
    }

    public void addMarker(Marker<?> marker) {
        this.markers.add(marker);
        if (!this.stopped) this.startTask();
    }

    public void removeMarker(Marker<?> marker) {
        this.markers.remove(marker);
        if (this.markers.isEmpty()) this.stopTask();
    }

    public boolean isStopped() {
        return stopped;
    }

    public Set<Marker<?>> getMarkers() {
        return new HashSet<>(this.markers);
    }

    protected Plugin getPlugin() {
        return this.plugin;
    }
}
