package me.delta.mc.marker.api.util;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public record Line(Vector base, Vector dest) {


    public Line(Location base, Location dest) {
        this(base.toVector(), dest.toVector());
    }

    @Override
    public Vector base() {

        Vector vector1 = new Vector(1, 1, 1);

        if (this.dest.dot(vector1) < this.base.dot(vector1))
            return dest;

        return base;
    }

    @Override
    public Vector dest() {

        Vector vector1 = new Vector(1, 1, 1);

        if (this.dest.dot(vector1) < this.base.dot(vector1))
            return base;

        return dest;
    }

    public Vector asVector() {
        return this.dest().clone().subtract(this.base());
    }
}
