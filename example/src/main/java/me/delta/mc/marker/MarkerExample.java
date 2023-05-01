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


    }

    @Override
    public void onEnable() {

        MAIN_CACHE = new MarkerCache(this);
        RGBController controller = new RGBController(this, 1, 17);
        Bukkit.getPluginManager().registerEvents(new AreaSelection(controller), this);
        Bukkit.getPluginManager().registerEvents(new GroundRGB(controller), this);

    }
}
