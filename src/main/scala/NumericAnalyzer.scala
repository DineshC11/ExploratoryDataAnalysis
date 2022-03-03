import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

import scala.collection.mutable.ArrayBuffer

class NumericAnalyzer(strCSVFileContents: ArrayBuffer[Array[String]])
    extends LazyLogging {

  /** #1 Func : Analyze the numeric file possibilities.
    *
    * @param : File contents as array buffer of strings
    * @return : The numeric columns have been identified in the previous class
    * itself. So, the arithmetic operations are being performed over here for each and every
    * column whichever was identified as numeric and the output is returned to main class.
    */

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

  /** #2 Func : To find out the maximum number in each row.
    *
    * @param : ArrayBuffer of numeric columns
    * @return : To find out the maximum value in each numeric column. And store the data
    * in a separate container. So as to readily available to process final call.
    */

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

  /** #3 Func : To find out the minimum number in each row.
    *
    * @param : ArrayBuffer of numeric columns
    * @return : To find out the minimum value in each numeric column. And store the data
    * in a separate container. So as to readily available to process final call.
    */

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

  /** #4 Func : To find out the mean number in each row.
    *
    * @param : ArrayBuffer of numeric columns
    * @return : To find out the mean value in each numeric column. And store the data
    * in a separate container. So as to readily available to process final call.
    */

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
