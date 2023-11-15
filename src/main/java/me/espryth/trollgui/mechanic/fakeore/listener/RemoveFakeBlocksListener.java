package me.espryth.trollgui.mechanic.fakeore.listener;

import me.espryth.trollgui.mechanic.fakeore.FakeOreRegistry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class RemoveFakeBlocksListener implements Listener {

  private final FakeOreRegistry fakeOreRegistry;

  public RemoveFakeBlocksListener(FakeOreRegistry fakeOreRegistry) {
    this.fakeOreRegistry = fakeOreRegistry;
  }

  @EventHandler
  public void onQuit(final PlayerQuitEvent event) {
    fakeOreRegistry.removeAll(event.getPlayer());
  }

  @EventHandler
  public void onChangeWorld(final PlayerChangedWorldEvent event) {
    fakeOreRegistry.removeAll(event.getPlayer());
  }

}
