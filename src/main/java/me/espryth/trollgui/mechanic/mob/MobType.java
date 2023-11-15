package me.espryth.trollgui.mechanic.mob;

import java.util.function.BiFunction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.jetbrains.annotations.NotNull;

public enum MobType {

  ZOMBIE(
      (level, location) -> applyLocation(
          new Zombie(EntityType.ZOMBIE, level),
          location
      ),
      Material.ZOMBIE_SPAWN_EGG
  ),
  CREEPER(
      (level, location) -> applyLocation(
          new Creeper(EntityType.CREEPER, level),
          location
      ),
      Material.CREEPER_SPAWN_EGG
  ),
  SKELETON(
      (level, location) -> applyLocation(
          new Skeleton(EntityType.SKELETON, level),
          location
      ),
      Material.SKELETON_SPAWN_EGG
  );

  private final BiFunction<ServerLevel, Location, Entity> entityFactory;
  private final Material material;

  MobType(
      final @NotNull BiFunction<ServerLevel, Location, Entity> entityFactory,
      final @NotNull Material material
  ) {
    this.entityFactory = entityFactory;
    this.material = material;
  }

  public @NotNull Entity create(
      final @NotNull Location location
  ) {
    final var level = ((CraftWorld) location.getWorld()).getHandle();
    return entityFactory.apply(level, location);
  }

  private static Entity applyLocation(
      final @NotNull Entity entity,
      final @NotNull Location location
  ) {
    entity.setPos(
        location.getX(),
        location.getY(),
        location.getZ()
    );
    return entity;
  }

  public static MobType fromMaterial(final @NotNull Material material) {
    for (final var mobType : values()) {
      if (mobType.material == material) {
        return mobType;
      }
    }
    return null;
  }

}
