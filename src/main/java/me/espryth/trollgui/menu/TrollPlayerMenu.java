package me.espryth.trollgui.menu;

import java.time.Duration;
import java.util.function.Supplier;
import me.espryth.trollgui.mechanic.MechanicManager;
import me.espryth.trollgui.mechanic.context.MechanicContext;
import me.espryth.trollgui.mechanic.fakeore.FakeOreMechanic;
import me.espryth.trollgui.mechanic.fakeore.FakeOreMechanicContextSnapshot;
import me.espryth.trollgui.mechanic.mob.FakeMobMechanic;
import me.espryth.trollgui.mechanic.mob.FakeMobMechanicContextSnapshot;
import me.espryth.trollgui.mechanic.rainbow.RainbowGroundMechanic;
import me.espryth.trollgui.mechanic.rainbow.RainbowGroundMechanicContext;
import me.espryth.trollgui.player.PlayerDataManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.incendo.interfaces.core.click.ClickHandler;
import org.incendo.interfaces.paper.element.ItemStackElement;
import org.incendo.interfaces.paper.pane.ChestPane;
import org.incendo.interfaces.paper.type.ChestInterface;
import org.jetbrains.annotations.NotNull;

public class TrollPlayerMenu {

  private final PlayerDataManager playerDataManager;
  private final MechanicManager mechanicManager;

  public TrollPlayerMenu(
      final @NotNull PlayerDataManager playerDataManager,
      final @NotNull MechanicManager mechanicManager
  ) {
    this.playerDataManager = playerDataManager;
    this.mechanicManager = mechanicManager;
  }

  public @NotNull ChestInterface create(
      final @NotNull Player target
  ) {
    final var playerData = playerDataManager.getPlayerData(target);
    return ChestInterface.builder()
        .title(Component.text("Troll Menu (" + target.getName() + ")"))
        .rows(1)
        .addTransform((pane, view) -> pane.element(
            ItemStackElementFactory.create(
                Material.WHITE_WOOL,
                "Rainbow Ground",
                "Times trolled: " + playerData.data(RainbowGroundMechanic.NAME).count(),
                ClickHandler.canceling(ctx -> {
                  final var mechanic = mechanicManager
                      .getMechanic(RainbowGroundMechanic.NAME, RainbowGroundMechanicContext.class);
                  mechanic.startHandling(new RainbowGroundMechanicContext(view.viewer().player(), Duration.ofSeconds(30)));
                  view.viewer().close();
                  view.viewer().player().sendPlainMessage(
                      "Start to troll " + target.getName() + " with Rainbow Ground!"
                  );
                })
            ),
            3, 0
        ))
        .addTransform((pane, view) -> pane.element(
            ItemStackElementFactory.create(
                Material.DIAMOND_ORE,
                "Fake Ore",
                "Times trolled: " + playerData.data(FakeOreMechanic.NAME).count(),
                ClickHandler.canceling(ctx -> {
                  final var snapshotManager = mechanicManager
                      .getSnapshotManager(FakeOreMechanic.NAME, FakeOreMechanicContextSnapshot.class);
                  snapshotManager.addSnapshot(
                      view.viewer().player(),
                      new FakeOreMechanicContextSnapshot(view.viewer().player())
                  );
                  view.viewer().close();
                  view.viewer().player().sendPlainMessage(
                      "Start to troll " + target.getName() + " with Fake Ore! " +
                      "Place an ore"
                  );
                })
            ),
            4, 0
        ))
        .addTransform((pane, view) -> pane.element(
            ItemStackElementFactory.create(
                Material.CREEPER_SPAWN_EGG,
                "Fake Mob",
                "Times trolled: " + playerData.data(FakeMobMechanic.NAME).count(),
                ClickHandler.canceling(ctx -> {
                  final var snapshotManager = mechanicManager
                      .getSnapshotManager(FakeMobMechanic.NAME, FakeMobMechanicContextSnapshot.class);
                  snapshotManager.addSnapshot(
                      view.viewer().player(),
                          new FakeMobMechanicContextSnapshot(view.viewer().player())
                      );
                  view.viewer().close();
                  view.viewer().player().sendPlainMessage(
                      "Start to troll " + target.getName() + " with Fake Mob! " +
                          "Use a mob spawn egg (Skeleton, Creeper, Zombie)"
                  );
                })
            ),
            5, 0
        ))
        .build();
  }

  @SuppressWarnings("unchecked")
  private <C extends MechanicContext> ItemStackElement<ChestPane> createElement(
      final @NotNull Material material,
      final @NotNull String displayName,
      final @NotNull String mechanicName,
      final @NotNull Supplier<C> contextSupplier
  ) {
    final var context = contextSupplier.get();
    return ItemStackElementFactory.create(
        material,
        displayName,
        "Times trolled: " + playerDataManager.getPlayerData(context.player()).data(mechanicName).count(),
        ClickHandler.canceling(ctx -> {
          final var mechanic = mechanicManager
              .getMechanic(mechanicName, (Class<C>) context.getClass());
          mechanic.startHandling(context);
        })
    );
  }

}
