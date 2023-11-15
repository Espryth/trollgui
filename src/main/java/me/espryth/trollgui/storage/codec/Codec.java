package me.espryth.trollgui.storage.codec;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;

public interface Codec<M> {

  @NotNull JsonObject encode(
      final @NotNull M model
  );

  @NotNull M decode(
      final @NotNull JsonObject object
  );

}
