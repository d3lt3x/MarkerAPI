package me.delta.mc.marker.apiv2;

import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;

public class AreaMarker extends SimpleDisplay{

    public AreaMarker(MarkerCache cache) {
        super(cache);
    }

    @Override
    public SimpleDisplay createMarker(BlockDisplay display) {
        return null;
    }
}
