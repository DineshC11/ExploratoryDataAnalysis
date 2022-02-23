import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

trait Analyzer extends LazyLogging {

  val stopWatch = new StopWatch()
  stopWatch.start()

  def checkColumnType(): Any
  def getColumnSize(): Any

  stopWatch.stop()

  logger.info(
    "Trait Time Taken To Complete" + stopWatch
      .getTime() + " ms "
  )
}
