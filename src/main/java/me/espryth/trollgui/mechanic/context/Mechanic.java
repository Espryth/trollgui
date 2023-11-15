package me.espryth.trollgui.mechanic.context;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public interface Mechanic<C extends MechanicContext, E extends Event>
    extends Listener {

  void startHandling(final @NotNull C context);

  default void stopHandling(final @NotNull C context) {}

  void handle(final @NotNull E event);

  @NotNull String name();

  @NotNull Class<E> eventType();

}
