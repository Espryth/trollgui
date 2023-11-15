package me.espryth.trollgui.mechanic.context;

import java.util.UUID;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractMechanicContextSnapshot<C extends MechanicContext>
    implements MechanicContextSnapshot<C> {

  protected final UUID targetId;

  protected AbstractMechanicContextSnapshot(final @NotNull Player target) {
    this.targetId = target.getUniqueId();
  }

}
