package ch.thejp.mod.serveradmin

import cpw.mods.fml.common.event.FMLInitializationEvent
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.Mod.EventHandler
import ch.thejp.mod.serveradmin.JPServerAdmin._
import org.apache.logging.log4j.LogManager

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
}