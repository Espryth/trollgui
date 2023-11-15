package me.espryth.trollgui.mechanic.fakeore.event;

import me.espryth.trollgui.mechanic.fakeore.FakeOreRegistry;
import me.espryth.trollgui.packet.intercept.PacketInterceptor;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket.Action;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FakeOreBreakEventDispatcher
    implements PacketInterceptor<ServerboundPlayerActionPacket> {

  private final FakeOreRegistry fakeOreRegistry;

  public FakeOreBreakEventDispatcher(FakeOreRegistry fakeOreRegistry) {
    this.fakeOreRegistry = fakeOreRegistry;
  }

  @Override
  public @Nullable ServerboundPlayerActionPacket in(
      final @NotNull Player player,
      final @NotNull ServerboundPlayerActionPacket packet
  ) {
    if (packet.getAction() == Action.START_DESTROY_BLOCK) {
      final var pos = packet.getPos();
      if (fakeOreRegistry.removeFakeOre(player, pos)) {
        new FakeOreBreakEvent(player, pos).callEvent();
      }
    }
    return packet;
  }
}