package me.espryth.trollgui.mechanic.fakeore.event;

import net.minecraft.core.BlockPos;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class FakeOreBreakEvent extends Event {

  private static final HandlerList HANDLER_LIST = new HandlerList();

  private final Player player;
  private final BlockPos pos;

  public FakeOreBreakEvent(
      final @NotNull Player player,
      final @NotNull BlockPos pos
  ) {
    super(true);
    this.player = player;
    this.pos = pos;
  }

  public @NotNull Player player() {
    return player;
  }

  public @NotNull BlockPos pos() {
    return pos;
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLER_LIST;
  }

  public static @NotNull HandlerList getHandlerList() {
    return HANDLER_LIST;
  }
}
