package me.delta.mc.marker.examples;

import me.delta.mc.marker.api.Area;
import me.delta.mc.marker.api.AreaMarker;
import me.delta.mc.marker.api.MarkerBuilder;
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
    private final Map<Player, AreaMarker> markers = new HashMap<>();


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


        AreaMarker marker;

        if (!this.markers.containsKey(player)) {
            marker = this.buildMarker(this.blockCache.get(player), event.getBlock());
            marker.expandMarker(-0.001).markArea(Area.FULL);
            this.markers.put(player, marker);
            player.sendMessage("area selected");
            this.blockCache.remove(player);
            return;
        }

        marker = this.markers.get(player);
        marker.setBox(BoundingBox.of(this.blockCache.get(player), event.getBlock())).expandMarker(-0.001).markArea(Area.FULL);
        player.sendMessage("area selected");
        this.blockCache.remove(player);

    }

    private AreaMarker buildMarker(Block corner1, Block corner2) {
        return new MarkerBuilder(corner1, corner2).build();
    }

}
