package me.espryth.trollgui.packet;

import java.util.Arrays;
import java.util.Collection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBundlePacket;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class Packets {

  private Packets() {
  }

  public static void send(
      final @NotNull Player player,
      final Packet<?> packet
  ) {
    ((CraftPlayer) player)
        .getHandle()
        .connection
        .send(packet);
  }

  @SuppressWarnings("unchecked")
  public static void send(
      final @NotNull Player player,
      final @NotNull Packet<ClientGamePacketListener>... packets
  ) {
    if (packets.length == 0) {
      return;
    }

    final var connection = ((CraftPlayer) player)
        .getHandle()
        .connection;

    final var packet = packets.length == 1
        ? packets[0]
        : new ClientboundBundlePacket(Arrays.asList(packets));

    connection.send(packet);
  }

  public static void send(
      final @NotNull Player player,
      final @NotNull Collection<Packet<ClientGamePacketListener>> packets
  ) {
    if (packets.isEmpty()) {
      return;
    }

    final var connection = ((CraftPlayer) player)
        .getHandle()
        .connection;

    final var packet = packets.size() == 1
        ? packets.iterator().next()
        : new ClientboundBundlePacket(packets);

    connection.send(packet);
  }
}