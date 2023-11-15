package me.espryth.trollgui.packet.intercept;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PacketChannelDuplexHandler extends ChannelDuplexHandler {

  private static final Map<Class<?>, PacketInterceptor<?>> INTERCEPTORS =
      new HashMap<>();

  private final WeakReference<Player> playerReference;

  public PacketChannelDuplexHandler(Player player) {
    this.playerReference = new WeakReference<>(player);
  }

  public static <T> void addInterceptor(Class<T> packetType, PacketInterceptor<T> interceptor) {
    INTERCEPTORS.putIfAbsent(packetType, interceptor);
  }

  @Override
  public void channelRead(@NotNull ChannelHandlerContext ctx, @NotNull Object packet) throws Exception {
    handleRead(ctx, packet);
  }

  private <T> void handleRead(ChannelHandlerContext ctx, T packet) throws Exception {
    @SuppressWarnings("unchecked")
    final var interceptor = (PacketInterceptor<T>) INTERCEPTORS.get(packet.getClass());
    if (interceptor == null) {
      super.channelRead(ctx, packet);
    } else {
      try {
        final var player = playerReference.get();

        if (player == null) {
          return;
        }

        packet = interceptor.in(player, packet);
      } catch (Throwable error) {
        throw new RuntimeException(error);
      }
      if (packet != null) {
        super.channelRead(ctx, packet);
      }
    }
  }

}