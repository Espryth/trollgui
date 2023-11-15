package me.espryth.trollgui.storage;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Storage<M extends Model> {

  @Nullable M find(final @NotNull UUID id);

  void save(final @NotNull M model);

  void delete(final @NotNull M model);

}
