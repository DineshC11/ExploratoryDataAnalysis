import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

class NumericAnalyzer(strCSVFileContents: String) extends LazyLogging {

  def process(): Any = {

    println("\t\t\t Numeric Process Method Call \n")

    val stopWatch = new StopWatch()
    stopWatch.start()

    getColumnMin
    getColumnMax
    getColumnMean

    stopWatch.stop()

    logger.info(
      "Numeric Analyzer Time Taken To Complete" + stopWatch
        .getTime() + " ms "
    )
  }

  private def getColumnMax: Any = {
    println("\t\t\t\t Get Column Max Method Call")

    val stopWatch = new StopWatch()
    stopWatch.start()

    stopWatch.stop()

    logger.info(
      "Numeric Analyzer (Max) Time Taken To Complete" + stopWatch
        .getTime() + " ms "
    )
  }

  private def getColumnMin: Any = {
    println("\t\t\t\t Get Column Min Method Call")

    val stopWatch = new StopWatch()
    stopWatch.start()

    stopWatch.stop()

    logger.info(
      "Numeric Analyzer (Min) Time Taken To Complete" + stopWatch
        .getTime() + " ms "
    )
  }

  private def getColumnMean: Any = {
    println("\t\t\t\t Get Column Mean Method Call \n")

    val stopWatch = new StopWatch()
    stopWatch.start()

    stopWatch.stop()

    logger.info(
      "Numeric Analyzer (Mean) Time Taken To Complete" + stopWatch
        .getTime() + " ms "
    )
  }
}
