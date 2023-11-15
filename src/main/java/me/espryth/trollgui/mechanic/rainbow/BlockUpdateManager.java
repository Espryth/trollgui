package me.espryth.trollgui.mechanic.rainbow;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import me.espryth.trollgui.mechanic.block.FakeBlockHandler;
import net.minecraft.core.BlockPos;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class BlockUpdateManager {

  private static final int DEFAULT_TICKS = 20;
  private final Map<UUID, Set<Context>> playerContexts = new ConcurrentHashMap<>();

  public BlockUpdateManager(
      final @NotNull Plugin plugin,
      final @NotNull FakeBlockHandler fakeBlockHandler
  ) {
    Bukkit.getScheduler().runTaskTimer(plugin, () -> {
      playerContexts.forEach((uuid, contexts) -> {
        contexts.forEach(context -> {
          if (context.remainingTicks-- <= 0) {
            final var player = Bukkit.getPlayer(uuid);
            if(player != null) {
              fakeBlockHandler.updateBlock(player, context.blockPos);
            }
            contexts.remove(context);
          }
        });
      });
    }, 0, 1);
  }

  public void add(
      final @NotNull Player player,
      final @NotNull Block block
  ) {

    final var contexts = playerContexts.computeIfAbsent(
        player.getUniqueId(),
        uuid -> ConcurrentHashMap.newKeySet()
    );

    final var blockPos = new BlockPos(
        block.getX(),
        block.getY(),
        block.getZ()
    );

    // remove old context if exists
    contexts.remove(new Context(blockPos));
    contexts.add(new Context(blockPos, DEFAULT_TICKS));
  }

  private static class Context {
    private final BlockPos blockPos;
    private int remainingTicks;

    public Context(
        final @NotNull BlockPos blockPos,
        final int remainingTicks
    ) {
      this.blockPos = blockPos;
      this.remainingTicks = remainingTicks;
    }

    public Context(
        final @NotNull BlockPos blockPos
    ) {
      this(blockPos, -1);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      final var context = (Context) o;
      return Objects.equals(blockPos, context.blockPos);
    }

    @Override
    public int hashCode() {
      return Objects.hash(blockPos);
    }
  }

}
