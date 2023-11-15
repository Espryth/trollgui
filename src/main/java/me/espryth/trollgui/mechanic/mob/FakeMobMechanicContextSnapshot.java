package me.espryth.trollgui.mechanic.mob;

import me.espryth.trollgui.mechanic.context.AbstractMechanicContextSnapshot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class FakeMobMechanicContextSnapshot
    extends AbstractMechanicContextSnapshot<FakeMobMechanicContext> {

  private Location location;
  private MobType mobType;

  public FakeMobMechanicContextSnapshot(Player player) {
    super(player);
  }

  public FakeMobMechanicContextSnapshot location(Location location) {
    this.location = location;
    return this;
  }

  public FakeMobMechanicContextSnapshot mobType(MobType mobType) {
    this.mobType = mobType;
    return this;
  }

  @Override
  public @Nullable FakeMobMechanicContext finish() {

    final var player = Bukkit.getPlayer(targetId);

    if (player == null) {
      return null;
    }

    if (location == null || mobType == null) {
      return null;
    }

    return new FakeMobMechanicContext(
        player,
        location,
        mobType
    );
  }
}
