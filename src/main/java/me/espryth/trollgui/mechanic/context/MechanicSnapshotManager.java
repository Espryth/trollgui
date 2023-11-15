package me.espryth.trollgui.mechanic.context;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.time.Duration;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MechanicSnapshotManager<C extends MechanicContext, S extends MechanicContextSnapshot<C>> {

  private final Cache<UUID, S> cache = CacheBuilder.newBuilder()
      .expireAfterWrite(Duration.ofSeconds(60))
      .build();

  public @Nullable S getSnapshot(final @NotNull Player player) {
    return cache.getIfPresent(player.getUniqueId());
  }

  public void addSnapshot(final @NotNull Player player, final @NotNull S snapshot) {
    cache.put(player.getUniqueId(), snapshot);
  }

  public @Nullable C finishSnapshot(final @NotNull Player player) {
    final var snapshot = getSnapshot(player);

    if (snapshot != null) {
      cache.invalidate(player.getUniqueId());
      return snapshot.finish();
    }

    return null;
  }

}
