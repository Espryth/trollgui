package me.espryth.trollgui.command;

import me.espryth.trollgui.menu.TrollPlayerMenu;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.incendo.interfaces.paper.PlayerViewer;
import org.jetbrains.annotations.NotNull;

public class TrollCommand extends Command {

  private final TrollPlayerMenu trollPlayerMenu;

  public TrollCommand(TrollPlayerMenu trollPlayerMenu) {
    super("troll");
    this.trollPlayerMenu = trollPlayerMenu;
  }

  @Override
  public boolean execute(
      final @NotNull CommandSender sender,
      final @NotNull String label,
      final @NotNull String[] args
  ) {

    if (!(sender instanceof Player player)) {
      sender.sendPlainMessage("Only players can execute this command.");
      return true;
    }

    if (args.length < 1) {
      sender.sendPlainMessage("Usage: /troll <player>");
      return true;
    }

    final var target = Bukkit.getPlayer(args[0]);

    if (target == null) {
      sender.sendPlainMessage("Player not found.");
      return true;
    }

    trollPlayerMenu.create(target)
        .open(PlayerViewer.of(player));
    return true;
  }
}
