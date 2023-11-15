package me.espryth.trollgui.storage.dist;

import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;
import me.espryth.trollgui.storage.Model;
import me.espryth.trollgui.storage.Storage;
import me.espryth.trollgui.storage.codec.Codec;
import me.espryth.trollgui.util.GsonProvider;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FileStorage<M extends Model>
    implements Storage<M> {

  private final Plugin plugin;
  private final Codec<M> codec;
  private final File folder;

  public FileStorage(
      final @NotNull Plugin plugin,
      final @NotNull Codec<M> codec,
      final @NotNull File folder
  ) {
    this.plugin = plugin;
    this.codec = codec;
    this.folder = folder;

    if (!folder.exists()) {
      folder.mkdirs();
    }

  }

  @Override
  public @Nullable M find(final @NotNull UUID id) {
    final var file = new File(folder, id + ".json");
    if (file.exists()) {
      try (final var reader = new FileReader(file)) {
        final var jsonObject = GsonProvider.INSTANCE
            .fromJson(reader, JsonObject.class);
        return jsonObject == null ? null : codec.decode(jsonObject);
      } catch (IOException e) {
        plugin.getSLF4JLogger().error(
            "An error occurred while trying to read the file {}",
            file.getName(),
            e
        );
      }
    }
    return null;
  }

  @Override
  public void save(@NotNull M model) {
    final var file = new File(folder, model.id() + ".json");

    try {
      if (!file.exists()) {
        file.createNewFile();
      }
    } catch (IOException e) {
      plugin.getSLF4JLogger().error(
          "An error occurred while trying to create the file {}",
          file.getName(),
          e
      );
      return;
    }

    try (final var writer = new FileWriter(file)) {
      final var jsonObject = codec.encode(model);
      GsonProvider.INSTANCE.toJson(jsonObject, writer);
    } catch (IOException e) {
      plugin.getSLF4JLogger().error(
          "An error occurred while trying to write the file {}",
          file.getName(),
          e
      );
    }
  }

  @Override
  public void delete(@NotNull M model) {
    final var file = new File(folder, model.id() + ".json");
    if (file.exists()) {
      file.delete();
    }
  }

}
