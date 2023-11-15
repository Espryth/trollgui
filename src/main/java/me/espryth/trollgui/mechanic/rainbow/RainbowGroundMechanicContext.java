package me.espryth.trollgui.mechanic.rainbow;

import java.time.Duration;
import me.espryth.trollgui.mechanic.context.MechanicContext;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RainbowGroundMechanicContext implements MechanicContext {

  private final Player player;
  private int ticksLeft;

  public RainbowGroundMechanicContext(
      final Player player,
      final Duration duration
  ) {
    this.player = player;
    this.ticksLeft = (int) (duration.toMillis() / 50);
  }

  @Override
  public @NotNull Player player() {
    return player;
  }

  public boolean tick() {
    return ticksLeft-- <= 0;
  }
}
