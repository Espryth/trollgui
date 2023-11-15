package me.espryth.trollgui.player;

import com.google.gson.JsonObject;
import me.espryth.trollgui.storage.codec.Codec;
import org.jetbrains.annotations.NotNull;

public class CountData {

  public static final Codec<CountData> CODEC = new CountDataCodec();

  private final String mechanic;
  private int count;

  public CountData(
      final @NotNull String mechanic,
      final int count
  ) {
    this.mechanic = mechanic;
    this.count = count;
  }

  public String mechanic() {
    return mechanic;
  }

  public int count() {
    return count;
  }

  public void increment() {
    count++;
  }

  private static class CountDataCodec implements Codec<CountData> {

    @Override
    public @NotNull JsonObject encode(final @NotNull CountData model) {
      final var object = new JsonObject();
      object.addProperty("mechanic", model.mechanic());
      object.addProperty("count", model.count());
      return object;
    }

    @Override
    public @NotNull CountData decode(final @NotNull JsonObject object) {
      return new CountData(
          object.get("mechanic").getAsString(),
          object.get("count").getAsInt()
      );
    }
  }

}
