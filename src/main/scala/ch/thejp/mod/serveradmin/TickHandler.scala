package ch.thejp.mod.serveradmin

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.TickEvent.Phase
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent
import java.io.File

object TickHandler {

  val reactions = Map(
    "shutdown.cmd" -> { () => FMLCommonHandler.instance().exitJava(0, false); }
  ) map { case (file, command) => (new File(file), command) }

  /**
   * Interval (in ticks) in which the files should be checked.
   */
  val CheckInterval = 20

  private var tickCount: Int = 0

  /**
   * Checks for marker files and reacts to them.
   */
  def check =
    reactions filter { _._1.isFile } foreach { case (file, command) =>
      file.delete
      command()
    }

  @SubscribeEvent
  def tick(e: ServerTickEvent) = {
    if(e.phase == Phase.END){
      tickCount += 1
      if(tickCount % CheckInterval == 0){ check }
    }
  }

}