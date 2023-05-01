package me.delta.mc.marker.examples;

import me.delta.mc.marker.MarkerExample;
import me.delta.mc.marker.api.controllers.RGBController;
import me.delta.mc.marker.api.markers.CubicMarker;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;

import java.util.HashMap;
import java.util.Map;

public class AreaSelection implements Listener {

    private final Map<Player, CubicMarker> blockCache = new HashMap<>();
    private final Map<Player, CubicMarker> currentSelections = new HashMap<>();
    private final Map<Player, CubicMarker> currentPreview = new HashMap<>();

    private final Map<Player, CubicMarker> currentPos = new HashMap<>();


    private final RGBController controller;

    public AreaSelection(RGBController controller) {
        this.controller = controller;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();


        if (!(player.getInventory().getItemInMainHand().getType().equals(Material.TORCH)) || !this.currentSelections.containsKey(player)) {
            if (this.currentPreview.containsKey(player)) {
                this.currentPreview.get(player).removeMarker(true);
                this.currentPreview.remove(player);
            }

        } else {

            CubicMarker marker = this.currentSelections.get(player);

            RayTraceResult result = player.rayTraceBlocks(200);
            if (result == null) return;

            Block hitBlock = result.getHitBlock();
            if (hitBlock == null) return;

            Location destination = hitBlock.getLocation().add(0, 1, 0);

            CubicMarker preview;

            if (!this.currentPreview.containsKey(player)) {

                preview = new CubicMarker(player, MarkerExample.getMainCache(), destination.getWorld(), BoundingBox.of(destination.toVector(), destination.toVector().add(marker.getArea().getMax().subtract(marker.getArea().getMin()))));
                preview.addController(this.controller);
                this.currentPreview.put(player, preview);

            } else preview = this.currentPreview.get(player);

            if (destination.toVector().toBlockVector().equals(preview.getArea().getMin().toBlockVector())) {
                return;
            }


            preview.setArea(BoundingBox.of(destination.toVector(), destination.toVector().add(marker.getArea().getMax().subtract(marker.getArea().getMin()))));
            preview.updateMarker();

        }


        if (!player.getInventory().getItemInMainHand().getType().equals(Material.REDSTONE_TORCH)) {
            if (currentPos.containsKey(player)) {
                currentPos.get(player).removeMarker(true);
                currentPos.remove(player);
            }
            return;
        }

        RayTraceResult result = player.rayTraceBlocks(200);
        if (result == null) return;

        Block hitBlock = result.getHitBlock();
        if (hitBlock == null) return;

        CubicMarker marker;

        if (!this.currentPos.containsKey(player)) {
            marker = new CubicMarker(player, MarkerExample.getMainCache(), player.getWorld(), BoundingBox.of(hitBlock, hitBlock));
            marker.addController(this.controller);
            this.currentPos.put(player, marker);
        } else marker = this.currentPos.get(player);

        if (marker.getArea().equals(BoundingBox.of(hitBlock, hitBlock)))
            return;

        marker.setArea(BoundingBox.of(hitBlock, hitBlock));
        marker.updateMarker();


    }


    @EventHandler
    public void onInteract(PlayerInteractEvent event) {


        if (!event.getAction().isLeftClick()) return;

        Player player = event.getPlayer();

        RayTraceResult result = player.rayTraceBlocks(200);
        if (result == null) return;

        Block hitBlock = result.getHitBlock();
        if (hitBlock == null) return;

        if ((player.getInventory().getItemInMainHand().getType().equals(Material.TORCH))) {
            if (!this.currentSelections.containsKey(player)) return;
            CubicMarker marker = this.currentSelections.get(player);
            marker.getSelection().pasteSelection(hitBlock.getLocation().add(0, 1, 0));
            marker.removeMarker(true);
            this.currentSelections.remove(player);
        }


        if (!(player.getInventory().getItemInMainHand().getType().equals(Material.REDSTONE_TORCH))) return;


        if (!blockCache.containsKey(player)) {
            blockCache.put(player, new CubicMarker(player, MarkerExample.getMainCache(), player.getWorld(), BoundingBox.of(hitBlock, hitBlock)));
            blockCache.get(player).addController(this.controller).mark();
            player.sendMessage("1 block selected");
            return;
        }

        CubicMarker cubicMarker;

        if (this.currentSelections.containsKey(player)) {

            cubicMarker = this.currentSelections.get(player);
            cubicMarker.removeMarker(true);
            cubicMarker.setArea(BoundingBox.of(this.blockCache.get(event.getPlayer()).getArea().getCenter().toBlockVector().toLocation(player.getWorld()).getBlock(), hitBlock));

        } else {

            cubicMarker = new CubicMarker(player, MarkerExample.getMainCache(), hitBlock.getWorld(), BoundingBox.of(this.blockCache.get(event.getPlayer()).getArea().getCenter().toBlockVector().toLocation(player.getWorld()).getBlock(), hitBlock));
            this.currentSelections.put(player, cubicMarker);
        }

        cubicMarker.setArea(cubicMarker.getArea().expand(-0.001)).mark();
        player.sendMessage("area selected");
        this.blockCache.get(player).removeMarker(true);
        this.blockCache.remove(player);

    }

}
