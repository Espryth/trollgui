package me.espryth.trollgui.mechanic.fakeore;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import me.espryth.trollgui.mechanic.block.FakeBlockHandler;
import net.minecraft.core.BlockPos;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FakeOreRegistry {

  private final Map<UUID, Set<BlockPos>> fakeOres = new ConcurrentHashMap<>();
  private final FakeBlockHandler fakeBlockHandler;

  public FakeOreRegistry(FakeBlockHandler fakeBlockHandler) {
    this.fakeBlockHandler = fakeBlockHandler;
  }

  public void setFakeOre(
      final @NotNull Player player,
      final @NotNull Location location,
      final @NotNull OreType oreType
  ) {
    fakeBlockHandler.setFakeBlock(
        player,
        location.getBlock(),
        oreType.material()
    );

    fakeOres.computeIfAbsent(
        player.getUniqueId(),
        k -> ConcurrentHashMap.newKeySet()
    ).add(
        new BlockPos(
            location.getBlockX(),
            location.getBlockY(),
            location.getBlockZ()
        )
    );
  }


  public boolean removeFakeOre(
      final @NotNull Player player,
      final @NotNull BlockPos pos
  ) {
    return fakeOres.computeIfPresent(
        player.getUniqueId(),
        (k, v) -> {
          fakeBlockHandler.updateBlock(player, pos);
          return v.remove(pos) ? v : null;
        }
    ) != null;
  }

  public void removeAll(final @NotNull Player player) {
    final var ores = fakeOres.remove(player.getUniqueId());
    if (ores != null) {
      ores.forEach(pos -> fakeBlockHandler.updateBlock(player, pos));
    }
  }

}
