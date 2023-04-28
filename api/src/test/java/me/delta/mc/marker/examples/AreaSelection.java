package me.delta.mc.marker.examples;

import me.delta.mc.marker.MarkerExample;
import me.delta.mc.marker.api.markers.SurfaceMarker;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.BoundingBox;

import java.util.HashMap;
import java.util.Map;

public class AreaSelection implements Listener {

    private final Map<Player, Block> blockCache = new HashMap<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();

        if (!(player.getInventory().getItemInMainHand().getType().equals(Material.REDSTONE_TORCH))) return;

        event.setCancelled(true);


        if (!blockCache.containsKey(player)) {
            blockCache.put(player, event.getBlock());
            player.sendMessage("1 block selected");
            return;
        }


        SurfaceMarker surfaceMarker = new SurfaceMarker(player, MarkerExample.getMainCache(), event.getBlock().getWorld(), BoundingBox.of(this.blockCache.get(event.getPlayer()), event.getBlock()));
        surfaceMarker.setSurfaceSides(SurfaceMarker.SURFACE_SIDE.BOTTOM, SurfaceMarker.SURFACE_SIDE.NEG_Z, SurfaceMarker.SURFACE_SIDE.NEG_X, SurfaceMarker.SURFACE_SIDE.POS_X, SurfaceMarker.SURFACE_SIDE.POS_Z, SurfaceMarker.SURFACE_SIDE.TOP);
        surfaceMarker.setArea(surfaceMarker.getArea().expand(-0.001)).mark();
        player.sendMessage("area selected");

        this.blockCache.remove(player);

    }

}
