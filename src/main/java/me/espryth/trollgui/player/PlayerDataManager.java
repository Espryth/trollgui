package me.espryth.trollgui.player;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import me.espryth.trollgui.storage.Storage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerDataManager {

  private final Map<UUID, PlayerData> playerData = new ConcurrentHashMap<>();
  private final Storage<PlayerData> storage;
  private final Executor executor;

  public PlayerDataManager(Storage<PlayerData> storage, Executor executor) {
    this.storage = storage;
    this.executor = executor;
  }

  public @NotNull PlayerData getPlayerData(
      final @NotNull Player player
  ) {
    return Objects.requireNonNull(
        playerData.get(player.getUniqueId()),
        "Player data of " + player.getName() + " is null"
    );
  }

  public void loadData(
      final @NotNull Player player
  ) {
    executor.execute(() -> {
      var model = storage.find(player.getUniqueId());
      if (model == null) {
        model = new PlayerData(player.getUniqueId());
        storage.save(model);
      }
      playerData.put(player.getUniqueId(), model);
    });
  }

  public void saveData(
      final @NotNull Player player
  ) {
    executor.execute(() -> {
      final var model = playerData.get(player.getUniqueId());
      if (model != null) {
        storage.save(model);
      }
    });
  }

  public void uploadData(
      final @NotNull Player player
  ) {
    executor.execute(() -> {
      final var model = playerData.remove(player.getUniqueId());
      if (model != null) {
        storage.save(model);
      }
    });
  }
}
