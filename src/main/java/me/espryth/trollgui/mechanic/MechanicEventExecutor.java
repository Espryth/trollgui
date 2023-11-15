package me.espryth.trollgui.mechanic;

import me.espryth.trollgui.mechanic.context.Mechanic;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

final class MechanicEventExecutor<E extends Event>
    implements EventExecutor {

  private final Mechanic<?, E> mechanic;

  public MechanicEventExecutor(final @NotNull Mechanic<?, E> mechanic) {
    this.mechanic = mechanic;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void execute(
      final @NotNull Listener listener,
      final @NotNull Event event
  ) throws EventException {
    mechanic.handle((E) event);
  }

}
