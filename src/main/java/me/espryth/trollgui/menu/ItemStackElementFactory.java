package me.espryth.trollgui.menu;

import java.util.List;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.incendo.interfaces.core.click.ClickContext;
import org.incendo.interfaces.core.click.ClickHandler;
import org.incendo.interfaces.paper.PlayerViewer;
import org.incendo.interfaces.paper.element.ItemStackElement;
import org.incendo.interfaces.paper.pane.ChestPane;
import org.jetbrains.annotations.NotNull;

public class ItemStackElementFactory {

  public static ItemStackElement<ChestPane> create(
      final @NotNull Material material,
      final @NotNull String displayName,
      final @NotNull String lore,
      final @NotNull ClickHandler<ChestPane, InventoryClickEvent, PlayerViewer,
          ClickContext<ChestPane, InventoryClickEvent, PlayerViewer>> clickHandler
  ) {
    final var item = new ItemStack(material);
    item.editMeta(meta -> {
      meta.displayName(Component.text(displayName));
      meta.lore(
          List.of(
              Component.empty(),
              Component.text(lore)
          )
      );
    });
    return ItemStackElement.of(
        item,
        clickHandler
    );
  }

}
