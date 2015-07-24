package ch.thejp.mod.serveradmin

import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import ch.thejp.mod.serveradmin.JPServerAdmin._
import org.apache.logging.log4j.LogManager
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent
import net.minecraft.server.MinecraftServer
import net.minecraft.entity.player.EntityPlayer
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.FMLCommonHandler
import java.io.PrintStream
import java.io.FileOutputStream

@Mod(modid = ModId, name = ModName, version = Version,
  modLanguage = "scala", useMetadata = true)
object JPServerAdmin {

  final val ModId = "JPServerAdmin"
  final val ModName = "JPServerAdmin"
  final val Version = "@VERSION@"

  val log = LogManager.getLogger(ModName)

  val PlayersFileName = "players.txt"

  @EventHandler
  def init(e: FMLInitializationEvent){
    //Register join/leave events
    FMLCommonHandler.instance().bus().register(this)
    log.info("Done with init")
  }

  /**
   * Stores every online player into a file.
   */
  def updatePlayerList = {
    log.info("update") //TODO: remove
    //Get player names
    val players = MinecraftServer.getServer.worldServers flatMap
      { _.playerEntities.toArray map { _.asInstanceOf[EntityPlayer].getDisplayName } }
    log.info(players mkString { ", " })
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