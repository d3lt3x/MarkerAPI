package me.delta.mc.marker.examples;

import me.delta.mc.marker.MarkerExample;
import me.delta.mc.marker.api.markers.CubicMarker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;

import java.util.HashMap;
import java.util.Map;

public class AreaSelection implements Listener {

    private final Map<Player, Block> blockCache = new HashMap<>();
    private final Map<Player, CubicMarker> currentSelections = new HashMap<>();
    private final Map<Player, CubicMarker> currentPreview = new HashMap<>();

    private final Plugin plugin;

    public AreaSelection(Plugin plugin) {
        this.plugin = plugin;
        check();
    }

    public void check() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {

                if (!(player.getInventory().getItemInMainHand().getType().equals(Material.TORCH)) || !this.currentSelections.containsKey(player)) {
                    if (this.currentPreview.containsKey(player)) {
                        this.currentPreview.get(player).removeMarker(true);
                        this.currentPreview.remove(player);
                    }
                    return;
                }

                CubicMarker marker = this.currentSelections.get(player);

                Block hitBlock = player.rayTraceBlocks(50).getHitBlock();
                if (hitBlock == null) return;

                Location destination = hitBlock.getLocation().add(0, 1, 0);

                CubicMarker preview;

                if (!this.currentPreview.containsKey(player)) {

                    preview = new CubicMarker(player, MarkerExample.getMainCache(), destination.getWorld(), BoundingBox.of(destination.toVector(), destination.toVector().add(marker.getArea().getMax().subtract(marker.getArea().getMin()))));
                    this.currentPreview.put(player, preview);

                } else preview = this.currentPreview.get(player);

                if (destination.toVector().toBlockVector().equals(preview.getArea().getMin().toBlockVector())) {
                    return;
                }


                preview.setArea(BoundingBox.of(destination.toVector(), destination.toVector().add(marker.getArea().getMax().subtract(marker.getArea().getMin()))));
                preview.updateMarker();

            });
        }, 0, 2);
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {


        if (!event.getAction().isLeftClick()) return;

        Player player = event.getPlayer();

        Block hitBlock = player.rayTraceBlocks(50).getHitBlock();
        if (hitBlock == null) return;

        if ((player.getInventory().getItemInMainHand().getType().equals(Material.TORCH))) {
            //event.setCancelled(true);
            if (!this.currentSelections.containsKey(player)) return;
            CubicMarker marker = this.currentSelections.get(player);
            marker.getSelection().pasteSelection(hitBlock.getLocation().add(0, 1, 0));
            marker.removeMarker(true);
            this.currentSelections.remove(player);
        }


        if (!(player.getInventory().getItemInMainHand().getType().equals(Material.REDSTONE_TORCH))) return;

        //event.setCancelled(true);

        if (!blockCache.containsKey(player)) {
            blockCache.put(player, hitBlock);
            player.sendMessage("1 block selected");
            return;
        }

        CubicMarker cubicMarker;

        if (this.currentSelections.containsKey(player)) {

            cubicMarker = this.currentSelections.get(player);
            cubicMarker.removeMarker(true);
            cubicMarker.setArea(BoundingBox.of(this.blockCache.get(event.getPlayer()), hitBlock));

        } else {

            cubicMarker = new CubicMarker(player, MarkerExample.getMainCache(), hitBlock.getWorld(), BoundingBox.of(this.blockCache.get(event.getPlayer()), hitBlock));
            this.currentSelections.put(player, cubicMarker);
        }

        cubicMarker.setArea(cubicMarker.getArea().expand(-0.001)).mark();
        player.sendMessage("area selected");
        this.blockCache.remove(player);

    }

}
