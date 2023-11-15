package me.espryth.trollgui.mechanic.mob;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.espryth.trollgui.packet.Packets;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FakeMobHandler {

  private final Map<UUID, IntList> watchedEntities = new HashMap<>();

  public FakeMobHandler() {}

  public void spawnFakeMob(
      final @NotNull Player viewer,
      final @NotNull Location location,
      final @NotNull MobType mobType
  ) {

    final var watching = watchedEntities.computeIfAbsent(
        viewer.getUniqueId(),
        player -> new IntArrayList()
    );

    final var mob = mobType.create(location);

    if (watching.add(mob.getId())) {
      Packets.send(
          viewer,
          new ClientboundAddEntityPacket(mob)
      );
    }
  }

  public boolean despawnFakeMob(
      final @NotNull Player viewer,
      final int entityId
  ) {
    final var watching = watchedEntities.get(viewer.getUniqueId());
    if (watching != null && watching.rem(entityId)) {
      Packets.send(
          viewer,
          new ClientboundRemoveEntitiesPacket(entityId)
      );
      return true;
    }
    return false;
  }

  public void despawnAll(
      final @NotNull Player viewer
  ) {
    final var watching = watchedEntities.get(viewer.getUniqueId());
    if (watching != null) {
      Packets.send(
          viewer,
          new ClientboundRemoveEntitiesPacket(watching)
      );
      watchedEntities.remove(viewer.getUniqueId());
    }
  }
}
