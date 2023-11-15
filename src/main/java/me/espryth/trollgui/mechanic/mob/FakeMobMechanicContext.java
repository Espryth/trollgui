package me.espryth.trollgui.mechanic.mob;

import me.espryth.trollgui.mechanic.context.MechanicContext;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public record FakeMobMechanicContext(
    Player player,
    Location location,
    MobType mobType
) implements MechanicContext {

}
