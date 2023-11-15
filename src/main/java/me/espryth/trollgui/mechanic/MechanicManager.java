package me.espryth.trollgui.mechanic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import me.espryth.trollgui.mechanic.context.Mechanic;
import me.espryth.trollgui.mechanic.context.MechanicContext;
import me.espryth.trollgui.mechanic.context.MechanicContextSnapshot;
import me.espryth.trollgui.mechanic.context.MechanicSnapshotManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class MechanicManager {

  private final Map<String, Mechanic<?, ?>> mechanics = new HashMap<>();
  private final Map<String, MechanicSnapshotManager<?, ?>> snapshotManagerMap = new HashMap<>();

  private final Plugin plugin;


  public MechanicManager(Plugin plugin) {
    this.plugin = plugin;
  }

  @SuppressWarnings("unchecked")
  public <C extends MechanicContext> @NotNull Mechanic<C, ?> getMechanic(
      final @NotNull String name,
      final @NotNull Class<C> contextType
  ) {
    return Objects.requireNonNull((Mechanic<C, ?>) mechanics.get(name));
  }

  public @NotNull Collection<Mechanic<?, ?>> getMechanics() {
    return mechanics.values();
  }

  @SuppressWarnings("unchecked")
  public <S extends MechanicContextSnapshot<?>> @NotNull MechanicSnapshotManager<?, S> getSnapshotManager(
      final @NotNull String name,
      final @NotNull Class<S> snapshotType
  ) {
    return (MechanicSnapshotManager<?, S>) snapshotManagerMap.computeIfAbsent(
        name,
        key -> new MechanicSnapshotManager<>()
    );
  }

  public void registerMechanic(
      final @NotNull Mechanic<?, ?> mechanic
  ) {
    mechanics.put(mechanic.name(), mechanic);
    Bukkit.getPluginManager().registerEvent(
        mechanic.eventType(),
        mechanic,
        EventPriority.NORMAL,
        new MechanicEventExecutor<>(mechanic),
        plugin
    );
  }
}
