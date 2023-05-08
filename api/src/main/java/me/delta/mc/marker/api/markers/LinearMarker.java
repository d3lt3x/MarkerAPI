package me.delta.mc.marker.api.markers;

import me.delta.mc.marker.api.holders.MarkerCache;
import me.delta.mc.marker.api.util.Line;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class LinearMarker extends Marker<LinearMarker> {

    private Line[] lines;

    private float width = 0.02F;

    public LinearMarker(@Nullable Player owner, MarkerCache cache, World world, Line... lines) {
        super(owner, cache, world);
        this.lines = lines;
    }

    @Override
    public void mark() {

        for (Line line : this.lines) {

            Vector vector = line.asVector();
            Vector xAxis = new Vector(1, 0, 0);
            Vector rotationAxis = vector.clone().crossProduct(xAxis).normalize();
            float angle = -vector.angle(xAxis);

            if (vector.angle(xAxis) == 0)
                rotationAxis = new Vector(0, 0, 0);

            Transformation transformation = new Transformation(new Vector3f(), new AxisAngle4f(angle, (float) rotationAxis.getX(), (float) rotationAxis.getY(), (float) rotationAxis.getZ()), new Vector3f((float) vector.length(), this.width, this.width), new AxisAngle4f());

            super.spawnMarker(line.base().toLocation(super.getWorld()), blockDisplay -> {
                blockDisplay.setTransformation(transformation);

            });

        }

    }

    public Line[] getLines() {
        return this.lines;
    }

    public LinearMarker setLines(Line... lines) {
        this.lines = lines;
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
