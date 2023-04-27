package me.delta.mc.marker.api.markers;

import me.delta.mc.marker.api.holders.MarkerCache;
import org.bukkit.entity.BlockDisplay;


public class CubicMarker extends Marker<CubicMarker>{


    public CubicMarker(MarkerCache cache) {
        super(cache);
    }

    @Override
    public CubicMarker createMarker(BlockDisplay display) {
        return null;
    }
}
