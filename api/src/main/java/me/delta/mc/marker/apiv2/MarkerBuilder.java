package me.delta.mc.marker.apiv2;


import org.bukkit.Location;
import org.bukkit.entity.Display;

import java.util.function.Consumer;

public class MarkerBuilder {


    private MarkerCache cache;

    public MarkerBuilder(){

    }
    public <T extends SimpleDisplay> T build(Class<T> clazz, Location location, Consumer<T> consumer){

        T t = null;

       if(clazz.equals(LinearMarker.class)) {
           t = (T) new LinearMarker(cache);
           consumer.accept(t);
       }

       if(t == null)
           throw new IllegalArgumentException();

       return t;
    }

    public MarkerCache getCache() {
        return cache;
    }

    public MarkerBuilder setCache(MarkerCache cache) {
        this.cache = cache;
        return this;
    }
}
