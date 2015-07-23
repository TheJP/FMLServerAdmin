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

@Mod(modid = ModId, name = ModName, version = Version,
  modLanguage = "scala", useMetadata = true)
object JPServerAdmin {

  final val ModId = "JPServerAdmin"
  final val ModName = "JPServerAdmin"
  final val Version = "@VERSION@"

  var log = LogManager.getLogger(ModName)

  @EventHandler
  def init(e: FMLInitializationEvent){
    log.info("Done with init")
  }

  /**
   * Stores every online player into a file.
   */
  def updatePlayerList = {
    log.info("update") //TODO: remove
    val players = MinecraftServer.getServer.worldServers flatMap
      { _.playerEntities.toArray map { _.asInstanceOf[EntityPlayer] } }
    log.info(players map { _.getDisplayName } mkString { ", " })
  }

  @EventHandler
  def join(e: PlayerLoggedInEvent) = updatePlayerList

  @EventHandler
  def leave(e: PlayerLoggedOutEvent) = updatePlayerList
}