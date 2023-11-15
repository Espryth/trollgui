package me.espryth.trollgui.mechanic.mob.event;

import me.espryth.trollgui.mechanic.mob.FakeMobHandler;
import me.espryth.trollgui.packet.intercept.PacketInterceptor;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FakeMobHitEventDispatcher
    implements PacketInterceptor<ServerboundInteractPacket> {

  private final FakeMobHandler fakeMobHandler;

  public FakeMobHitEventDispatcher(final @NotNull FakeMobHandler fakeMobHandler) {
    this.fakeMobHandler = fakeMobHandler;
  }

  @Override
  public @Nullable ServerboundInteractPacket in(
      final @NotNull Player player,
      final @NotNull ServerboundInteractPacket packet
  ) {
    if (fakeMobHandler.despawnFakeMob(player, packet.getEntityId())) {
      new FakeMobHitEvent(player, packet.getEntityId()).callEvent();
    }
    return packet;
  }
}
