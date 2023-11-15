package me.espryth.trollgui.mechanic.fakeore;

import java.util.UUID;
import me.espryth.trollgui.mechanic.context.AbstractMechanicContextSnapshot;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FakeOreMechanicContextSnapshot extends AbstractMechanicContextSnapshot<FakeOreMechanicContext> {

  private Block block;

  public FakeOreMechanicContextSnapshot(final @NotNull Player target) {
    super(target);
  }

  public @NotNull UUID targetId() {
    return targetId;
  }

  public @NotNull Block block() {
    return null;
  }

  public void block(final @NotNull Block block) {
    this.block = block;
  }


  @Override
  public @Nullable FakeOreMechanicContext finish() {

    final var player = Bukkit.getPlayer(targetId);

    if(player == null) {
      return null;
    }

    final var oreType = OreType.fromMaterial(block.getType());

    if(oreType == null) {
      return null;
    }

    return new FakeOreMechanicContext(
        player,
        block.getLocation(),
        oreType
    );
  }
}
