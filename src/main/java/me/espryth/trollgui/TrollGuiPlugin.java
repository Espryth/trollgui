package me.espryth.trollgui;

import java.nio.file.Path;
import java.util.concurrent.Executors;
import me.espryth.trollgui.command.TrollCommand;
import me.espryth.trollgui.mechanic.MechanicManager;
import me.espryth.trollgui.mechanic.block.FakeBlockHandler;
import me.espryth.trollgui.mechanic.fakeore.FakeOreMechanic;
import me.espryth.trollgui.mechanic.fakeore.FakeOreRegistry;
import me.espryth.trollgui.mechanic.fakeore.event.FakeOreBreakEventDispatcher;
import me.espryth.trollgui.mechanic.fakeore.listener.FakeOreMechanicSnapshotListener;
import me.espryth.trollgui.mechanic.fakeore.listener.RemoveFakeBlocksListener;
import me.espryth.trollgui.mechanic.mob.FakeMobHandler;
import me.espryth.trollgui.mechanic.mob.FakeMobMechanic;
import me.espryth.trollgui.mechanic.mob.event.FakeMobHitEventDispatcher;
import me.espryth.trollgui.mechanic.mob.listener.DespawnEntitiesListener;
import me.espryth.trollgui.mechanic.mob.listener.FakeMobMechanicSnapshotListener;
import me.espryth.trollgui.mechanic.rainbow.BlockUpdateManager;
import me.espryth.trollgui.mechanic.rainbow.RainbowGroundMechanic;
import me.espryth.trollgui.menu.TrollPlayerMenu;
import me.espryth.trollgui.packet.intercept.PacketInterceptorHandler;
import me.espryth.trollgui.packet.intercept.listener.PacketInterceptorInjectListener;
import me.espryth.trollgui.player.PlayerData;
import me.espryth.trollgui.player.PlayerDataManager;
import me.espryth.trollgui.player.listener.PlayerDataLoadListeners;
import me.espryth.trollgui.storage.dist.FileStorage;
import net.minecraft.network.protocol.game.ServerboundInteractPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.interfaces.paper.PaperInterfaceListeners;

public class TrollGuiPlugin extends JavaPlugin {

  @Override
  public void onEnable() {

    final var dataFolder = getDataFolder();
    final var playerDataManager = new PlayerDataManager(
        new FileStorage<>(
            this,
            PlayerData.CODEC,
            Path.of(dataFolder.getPath(), "player-data").toFile()
        ),
        Executors.newSingleThreadExecutor()
    );

    final var mechanicManager = new MechanicManager(this);
    final var fakeBlockHandler = new FakeBlockHandler();
    final var fakeOreRegistry = new FakeOreRegistry(fakeBlockHandler);
    final var fakeMobHandler = new FakeMobHandler();
    final var blockUpdateManager = new BlockUpdateManager(this, fakeBlockHandler);

    final var trollMenu = new TrollPlayerMenu(playerDataManager, mechanicManager);

    mechanicManager.registerMechanic(new FakeOreMechanic(fakeOreRegistry, playerDataManager));
    mechanicManager.registerMechanic(new FakeMobMechanic(fakeMobHandler, playerDataManager));
    mechanicManager.registerMechanic(new RainbowGroundMechanic(this, fakeBlockHandler, blockUpdateManager, playerDataManager));

    PacketInterceptorHandler.registerPacketInterceptor(
        ServerboundPlayerActionPacket.class,
        new FakeOreBreakEventDispatcher(fakeOreRegistry)
    );
    PacketInterceptorHandler.registerPacketInterceptor(
        ServerboundInteractPacket.class,
        new FakeMobHitEventDispatcher(fakeMobHandler)
    );

    registerListeners(
        new PacketInterceptorInjectListener(),
        new PlayerDataLoadListeners(playerDataManager),
        new FakeOreMechanicSnapshotListener(this, mechanicManager),
        new RemoveFakeBlocksListener(fakeOreRegistry),
        new FakeMobMechanicSnapshotListener(mechanicManager),
        new DespawnEntitiesListener(fakeMobHandler),
        new PaperInterfaceListeners(this)
    );

    Bukkit.getCommandMap().register(
        "trollgui",
        new TrollCommand(trollMenu)
    );

  }

  private void registerListeners(final Listener... listeners) {
    for (final Listener listener : listeners) {
      getServer().getPluginManager().registerEvents(listener, this);
    }
  }
}
