package me.delta.mc.marker;

import me.delta.mc.marker.api.controllers.RGBController;
import me.delta.mc.marker.api.holders.MarkerCache;
import me.delta.mc.marker.examples.AreaSelection;
import me.delta.mc.marker.examples.GroundRGB;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MarkerExample extends JavaPlugin {

    private static MarkerCache MAIN_CACHE;

    public static MarkerCache getMainCache() {
        return MAIN_CACHE;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public void onEnable() {
        System.out.println("1");
        MAIN_CACHE = new MarkerCache(this);
        Bukkit.getPluginManager().registerEvents(new AreaSelection(), this);
        Bukkit.getPluginManager().registerEvents(new GroundRGB(new RGBController(this, 1, 15)), this);

    }
}
