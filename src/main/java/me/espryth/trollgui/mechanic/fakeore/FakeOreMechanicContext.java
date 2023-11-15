package me.espryth.trollgui.mechanic.fakeore;

import me.espryth.trollgui.mechanic.context.MechanicContext;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public record FakeOreMechanicContext(
    Player player,
    Location location,
    OreType oreType
) implements MechanicContext {}
