package me.espryth.trollgui.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.espryth.trollgui.storage.Model;
import me.espryth.trollgui.storage.codec.Codec;
import org.jetbrains.annotations.NotNull;

public class PlayerData implements Model {

  public static final Codec<PlayerData> CODEC = new PlayerDataCodec();

  private final UUID id;
  private final Map<String, CountData> data;

  public PlayerData(
      final @NotNull UUID id,
      final @NotNull Map<String, CountData> data
  ) {
    this.id = id;
    this.data = data;
  }

  public PlayerData(
      final @NotNull UUID id
  ) {
    this(id, new HashMap<>());
  }

  @Override
  public @NotNull UUID id() {
    return id;
  }

  public Map<String, CountData> data() {
    return data;
  }

  public @NotNull CountData data(final @NotNull String mechanic) {
    return data.computeIfAbsent(mechanic, key -> new CountData(mechanic, 0));
  }

  private static class PlayerDataCodec implements Codec<PlayerData> {

    @Override
    public @NotNull JsonObject encode(final @NotNull PlayerData model) {
      final var object = new JsonObject();
      object.addProperty("id", model.id().toString());

      final var array = new JsonArray();
      for (final var countData : model.data.values()) {
        array.add(CountData.CODEC.encode(countData));
      }

      object.add("countData", array);
      return object;
    }

    @Override
    public @NotNull PlayerData decode(@NotNull JsonObject object) {

      final var countData = new HashMap<String, CountData>();

      for (final var element : object.get("countData").getAsJsonArray()) {
        final var data = CountData.CODEC.decode(element.getAsJsonObject());
        countData.put(data.mechanic(), data);
      }

      return new PlayerData(
          UUID.fromString(object.get("id").getAsString()),
          countData
      );
    }
  }
}
