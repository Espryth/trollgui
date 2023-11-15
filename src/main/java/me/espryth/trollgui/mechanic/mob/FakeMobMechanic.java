package me.espryth.trollgui.mechanic.mob;

import me.espryth.trollgui.mechanic.context.Mechanic;
import me.espryth.trollgui.mechanic.mob.event.FakeMobHitEvent;
import me.espryth.trollgui.player.PlayerDataManager;
import org.jetbrains.annotations.NotNull;

public class FakeMobMechanic
    implements Mechanic<FakeMobMechanicContext, FakeMobHitEvent> {

  public static final String NAME = "fake-mob";

  private final FakeMobHandler fakeMobHandler;
  private final PlayerDataManager playerDataManager;

  public FakeMobMechanic(
      final @NotNull FakeMobHandler fakeMobHandler,
      final @NotNull PlayerDataManager playerDataManager
  ) {
    this.fakeMobHandler = fakeMobHandler;
    this.playerDataManager = playerDataManager;
  }

  @Override
  public void startHandling(final @NotNull FakeMobMechanicContext context) {
    fakeMobHandler.spawnFakeMob(
        context.player(),
        context.location(),
        context.mobType()
    );
  }

  @Override
  public void handle(final @NotNull FakeMobHitEvent event) {
    final var playerData = playerDataManager
        .getPlayerData(event.player());
    playerData.data(name()).increment();

    event.player().sendPlainMessage(
        "You have hit a fake mob!"
    );

  }

  @Override
  public @NotNull String name() {
    return NAME;
  }

  @Override
  public @NotNull Class<FakeMobHitEvent> eventType() {
    return FakeMobHitEvent.class;
  }
}
