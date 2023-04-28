package me.delta.mc.marker.examples;

import me.delta.mc.marker.MarkerExample;
import me.delta.mc.marker.api.controllers.RGBController;
import me.delta.mc.marker.api.markers.SurfaceMarker;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.BoundingBox;

import java.util.HashMap;
import java.util.Map;

public class GroundRGB implements Listener {

    private final Map<Location, SurfaceMarker> markers = new HashMap<>();
    private RGBController rgbController;

    public GroundRGB(RGBController rgbController) {
        this.rgbController = rgbController;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        if (!event.hasChangedBlock()) return;

        if (this.markers.containsKey(event.getFrom().getBlock().getLocation().subtract(0, 1, 0))) {
            this.markers.get(event.getFrom().getBlock().getLocation().subtract(0, 1, 0)).removeMarker(true);
            this.markers.remove(event.getFrom().getBlock().getLocation().subtract(0, 1, 0));
        }

        Block block = event.getTo().getBlock().getLocation().subtract(0, 1, 0).getBlock();

        if (!block.isSolid()) return;

        if (this.markers.containsKey(block.getLocation())) return;

        this.spawnMarker(block, event.getPlayer());
    }

    private void spawnMarker(Block block, Player player) {

        SurfaceMarker marker = new SurfaceMarker(player, MarkerExample.getMainCache(), block.getWorld(), BoundingBox.of(block, block).expand(-0.001), SurfaceMarker.SURFACE_SIDE.TOP);
        marker.setInitialGlow(false);
        marker.mark();
        this.markers.put(block.getLocation(), marker);
        this.rgbController.addMarker(marker);


    }
}
