package me.espryth.trollgui.packet.intercept;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class PacketInterceptorHandler {

  private static final String CHANNEL_NAME = "trollgui:packet";

  public static <T> void registerPacketInterceptor(
      @NotNull final Class<T> packetClass,
      @NotNull final PacketInterceptor<T> packetInterceptor
  ) {
    PacketChannelDuplexHandler.addInterceptor(
        packetClass,
        packetInterceptor
    );
  }

  public static void injectPacketHandler(
      @NotNull final Player player
  ) {
    ServerGamePacketListenerImpl connection = ((CraftPlayer) player)
        .getHandle()
        .connection;

    connection
        .connection
        .channel.pipeline()
        .addBefore(
            "packet_handler",
            CHANNEL_NAME,
            new PacketChannelDuplexHandler(player)
        );
  }
}