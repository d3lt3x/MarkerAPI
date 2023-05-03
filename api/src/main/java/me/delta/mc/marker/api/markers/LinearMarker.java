package me.delta.mc.marker.api.markers;

import me.delta.mc.marker.api.holders.MarkerCache;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class LinearMarker extends Marker<LinearMarker> {

    private Location loc1;
    private Location loc2;

    private float width = 0.02F;

    public LinearMarker(@Nullable Player owner, MarkerCache cache, World world, Location loc1, Location loc2) {
        super(owner, cache, world);
        this.loc1 = loc1;
        this.loc2 = loc2;
    }

    @Override
    public void mark() {

        Vector vector = this.loc2.toVector().clone().subtract(this.loc1.toVector());
        Vector xAxis = new Vector(1, 0, 0);
        Vector rotationAxis = vector.clone().crossProduct(xAxis).normalize();
        float angle = -vector.angle(xAxis);

        if (vector.angle(xAxis) == 0)
            rotationAxis = new Vector(0, 0, 0);

        Transformation transformation = new Transformation(new Vector3f(), new AxisAngle4f(angle, (float) rotationAxis.getX(), (float) rotationAxis.getY(), (float) rotationAxis.getZ()), new Vector3f((float) vector.length(), this.width, this.width), new AxisAngle4f());

        super.spawnMarker(this.loc1, blockDisplay -> {
            blockDisplay.setTransformation(transformation);

        });

    }

    public Location getLoc1() {
        return loc1;
    }

    public LinearMarker setLoc1(Location loc1) {
        this.loc1 = loc1;
        return this;
    }

    public Location getLoc2() {
        return loc2;
    }

    public LinearMarker setLoc2(Location loc2) {
        this.loc2 = loc2;
        return this;
    }

    public float getWidth() {
        return width;
    }

    public LinearMarker setWidth(float width) {
        this.width = width;
        return this;
    }
}
