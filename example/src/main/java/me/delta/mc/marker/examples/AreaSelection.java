package me.delta.mc.marker.examples;

import me.delta.mc.marker.MarkerExample;
import me.delta.mc.marker.api.controllers.FloatingController;
import me.delta.mc.marker.api.controllers.RGBController;
import me.delta.mc.marker.api.markers.CubicMarker;
import me.delta.mc.marker.api.markers.GridMarker;
import me.delta.mc.marker.api.markers.LinearMarker;
import me.delta.mc.marker.api.util.Line;
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
    private final Map<Player, GridMarker> currentPreview = new HashMap<>();

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

            GridMarker preview;

            if (!this.currentPreview.containsKey(player)) {

                preview = new GridMarker(player, MarkerExample.getMainCache(), destination.getWorld(), BoundingBox.of(destination.toVector(), destination.toVector().add(marker.getArea().getMax().subtract(marker.getArea().getMin()))));
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

        Player player = event.getPlayer();

        if (player.getInventory().getItemInMainHand().getType().equals(Material.APPLE)) {
            if (event.getClickedBlock() == null)
                return;
            Location location = event.getClickedBlock().getLocation().add(0, 1, 0);
            LinearMarker marker = new LinearMarker(player, MarkerExample.getMainCache(), player.getWorld());
            marker.addController(new FloatingController(MarkerExample.getInstance(), 0, 0.04F));
            marker.addController(this.controller);
            marker.setWidth(0.01F);
            marker.setLines(
                    new Line(location.clone().add(0, 0, 1), location),
                    new Line(location.clone().add(1, 0, 0), location),
                    new Line(location.clone().add(1, 0, 0), location.clone().add(1, 0, 1)),
                    new Line(location.clone().add(1, 0, 1), location.clone().add(0, 0, 1)),
                    new Line(location.clone().add(0.5, 1, 0.5), location),
                    new Line(location.clone().add(0.5, 1, 0.5), location.clone().add(1, 0, 0)),
                    new Line(location.clone().add(0, 0, 1), location.clone().add(0.5, 1, 0.5)),
                    new Line(location.clone().add(0.5, 1, 0.5), location.clone().add(1, 0, 1))
            ).mark();
            return;
        }

        if (!event.getAction().isLeftClick()) return;

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
            player.sendMessage("pasted");
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

        cubicMarker.mark();
        player.sendMessage("area selected");
        this.currentPos.get(player).removeMarker(true);
        this.currentPos.remove(player);
        this.blockCache.get(player).removeMarker(true);
        this.blockCache.remove(player);

    }

}
