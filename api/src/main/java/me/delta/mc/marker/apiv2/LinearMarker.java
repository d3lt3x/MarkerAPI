package me.delta.mc.marker.apiv2;

import org.bukkit.entity.BlockDisplay;

public class LinearMarker extends SimpleDisplay{


    public LinearMarker(MarkerCache cache) {
        super(cache);
    }

    @Override
    public SimpleDisplay createMarker(BlockDisplay display) {
        return null;
    }
}
