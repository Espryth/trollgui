package me.espryth.trollgui.mechanic.context;

import org.jetbrains.annotations.Nullable;

public interface MechanicContextSnapshot<C extends MechanicContext> {

  @Nullable C finish();

}
