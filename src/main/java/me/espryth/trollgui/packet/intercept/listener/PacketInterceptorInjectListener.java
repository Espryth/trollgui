package me.espryth.trollgui.packet.intercept.listener;

import me.espryth.trollgui.packet.intercept.PacketInterceptorHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PacketInterceptorInjectListener
    implements Listener {

  @EventHandler
  public void handle(PlayerJoinEvent event) {
    PacketInterceptorHandler.injectPacketHandler(event.getPlayer());
  }

}
