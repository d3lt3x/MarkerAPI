package me.delta.mc.marker.api.holders;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BlockSelection {

    private final Map<BlockData, Set<Vector>> blockDataMap = new HashMap<>();

    public BlockSelection() {

    }

    public BlockSelection clearSelection() {
        this.blockDataMap.clear();
        return this;
    }

    public Map<BlockData, Set<Vector>> getBlockDataMap() {
        return new HashMap<>(this.blockDataMap);
    }

    public BlockSelection select(World world, BoundingBox box) {

        this.clearSelection();

        int minX = (int) Math.floor(box.getMinX());
        int minY = (int) Math.floor(box.getMinY());
        int minZ = (int) Math.floor(box.getMinZ());

        for (int x = minX; x <= Math.ceil(box.getMaxX()) - 1; x++) {
            for (int y = minY; y <= Math.ceil(box.getMaxY()) - 1; y++) {
                for (int z = minZ; z <= Math.ceil(box.getMaxZ()) - 1; z++) {

                    BlockData data = world.getBlockData(x, y, z);
                    Vector vector = new Vector(x - minX, y - minY, z - minZ);
                    this.blockDataMap.putIfAbsent(data, new HashSet<>());
                    this.blockDataMap.get(data).add(vector);
                }
            }
        }

        return this;
    }

    public BlockSelection rotateSelectionVertically(boolean clockwise, int quarter_rotations) {

        return this;
    }

    public BlockSelection rotateSelectionHorizontally(boolean clockwise, int quarter_rotations) {

        return this;
    }

    public BlockSelection flipSelection() {

        return this;
    }

    public void pasteSelection(Location baseLocation) {

        if (this.blockDataMap.isEmpty()) return;

        this.blockDataMap.entrySet().forEach(entry -> {
            entry.getValue().forEach(vector -> {
                baseLocation.clone().add(vector).getBlock().setBlockData(entry.getKey());
            });
        });
    }


}
