package me.delta.mc.marker.examples;

import me.delta.mc.marker.apiv2.CubicMarker;
import me.delta.mc.marker.apiv2.MarkerBuilder;
import me.delta.mc.marker.apiv2.MarkerCache;

public class NewBuilderExample {

    public NewBuilderExample(){
        new MarkerBuilder().build(CubicMarker.class, null, cubicMarker -> {
            cubicMarker.test();
        });
    }
}
