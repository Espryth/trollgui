package me.espryth.trollgui.mechanic.fakeore.listener;

import me.espryth.trollgui.mechanic.MechanicManager;
import me.espryth.trollgui.mechanic.fakeore.FakeOreMechanic;
import me.espryth.trollgui.mechanic.fakeore.FakeOreMechanicContext;
import me.espryth.trollgui.mechanic.fakeore.FakeOreMechanicContextSnapshot;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class FakeOreMechanicSnapshotListener implements Listener {

  private final Plugin plugin;
  private final MechanicManager mechanicManager;

  public FakeOreMechanicSnapshotListener(
      final Plugin plugin,
      final MechanicManager mechanicManager
  ) {
    this.plugin = plugin;
    this.mechanicManager = mechanicManager;
  }

  @EventHandler(ignoreCancelled = true)
  public void onPlace(final @NotNull BlockPlaceEvent event) {
    final var player = event.getPlayer();

    final var snapshotManager = mechanicManager
        .getSnapshotManager(FakeOreMechanic.NAME, FakeOreMechanicContextSnapshot.class);
    final var snapshot = snapshotManager
        .getSnapshot(player);

    if (snapshot == null) {
      return;
    }

    snapshot.block(event.getBlock());

    final var context = snapshotManager
        .finishSnapshot(player);

    if (context == null) {
      player.sendPlainMessage(
          "The block placed is not a valid ore!"
      );
      return;
    }

    event.setCancelled(true);
    Bukkit.getScheduler().runTaskLater(plugin, () -> {
      mechanicManager.getMechanic(FakeOreMechanic.NAME, FakeOreMechanicContext.class)
          .startHandling((FakeOreMechanicContext) context);
    }, 10);

  }

}
