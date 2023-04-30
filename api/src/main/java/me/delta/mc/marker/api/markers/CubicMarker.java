package me.delta.mc.marker.api.markers;

import me.delta.mc.marker.api.holders.BlockSelection;
import me.delta.mc.marker.api.holders.MarkerCache;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.Nullable;


public class CubicMarker extends SurfaceMarker {

    private BlockSelection selection = new BlockSelection();

    public CubicMarker(@Nullable Player owner, MarkerCache cache, World world, BoundingBox area) {
        super(owner, cache, world, area);
        super.setSurfaceSides(SURFACE_SIDE.values());
    }

    @Override
    public void mark() {
        super.mark();
        this.selection.select(super.getWorld(), super.getArea());
    }

    public BlockSelection getSelection() {
        return this.selection;
    }

}

