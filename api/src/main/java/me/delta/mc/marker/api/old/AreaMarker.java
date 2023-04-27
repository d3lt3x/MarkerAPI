package me.delta.mc.marker.api.old;

import java.util.HashSet;
import java.util.Set;

import me.delta.mc.marker.api.controllers.RGBController;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.util.BlockVector;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

public class AreaMarker {
  private World world;
  private Color color;
  private BlockData markerBlock;
  private BoundingBox actualBox;
  private BoundingBox markerBox;
  private RGBController rgbController;
  private final Set<BlockDisplay> markerDisplays = new HashSet<>();

  private Area area;

  public AreaMarker(
      World world,
      Color color,
      BlockData markerBlock,
      BoundingBox actualBox,
      BoundingBox markerBox,
      RGBController rgbController) {
    this.world = world;
    this.color = color;
    this.markerBlock = markerBlock;
    this.actualBox = actualBox;
    this.markerBox = markerBox;
    this.rgbController = rgbController;
  }

  public AreaMarker markArea(Area area) {

    this.area = area;

    if (!this.markerDisplays.isEmpty()) this.unMark();

    Location loc1 = new BlockVector(this.markerBox.getMin()).toLocation(this.world);
    Location loc2 = new BlockVector(this.markerBox.getMax()).toLocation(this.world);

    switch (area) {
      case FULL -> {
        this.spawnDisplay(
            this.scale(
                new Vector3f(
                    (float) this.markerBox.getWidthX(), (float) this.markerBox.getHeight(), 0)),
            loc1);
        this.spawnDisplay(
            this.scale(
                new Vector3f(
                    0, (float) this.markerBox.getHeight(), (float) this.markerBox.getWidthZ())),
            loc1);
        this.spawnDisplay(
            this.scale(
                new Vector3f(
                    (float) this.markerBox.getWidthX(), 0, (float) this.markerBox.getWidthZ())),
            loc1);

        this.spawnDisplay(
            this.scale(
                new Vector3f(
                    (float) -this.markerBox.getWidthX(), (float) -this.markerBox.getHeight(), 0)),
            loc2);
        this.spawnDisplay(
            this.scale(
                new Vector3f(
                    0, (float) -this.markerBox.getHeight(), (float) -this.markerBox.getWidthZ())),
            loc2);
        this.spawnDisplay(
            this.scale(
                new Vector3f(
                    (float) -this.markerBox.getWidthX(), 0, (float) -this.markerBox.getWidthZ())),
            loc2);
      }

      case NEGATIVE_Z -> this.spawnDisplay(
          this.scale(
              new Vector3f(
                  (float) this.markerBox.getWidthX(), (float) this.markerBox.getHeight(), 0)),
          loc1);
      case NEGATIVE_X -> this.spawnDisplay(
          this.scale(
              new Vector3f(
                  0, (float) this.markerBox.getHeight(), (float) this.markerBox.getWidthZ())),
          loc1);
      case BOTTOM -> this.spawnDisplay(
          this.scale(
              new Vector3f(
                  (float) this.markerBox.getWidthX(), 0, (float) this.markerBox.getWidthZ())),
          loc1);
      case POSITIVE_Z -> this.spawnDisplay(
          this.scale(
              new Vector3f(
                  (float) -this.markerBox.getWidthX(), (float) -this.markerBox.getHeight(), 0)),
          loc2);
      case POSITIVE_X -> this.spawnDisplay(
          this.scale(
              new Vector3f(
                  0, (float) -this.markerBox.getHeight(), (float) -this.markerBox.getWidthZ())),
          loc2);
      case TOP -> this.spawnDisplay(
          this.scale(
              new Vector3f(
                  (float) -this.markerBox.getWidthX(), 0, (float) -this.markerBox.getWidthZ())),
          loc2);
    }

    return this;
  }

  private Transformation scale(Vector3f vector3f) {
    return new Transformation(new Vector3f(), new AxisAngle4f(), vector3f, new AxisAngle4f());
  }

  private void spawnDisplay(Transformation transformation, Location location) {

    this.world.spawn(
        location,
        BlockDisplay.class,
        blockDisplay -> {
          if (this.rgbController != null) this.rgbController.addDisplay(blockDisplay);
          else {
            blockDisplay.setGlowColorOverride(this.color);
            blockDisplay.setGlowing(true);
          }
          blockDisplay.setBlock(this.markerBlock);
          blockDisplay.setGravity(false);
          blockDisplay.setInvulnerable(true);
          blockDisplay.setPersistent(true);
          blockDisplay.setTransformation(transformation);
          blockDisplay.setBrightness(new Display.Brightness(15, 15));
          blockDisplay.setShadowStrength(0);
          blockDisplay.setShadowRadius(0);

          this.markerDisplays.add(blockDisplay);
        });
  }

  public void unMark() {
    this.markerDisplays.forEach(blockDisplay -> blockDisplay.remove());
    this.markerDisplays.clear();
  }

  public AreaMarker expandMarker(double expansion) {
    this.markerBox.expand(expansion);
    return this;
  }

  public Set<BlockDisplay> getMarkerDisplays() {
    return this.markerDisplays;
  }

  public BoundingBox getActualBox() {
    return this.actualBox.clone();
  }

  public AreaMarker setActualBox(BoundingBox actualBox) {
    this.actualBox = actualBox;
    return this;
  }

  public AreaMarker setBox(BoundingBox box) {
    this.setActualBox(box);
    this.setMarkerBox(box);
    return this;
  }

  public RGBController getRgbController() {
    return this.rgbController;
  }

  public void setRgbController(RGBController rgbController) {
    this.rgbController = rgbController;
  }

  public BoundingBox getMarkerBox() {
    return this.markerBox.clone();
  }

  public AreaMarker setMarkerBox(BoundingBox markerBox) {
    this.markerBox = markerBox;
    return this;
  }

  public BlockData getMarkerBlock() {
    return this.markerBlock.clone();
  }

  public AreaMarker setMarkerBlock(BlockData markerBlock) {
    this.markerBlock = markerBlock;
    return this;
  }

  public World getWorld() {
    return world;
  }

  public AreaMarker setWorld(World world) {
    this.world = world;
    return this;
  }

  public Color getColor() {
    return color;
  }

  public AreaMarker setColor(Color color) {
    this.color = color;
    return this;
  }
}
