package me.delta.mc.marker.api.controllers;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Display;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.atomic.AtomicInteger;

public class RGBController extends Controller {
    private int tickInterval;
    private int colorDelta;

    public RGBController(Plugin plugin, int tickInterval, int colorDelta) {

        super(plugin);

        this.tickInterval = tickInterval;

        if (255 % colorDelta == 0) this.colorDelta = colorDelta;
        else this.colorDelta = 15;
    }

    public int getColorDelta() {
        return this.colorDelta;
    }

    public void setColorDelta(int colorDelta) {
        this.colorDelta = colorDelta;
    }

    protected BukkitTask run() {


        AtomicInteger r = new AtomicInteger(255);
        AtomicInteger g = new AtomicInteger();
        AtomicInteger b = new AtomicInteger();

        return Bukkit.getScheduler().runTaskTimer(super.getPlugin(), () -> {
            if (r.get() > 0 && b.get() == 0) {
                r.getAndAdd(-this.colorDelta);
                g.getAndAdd(this.colorDelta);
            }
            if (g.get() > 0 && r.get() == 0) {
                g.getAndAdd(-this.colorDelta);
                b.getAndAdd(this.colorDelta);
            }
            if (b.get() > 0 && g.get() == 0) {
                r.getAndAdd(this.colorDelta);
                b.getAndAdd(-this.colorDelta);
            }

            super.getMarkers().forEach(marker -> marker.getActiveMarkers().forEach(uuid -> {
                Display display = (Display) Bukkit.getEntity(uuid);
                display.setGlowColorOverride(Color.fromRGB(r.get(), g.get(), b.get()));
                display.setGlowing(true);
            }));
        }, 0, this.tickInterval);
    }


    public int getTickInterval() {
        return tickInterval;
    }

    public void setTickInterval(int tickInterval) {
        this.tickInterval = tickInterval;
    }

}
