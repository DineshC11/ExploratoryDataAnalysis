import scala.collection.mutable.ArrayBuffer
import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

class StringAnalyzer(strCSVFileContents: ArrayBuffer[Array[String]])
    extends LazyLogging {

  def process(): (
      ArrayBuffer[String],
      ArrayBuffer[String],
      ArrayBuffer[String],
      ArrayBuffer[String]
  ) = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    val colUniVal = getColumnUniqueValues
    val colRepVal = getColumnRepeatedValues
    val colCaseType = getColumnCaseType
    val colCntVal = getAbbreviationCount

    stopWatch.stop()

    logger.info(
      "String Analyzer Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
    (colUniVal, colRepVal, colCaseType, colCntVal)
  }

  private def getColumnUniqueValues: ArrayBuffer[String] = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    val columUniq = new ArrayBuffer[String]()

    for (inputRow <- strCSVFileContents) {
      columUniq += "Unique Values , " + inputRow.toList.distinct.length.toString
    }

    stopWatch.stop()

    logger.info(
      "String Analyzer (Unique Values) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

    return columUniq
  }

  private def getColumnRepeatedValues: ArrayBuffer[String] = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    val columRept = new ArrayBuffer[String]()

    val MAX_CHARS = 256
    val ctr = new Array[Int](MAX_CHARS)
    for (inputRow <- strCSVFileContents) {

      val repeatValues =
        inputRow.groupBy(identity).collect { case (x, Array(_, _, _*)) =>
          x
        }
      val repeatValuesCount = repeatValues.toList.length

      if (repeatValuesCount > 0) {
        columRept += "Repeated Values ," + (repeatValues + ", Count is :" + repeatValuesCount)
      } else {
        columRept += "Repeated Values ," + "None"
      }
    }

    stopWatch.stop()

    logger.info(
      "String Analyzer (Repeated Values) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

    return columRept
  }

  private def getColumnCaseType: ArrayBuffer[String] = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    var checkType = ""

    val columCase = new ArrayBuffer[String]()

    for (inputRow <- strCSVFileContents) {

      var upperCase = 0
      var lowerCase = 0
      var mixedCase = 0

      for (x <- inputRow) {
        checkType = x.split(",").mkString

        if (checkType == checkType.toLowerCase) {
          lowerCase += 1
        } else if (checkType == checkType.toUpperCase) {
          upperCase += 1
        } else
          mixedCase += 1
      }

      columCase += ("Case Type ," + "U" + upperCase + " : L" + lowerCase + " : M" + mixedCase).toString
    }

    stopWatch.stop()

    logger.info(
      "String Analyzer (Case Type) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

    return columCase
  }

  private def getAbbreviationCount: ArrayBuffer[String] = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    var checkType = ""
    var countAbbr = 0

    val columAbbr = new ArrayBuffer[String]()

    for (inputRow <- strCSVFileContents) {

      for (x <- inputRow) {
        checkType = x.split(",").mkString

        if (checkType.takeRight(1) == ".") {
          countAbbr += 1
        }
      }
      if (countAbbr > 0) {
        columAbbr += "Abbreviation Count ," + countAbbr.toString
      } else {
        columAbbr += "Abbreviation Count , None"
      }
    }

    stopWatch.stop()

    logger.info(
      "String Analyzer (Abbreviation Count) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

    return columAbbr

  }
}
