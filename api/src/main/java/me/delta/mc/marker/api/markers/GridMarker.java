package me.delta.mc.marker.api.markers;

import me.delta.mc.marker.api.holders.BlockSelection;
import me.delta.mc.marker.api.holders.MarkerCache;
import me.delta.mc.marker.api.util.Line;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

public class GridMarker extends LinearMarker {

    private BoundingBox area;
    private BlockSelection selection = new BlockSelection();

    public GridMarker(@Nullable Player owner, MarkerCache cache, World world, BoundingBox area) {
        super(owner, cache, world, null);
        this.area = area;
        super.setLines(this.updateLines());
    }

    private Line get() {
        return null;
    }

    public BoundingBox getArea() {
        return area;
    }

    public GridMarker setArea(BoundingBox area) {
        this.area = area;
        super.setLines(this.updateLines());
        return this;
    }

    private Line[] updateLines() {
        return new Line[]{
                new Line(area.getMin(), area.getMin().clone().add(new Vector(0, area.getHeight(), 0))),
                new Line(area.getMin(), area.getMin().clone().add(new Vector(area.getWidthX(), 0, 0))),
                new Line(area.getMin(), area.getMin().clone().add(new Vector(0, 0, area.getWidthZ()))),
                new Line(area.getMax(), area.getMax().clone().subtract(new Vector(0, area.getHeight(), 0))),
                new Line(area.getMax(), area.getMax().clone().subtract(new Vector(area.getWidthX(), 0, 0))),
                new Line(area.getMax(), area.getMax().clone().subtract(new Vector(0, 0, area.getWidthZ()))),

                new Line(area.getMin().clone().add(new Vector(0, area.getHeight(), 0)), area.getMin().clone().add(new Vector(area.getWidthX(), area.getHeight(), 0))),
                new Line(area.getMin().clone().add(new Vector(0, area.getHeight(), 0)), area.getMin().clone().add(new Vector(0, area.getHeight(), area.getWidthZ()))),

                new Line(area.getMax().clone().subtract(new Vector(0, area.getHeight(), 0)), area.getMax().clone().subtract(new Vector(area.getWidthX(), area.getHeight(), 0))),
                new Line(area.getMax().clone().subtract(new Vector(0, area.getHeight(), 0)), area.getMax().clone().subtract(new Vector(0, area.getHeight(), area.getWidthZ()))),

                new Line(area.getMin().clone().add((new Vector(0, 0, area.getWidthZ()))), area.getMin().clone().add(new Vector(0, area.getHeight(), area.getWidthZ()))),
                new Line(area.getMin().clone().add(new Vector(area.getWidthX(), 0, 0)), area.getMin().clone().add(new Vector(area.getWidthX(), area.getHeight(), 0)))};
    }

    @Override
    public void mark() {
        super.mark();
        this.selection.select(super.getWorld(), this.getArea());
    }

    public BlockSelection getSelection() {
        return this.selection;
    }

}
