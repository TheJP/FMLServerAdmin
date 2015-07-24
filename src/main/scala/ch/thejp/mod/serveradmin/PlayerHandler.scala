package ch.thejp.mod.serveradmin

import java.io.FileOutputStream
import java.io.PrintStream

import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.server.MinecraftServer

object PlayerHandler {

  val PlayersFileName = "players.txt"

  /**
   * Stores every online player into a file.
   */
  def updatePlayerList = {
    JPServerAdmin.log.info("update") //TODO: remove
    //Get player names
    val players = MinecraftServer.getServer.worldServers flatMap
      { _.playerEntities.toArray map { _.asInstanceOf[EntityPlayer].getDisplayName } }
    JPServerAdmin.log.info(players mkString { ", " })
    //Save to the file
    var writer: PrintStream = null
    try {
      writer = new PrintStream(new FileOutputStream(PlayersFileName, false))
      players foreach { p => writer.println(p) }
    } finally { if(writer != null){ writer.close } } //(Scala does not support a "resource-try")
  }

  @SubscribeEvent
  def join(e: PlayerLoggedInEvent) = updatePlayerList

  @SubscribeEvent
  def leave(e: PlayerLoggedOutEvent) = updatePlayerList

}