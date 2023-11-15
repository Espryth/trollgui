package me.espryth.trollgui.packet.intercept;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PacketInterceptor<Packet> {

  @Nullable Packet in(
      final @NotNull Player player,
      final @NotNull Packet packet
  );

}
