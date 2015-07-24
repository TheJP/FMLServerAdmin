package ch.thejp.mod.serveradmin

import org.apache.logging.log4j.LogManager

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.FMLInitializationEvent

import ch.thejp.mod.serveradmin.JPServerAdmin._

@Mod(modid = ModId, name = ModName, version = Version,
  modLanguage = "scala", useMetadata = true)
object JPServerAdmin {

  final val ModId = "JPServerAdmin"
  final val ModName = "JPServerAdmin"
  final val Version = "@VERSION@"

  val log = LogManager.getLogger(ModName)

  @EventHandler
  def init(e: FMLInitializationEvent){
    //Register join/leave events
    FMLCommonHandler.instance().bus().register(PlayerHandler)
    FMLCommonHandler.instance().bus().register(TickHandler)
    log.info("Done with init")
  }
}