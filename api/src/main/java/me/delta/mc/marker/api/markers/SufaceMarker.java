package me.delta.mc.marker.api.markers;

import me.delta.mc.marker.api.holders.MarkerCache;
import org.bukkit.entity.BlockDisplay;

public class SufaceMarker extends Marker<SufaceMarker>{


    public SufaceMarker(MarkerCache cache) {
        super(cache);
    }

    public enum SURFACE_SIDE{
        TOP,
        BOTTOM,
        NEG_X,
        NEG_Z,
        POS_Z,
        POS_X
    }

    @Override
    public SufaceMarker createMarker(BlockDisplay display) {
        return null;
    }
}
