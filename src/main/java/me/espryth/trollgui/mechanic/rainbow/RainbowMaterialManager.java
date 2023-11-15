package me.espryth.trollgui.mechanic.rainbow;

import java.util.List;
import java.util.Random;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class RainbowMaterialManager {

  private static final List<Material> RAINBOW_MATERIALS = List.of(
      Material.RED_STAINED_GLASS,
      Material.ORANGE_STAINED_GLASS,
      Material.YELLOW_STAINED_GLASS,
      Material.LIME_STAINED_GLASS,
      Material.GREEN_STAINED_GLASS,
      Material.LIGHT_BLUE_STAINED_GLASS,
      Material.BLUE_STAINED_GLASS,
      Material.PURPLE_STAINED_GLASS
  );

  private static final Random RANDOM = new Random();

  public static @NotNull Material getRandom() {
    return RAINBOW_MATERIALS.get(RANDOM.nextInt(RAINBOW_MATERIALS.size()));
  }

}
