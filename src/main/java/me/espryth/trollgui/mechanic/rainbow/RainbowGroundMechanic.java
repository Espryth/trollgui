package me.espryth.trollgui.mechanic.rainbow;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import me.espryth.trollgui.mechanic.block.FakeBlockHandler;
import me.espryth.trollgui.mechanic.context.Mechanic;
import me.espryth.trollgui.player.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class RainbowGroundMechanic
    implements Mechanic<RainbowGroundMechanicContext, PlayerMoveEvent> {

  public static final String NAME = "rainbow-ground";

  private final Map<UUID, RainbowGroundMechanicContext> cachedContexts = new ConcurrentHashMap<>();
  private final FakeBlockHandler fakeBlockHandler;
  private final BlockUpdateManager blockUpdateManager;
  private final PlayerDataManager playerDataManager;

  public RainbowGroundMechanic(
      final @NotNull Plugin plugin,
      final @NotNull FakeBlockHandler fakeBlockHandler,
      final @NotNull BlockUpdateManager blockUpdateManager,
      final @NotNull PlayerDataManager playerDataManager
  ) {
    this.fakeBlockHandler = fakeBlockHandler;
    this.blockUpdateManager = blockUpdateManager;
    this.playerDataManager = playerDataManager;

    Bukkit.getScheduler().runTaskTimerAsynchronously(
        plugin,
        () -> cachedContexts.forEach((uuid, context) -> {
          if (context.tick()) {
            cachedContexts.remove(uuid);
          }
        }),
        0, 1
    );
  }

  @Override
  public void startHandling(final @NotNull RainbowGroundMechanicContext context) {

    final var playerId = context.player().getUniqueId();

    cachedContexts.remove(playerId);

    final var playerData = playerDataManager
        .getPlayerData(context.player());
    playerData.data(name()).increment();

    cachedContexts.put(playerId, context);
  }

  @Override
  public void stopHandling(@NotNull RainbowGroundMechanicContext context) {
    cachedContexts.remove(context.player().getUniqueId());
  }

  @Override
  public void handle(final @NotNull PlayerMoveEvent event) {
    if (!event.hasChangedBlock()) {
      return;
    }

    final var player = event.getPlayer();
    if (cachedContexts.containsKey(player.getUniqueId())) {

      final var block = event.getTo()
          .clone()
          .subtract(0, 1, 0)
          .getBlock();

      if (block.getType() == Material.AIR) {
        return;
      }

      fakeBlockHandler.setFakeBlock(
          player,
          block,
          RainbowMaterialManager.getRandom()
      );
      blockUpdateManager.add(player, block);
    }
  }

  @Override
  public @NotNull String name() {
    return NAME;
  }

  @Override
  public @NotNull Class<PlayerMoveEvent> eventType() {
    return PlayerMoveEvent.class;
  }
}
