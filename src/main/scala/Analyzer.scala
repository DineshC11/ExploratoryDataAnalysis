import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

import scala.collection.mutable.ArrayBuffer

trait Analyzer extends LazyLogging {

  val stopWatch = new StopWatch()
  stopWatch.start()

  def checkColumnType(columnInput: ArrayBuffer[Array[String]]): Any

  def getColumnSize(columnInput: ArrayBuffer[Array[String]]): Any

  stopWatch.stop()

  logger.info(
    "Trait Time Taken To Complete : " + stopWatch
      .getTime() + " ms "
  )
}
