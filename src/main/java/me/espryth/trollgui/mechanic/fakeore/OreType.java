package me.espryth.trollgui.mechanic.fakeore;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum OreType {

  DIAMOND(Material.DIAMOND_ORE),
  EMERALD(Material.EMERALD_ORE),
  GOLD(Material.GOLD_ORE),
  IRON(Material.IRON_ORE),
  LAPIS(Material.LAPIS_ORE),
  REDSTONE(Material.REDSTONE_ORE),
  NETHERITE(Material.ANCIENT_DEBRIS);

  private final Material material;

  OreType(Material material) {
    this.material = material;
  }

  public @NotNull Material material() {
    return material;
  }

  public static @Nullable OreType fromMaterial(final @NotNull Material material) {
    for (final var type  : values()) {
      if (type.material() == material) {
        return type;
      }
    }
    return null;
  }
}
