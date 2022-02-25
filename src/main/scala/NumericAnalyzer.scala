import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

import scala.collection.mutable.ArrayBuffer

class NumericAnalyzer(strCSVFileContents: ArrayBuffer[Array[String]])
    extends LazyLogging {

  def process()
      : (ArrayBuffer[Double], ArrayBuffer[Double], ArrayBuffer[Double]) = {

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

  private def getColumnMax: ArrayBuffer[Double] = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    val columMax = new ArrayBuffer[Double]()

    for (inputRow <- strCSVFileContents) {
      columMax += (inputRow.toList.max).toDouble
    }

    stopWatch.stop()

    logger.info(
      "Numeric Analyzer (Max) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

    return columMax
  }

  private def getColumnMin: ArrayBuffer[Double] = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    val columMin = new ArrayBuffer[Double]()

    for (inputRow <- strCSVFileContents) {
      columMin += (inputRow.toList.min).toDouble
    }

    stopWatch.stop()

    logger.info(
      "Numeric Analyzer (Min) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

    return columMin
  }

  private def getColumnMean: ArrayBuffer[Double] = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    val columMean = new ArrayBuffer[Double]()

    for (inputRow <- strCSVFileContents) {
      columMean += (inputRow.toList
        .map(_.toDouble)
        .sum) / (inputRow.toList
        .map(_.toDouble)
        .length)
        .toDouble
    }

    stopWatch.stop()

    logger.info(
      "Numeric Analyzer (Mean) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

    return columMean
  }
}
