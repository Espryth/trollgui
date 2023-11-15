package me.espryth.trollgui.player.listener;

import me.espryth.trollgui.player.PlayerDataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerDataLoadListeners implements Listener {

  private final PlayerDataManager playerDataManager;

  public PlayerDataLoadListeners(final @NotNull PlayerDataManager playerDataManager) {
    this.playerDataManager = playerDataManager;
  }

  @EventHandler
  public void onJoin(final PlayerJoinEvent event) {
    playerDataManager.loadData(event.getPlayer());
  }

  @EventHandler
  public void onQuit(final PlayerQuitEvent event) {
    playerDataManager.uploadData(event.getPlayer());
  }

}
