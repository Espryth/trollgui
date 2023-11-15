package me.espryth.trollgui.mechanic.fakeore;

import me.espryth.trollgui.mechanic.context.Mechanic;
import me.espryth.trollgui.mechanic.fakeore.event.FakeOreBreakEvent;
import me.espryth.trollgui.player.PlayerDataManager;
import org.jetbrains.annotations.NotNull;

public class FakeOreMechanic
    implements Mechanic<FakeOreMechanicContext, FakeOreBreakEvent> {

  public static final String NAME = "fake-ore";

  private final FakeOreRegistry fakeOreRegistry;
  private final PlayerDataManager playerDataManager;

  public FakeOreMechanic(FakeOreRegistry fakeOreRegistry, PlayerDataManager playerDataManager) {
    this.fakeOreRegistry = fakeOreRegistry;
    this.playerDataManager = playerDataManager;
  }

  @Override
  public void startHandling(final @NotNull FakeOreMechanicContext context) {
    fakeOreRegistry.setFakeOre(
        context.player(),
        context.location(),
        context.oreType()
    );
  }

  @Override
  public void handle(final @NotNull FakeOreBreakEvent event) {
    final var playerData = playerDataManager
        .getPlayerData(event.player());
    playerData.data(name()).increment();
    event.player().sendPlainMessage(
        "You have broken a fake ore!"
    );
  }

  @Override
  public @NotNull String name() {
    return NAME;
  }

  @Override
  public @NotNull Class<FakeOreBreakEvent> eventType() {
    return FakeOreBreakEvent.class;
  }

}
