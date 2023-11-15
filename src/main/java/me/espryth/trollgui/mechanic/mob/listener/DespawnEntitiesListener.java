package me.espryth.trollgui.mechanic.mob.listener;

import me.espryth.trollgui.mechanic.mob.FakeMobHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DespawnEntitiesListener implements Listener {

  private final FakeMobHandler fakeMobHandler;

  public DespawnEntitiesListener(FakeMobHandler fakeMobHandler) {
    this.fakeMobHandler = fakeMobHandler;
  }

  @EventHandler
  public void onQuit(final PlayerQuitEvent event) {
    fakeMobHandler.despawnAll(event.getPlayer());
  }

  @EventHandler
  public void onChangeWorld(final PlayerChangedWorldEvent event) {
    fakeMobHandler.despawnAll(event.getPlayer());
  }

}
