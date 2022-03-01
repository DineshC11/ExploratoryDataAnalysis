import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

import scala.collection.mutable.ArrayBuffer

class NumericAnalyzer(strCSVFileContents: ArrayBuffer[Array[String]])
    extends LazyLogging {

  def process()
      : (ArrayBuffer[String], ArrayBuffer[String], ArrayBuffer[String]) = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    val colMinVal = getColumnMin
    val colMaxVal = getColumnMax
    val colMeanVal = getColumnMean

    stopWatch.stop()

    logger.info(
      "Numeric Analyzer Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

    (colMinVal, colMaxVal, colMeanVal)
  }

  private def getColumnMax: ArrayBuffer[String] = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    val columMax = new ArrayBuffer[String]()

    for (inputRow <- strCSVFileContents) {
      columMax += "Max , " + (inputRow.toList.max)
    }

    stopWatch.stop()

    logger.info(
      "Numeric Analyzer (Max) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

    return columMax
  }

  private def getColumnMin: ArrayBuffer[String] = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    val columMin = new ArrayBuffer[String]()

    for (inputRow <- strCSVFileContents) {
      columMin += "Min , " + (inputRow.toList.min)
    }

    stopWatch.stop()

    logger.info(
      "Numeric Analyzer (Min) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

    return columMin
  }

  private def getColumnMean: ArrayBuffer[String] = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    val columMean = new ArrayBuffer[String]()

    for (inputRow <- strCSVFileContents) {
      columMean += "Mean , " + (inputRow.toList
        .map(_.toDouble)
        .sum) / (inputRow.toList
        .map(_.toDouble)
        .length)

    }

    stopWatch.stop()

    logger.info(
      "Numeric Analyzer (Mean) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

    return columMean
  }
}
