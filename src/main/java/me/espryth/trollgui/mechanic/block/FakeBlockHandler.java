package me.espryth.trollgui.mechanic.block;

import me.espryth.trollgui.packet.Packets;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_19_R3.block.data.CraftBlockData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FakeBlockHandler {

  public void setFakeBlock(
      final @NotNull Player viewer,
      final @NotNull Block block,
      final @NotNull Material replacement
  ) {

    final var pos = new BlockPos(
        block.getX(),
        block.getY(),
        block.getZ()
    );

    Packets.send(
        viewer,
        new ClientboundBlockUpdatePacket(
            pos,
            ((CraftBlockData) replacement
                .createBlockData())
                .getState()
        )
    );
  }

  public @NotNull Block updateBlock(
      final @NotNull Player viewer,
      final @NotNull BlockPos pos
  ) {

    final var realBlock = viewer.getWorld()
        .getBlockAt(
            pos.getX(),
            pos.getY(),
            pos.getZ()
        );

    Packets.send(
        viewer,
        new ClientboundBlockUpdatePacket(
            pos,
            ((CraftBlockState) realBlock
                .getState())
                .getHandle()
        )
    );

    return realBlock;
  }

}
