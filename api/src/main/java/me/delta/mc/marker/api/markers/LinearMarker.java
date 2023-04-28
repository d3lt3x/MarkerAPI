package me.delta.mc.marker.api.markers;

import me.delta.mc.marker.api.holders.MarkerCache;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class LinearMarker extends Marker<LinearMarker> {


    public LinearMarker(@Nullable Player owner, MarkerCache cache, World world) {
        super(owner, cache, world);
    }

    @Override
    public void mark() {

    }

    @Override
    public Marker<LinearMarker> updateMarker() {
        return this;
    }
}
