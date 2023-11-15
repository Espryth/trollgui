package me.espryth.trollgui.mechanic.mob.listener;

import me.espryth.trollgui.mechanic.MechanicManager;
import me.espryth.trollgui.mechanic.mob.FakeMobMechanic;
import me.espryth.trollgui.mechanic.mob.FakeMobMechanicContext;
import me.espryth.trollgui.mechanic.mob.FakeMobMechanicContextSnapshot;
import me.espryth.trollgui.mechanic.mob.MobType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

public class FakeMobMechanicSnapshotListener
    implements Listener {

  private final MechanicManager mechanicManager;

  public FakeMobMechanicSnapshotListener(final @NotNull MechanicManager mechanicManager) {
    this.mechanicManager = mechanicManager;
  }

  @EventHandler(ignoreCancelled = true)
  public void handle(final PlayerInteractEvent event) {

    final var player = event.getPlayer();

    if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
      return;
    }

    final var item = event.getItem();

    if (item == null) {
      return;
    }

    final var snapshotManager = mechanicManager
        .getSnapshotManager(FakeMobMechanic.NAME, FakeMobMechanicContextSnapshot.class);

    final var snapshot = snapshotManager
        .getSnapshot(player);

    if (snapshot == null) {
      return;
    }

    snapshot.mobType(MobType.fromMaterial(item.getType()));
    snapshot.location(event.getInteractionPoint());

    final var context = snapshotManager
        .finishSnapshot(player);

    if (context == null) {
      player.sendPlainMessage(
          "The item clicked is not a valid mob spawn egg!"
      );
      return;
    }

    event.setCancelled(true);
    mechanicManager.getMechanic(FakeMobMechanic.NAME, FakeMobMechanicContext.class)
        .startHandling((FakeMobMechanicContext) context);

  }

}
