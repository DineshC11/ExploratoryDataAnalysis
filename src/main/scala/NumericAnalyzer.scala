import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

import scala.collection.mutable.ArrayBuffer

class NumericAnalyzer(strCSVFileContents: ArrayBuffer[Array[String]])
    extends LazyLogging {

  def process(): Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    getColumnMin
    getColumnMax
    getColumnMean

    stopWatch.stop()

    logger.info(
      "Numeric Analyzer Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }

  private def getColumnMax: Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    for (inputRow <- strCSVFileContents) {
      println("The Maximum is : " + inputRow.toList.max)
    }

    stopWatch.stop()

    logger.info(
      "Numeric Analyzer (Max) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }

  private def getColumnMin: Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    for (inputRow <- strCSVFileContents) {
      println("The Minimum is : " + inputRow.toList.min)

    }

    stopWatch.stop()

    logger.info(
      "Numeric Analyzer (Min) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }

  private def getColumnMean: Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    for (inputRow <- strCSVFileContents) {
      println(
        "The Mean    is : " + (inputRow.toList
          .map(_.toDouble)
          .sum) / (inputRow.toList
          .map(_.toDouble)
          .length)
      )
    }

    stopWatch.stop()

    logger.info(
      "Numeric Analyzer (Mean) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }
}
