package me.espryth.trollgui.mechanic.mob.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class FakeMobHitEvent extends Event {

  private static final HandlerList HANDLER_LIST = new HandlerList();

  private final Player player;
  private final int entityId;

  public FakeMobHitEvent(
      final @NotNull Player player,
      final int entityId
  ) {
    super(true);
    this.player = player;
    this.entityId = entityId;
  }

  public @NotNull Player player() {
    return player;
  }

  public int entityId() {
    return entityId;
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLER_LIST;
  }

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }
}
