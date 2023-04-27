package me.delta.mc.marker;

import me.delta.mc.marker.api.controllers.RGBController;
import me.delta.mc.marker.examples.AreaSelection;
import me.delta.mc.marker.examples.GroundRGB;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MarkerExample extends JavaPlugin {

    @Override
    public void onEnable() {

        Bukkit.getPluginManager().registerEvents(new AreaSelection(), this);
        Bukkit.getPluginManager().registerEvents(new GroundRGB(new RGBController(this, 1, 15)), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
