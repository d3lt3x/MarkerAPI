package me.delta.mc.marker.examples;

import me.delta.mc.marker.apiv2.CubicMarker;
import me.delta.mc.marker.apiv2.MarkerBuilder;

public class NewBuilderExample {

    public NewBuilderExample(){
        new MarkerBuilder().build(CubicMarker.class, null, cubicMarker -> {
            cubicMarker.test();
        });
    }
}
