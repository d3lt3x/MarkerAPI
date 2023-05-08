package me.delta.mc.marker.api.controllers;

import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.joml.Vector3f;

public class FloatingController extends Controller {

    private int tickInterval;
    private float heightDelta;

    public FloatingController(Plugin plugin, int tickInterval, float heightDelta) {
        super(plugin);
        this.tickInterval = tickInterval;
        this.heightDelta = heightDelta;
    }


    @Override
    protected BukkitTask run() {

        AtomicDouble delta = new AtomicDouble(this.heightDelta);
        AtomicDouble y = new AtomicDouble(0);

        return Bukkit.getScheduler().runTaskTimer(super.getPlugin(), () -> {

            y.set(y.get() + delta.get());

            if (y.get() > 1.00 || y.get() < 0.00) {
                delta.set(delta.get() * -1);
            }

            super.getMarkers().forEach(marker -> {
                marker.translateMarker(new Vector3f(0, (float) delta.get(), 0));
            });

        }, 0, this.tickInterval);
    }

    public int getTickInterval() {
        return tickInterval;
    }

    public FloatingController setTickInterval(int tickInterval) {
        this.tickInterval = tickInterval;
        return this;
    }

    public float getHeightDelta() {
        return heightDelta;
    }

    public FloatingController setRotationDelta(float rotationDelta) {
        this.heightDelta = rotationDelta;
        return this;
    }
}
