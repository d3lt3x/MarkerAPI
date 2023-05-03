package me.delta.mc.marker.api.markers;

import me.delta.mc.marker.api.holders.MarkerCache;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class SurfaceMarker extends Marker<SurfaceMarker> {
    private BoundingBox area;
    private SURFACE_SIDE[] surfaceSides;

    public SurfaceMarker(@Nullable Player owner, MarkerCache cache, World world, BoundingBox area, SURFACE_SIDE... surfaceSides) {
        super(owner, cache, world);
        this.area = area;
        this.surfaceSides = surfaceSides;
    }

    @Override
    public void mark() {

        Location loc1 = new BlockVector(this.area.getMin()).toLocation(super.getWorld());
        Location loc2 = new BlockVector(this.area.getMax()).toLocation(super.getWorld());

        for (int i = 0; i < this.surfaceSides.length; i++) {
            switch (this.surfaceSides[i]) {
                case NEG_Z -> super.spawnMarker(loc1, display -> display.setTransformation(super.scale(new Vector3f((float) this.area.getWidthX(), (float) this.area.getHeight(), 0))));
                case NEG_X -> super.spawnMarker(loc1, display -> display.setTransformation(super.scale(new Vector3f(0, (float) this.area.getHeight(), (float) this.area.getWidthZ()))));
                case BOTTOM -> super.spawnMarker(loc1, display -> display.setTransformation(super.scale(new Vector3f((float) this.area.getWidthX(), 0, (float) this.area.getWidthZ()))));
                case POS_Z -> super.spawnMarker(loc2, display -> display.setTransformation(super.scale(new Vector3f((float) -this.area.getWidthX(), (float) -this.area.getHeight(), 0))));
                case POS_X -> super.spawnMarker(loc2, display -> display.setTransformation(super.scale(new Vector3f(0, (float) -this.area.getHeight(), (float) -this.area.getWidthZ()))));
                case TOP -> super.spawnMarker(loc2, display -> display.setTransformation(super.scale(new Vector3f((float) -this.area.getWidthX(), 0, (float) -this.area.getWidthZ()))));
            }
        }
    }



    public SURFACE_SIDE[] getSurfaceSides() {
        return this.surfaceSides.clone();
    }

    public SurfaceMarker setSurfaceSides(SURFACE_SIDE... surfaceSides) {
        this.surfaceSides = surfaceSides;
        return this;
    }

    public BoundingBox getArea() {
        return this.area.clone();
    }

    public SurfaceMarker setArea(BoundingBox area) {
        this.area = area;
        return this;
    }

    public enum SURFACE_SIDE {
        TOP, BOTTOM, NEG_X, NEG_Z, POS_Z, POS_X
    }
}
