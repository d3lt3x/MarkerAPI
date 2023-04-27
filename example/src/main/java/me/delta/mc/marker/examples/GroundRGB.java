package me.delta.mc.marker.examples;

import me.delta.mc.marker.api.old.Area;
import me.delta.mc.marker.api.old.AreaMarker;
import me.delta.mc.marker.api.old.MarkerBuilder;
import me.delta.mc.marker.api.controllers.RGBController;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

public class GroundRGB implements Listener {

    private final Map<Location, AreaMarker> markers = new HashMap<>();
    private final RGBController rgbController;

    public GroundRGB(RGBController rgbController) {
        this.rgbController = rgbController;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        if (!event.hasChangedBlock()) return;

        if (this.markers.containsKey(event.getFrom().getBlock().getLocation().subtract(0, 1, 0))) {
            this.markers.get(event.getFrom().getBlock().getLocation().subtract(0, 1, 0)).unMark();
            this.markers.remove(event.getFrom().getBlock().getLocation().subtract(0, 1, 0));
        }

        Block block = event.getTo().getBlock().getLocation().subtract(0, 1, 0).getBlock();

        if (!block.isSolid()) return;

        if (this.markers.containsKey(block.getLocation())) return;

        this.spawnMarker(block);
    }

    private void spawnMarker(Block block) {

        AreaMarker marker = new MarkerBuilder(block, block).setRgbController(this.rgbController).build();
        this.markers.put(block.getLocation(), marker);
        marker.expandMarker(-0.001).markArea(Area.TOP);

    }
}
