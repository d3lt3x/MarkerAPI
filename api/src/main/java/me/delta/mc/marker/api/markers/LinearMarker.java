package me.delta.mc.marker.api.markers;

import me.delta.mc.marker.api.holders.MarkerCache;
import org.bukkit.entity.BlockDisplay;

public class LinearMarker extends Marker<LinearMarker> {
    public LinearMarker(MarkerCache cache) {
        super(cache);
    }

    @Override
    public LinearMarker createMarker(BlockDisplay display) {
        return null;
    }
}
