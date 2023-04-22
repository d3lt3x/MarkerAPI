package me.delta.mc.marker.api;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

public class MarkerBuilder {

  private World world;
  private BoundingBox markerBox;
  private BlockData markerBlock = Material.LIME_STAINED_GLASS.createBlockData();
  private Color glowColor = Color.LIME;
  private RGBController rgbController;
  private BoundingBox actualBox;

  public MarkerBuilder(@NotNull Block corner1, @NotNull Block corner2) {
    this.world = corner1.getWorld();
    this.actualBox = BoundingBox.of(corner1, corner2);
    this.markerBox = this.actualBox;
  }

  public MarkerBuilder(@NotNull World world, @NotNull BoundingBox box) {
    this.world = world;
    this.actualBox = box;
    this.markerBox = box;
  }

  public World getWorld() {
    return world;
  }

  public MarkerBuilder setWorld(World world) {
    this.world = world;
    return this;
  }

  public BlockData getMarkerBlock() {
    return markerBlock;
  }

  public MarkerBuilder setMarkerBlock(BlockData markerBlock) {
    this.markerBlock = markerBlock;
    return this;
  }

  public BoundingBox getMarkerBox() {
    return markerBox;
  }

  public MarkerBuilder setMarkerBox(BoundingBox box) {
    this.markerBox = box;
    return this;
  }

  public BoundingBox getActualBox() {
    return actualBox;
  }

  public MarkerBuilder setActualBox(BoundingBox box) {
    this.actualBox = box;
    return this;
  }

  public MarkerBuilder expandMarker(double expansion) {
    this.markerBox.expand(expansion);
    return this;
  }

  public MarkerBuilder setBox(BoundingBox box) {
    this.setActualBox(box);
    this.setMarkerBox(box);
    return this;
  }

  public Color getGlowColor() {
    return glowColor;
  }

  public MarkerBuilder setGlowColor(Color glowColor) {
    this.glowColor = glowColor;
    return this;
  }

  public RGBController getRgbController() {
    return rgbController;
  }

  public MarkerBuilder setRgbController(RGBController rgbController) {
    this.rgbController = rgbController;
    return this;
  }

  public AreaMarker build() {
    return new AreaMarker(
        this.world,
        this.glowColor,
        this.markerBlock,
        this.actualBox,
        this.markerBox,
        this.rgbController);
  }
}
