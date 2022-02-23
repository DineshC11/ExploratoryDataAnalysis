import scala.collection.mutable.ArrayBuffer
import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

class StringAnalyzer(strCSVFileContents: ArrayBuffer[Array[String]])
    extends LazyLogging {

  def process(): Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    getColumnUniqueValues
    getColumnRepeatedValues
    getColumnCaseType
    getAbbreviationCount

    stopWatch.stop()

    logger.info(
      "String Analyzer Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }

  private def getColumnUniqueValues: Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    for (inputRow <- strCSVFileContents) {
      println(
        "The Unique Column Value Count is : " + inputRow.toList.distinct.length
      )
    }

    stopWatch.stop()

    logger.info(
      "String Analyzer (Unique Values) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

  }

  private def getColumnRepeatedValues: Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    val MAX_CHARS = 256
    val ctr = new Array[Int](MAX_CHARS)
    for (inputRow <- strCSVFileContents) {

      val repeatValues =
        inputRow.groupBy(identity).collect { case (x, Array(_, _, _*)) => x }
      val repeatValuesCount = repeatValues.toList.length

      println(
        "The Column Repeated Value is : " + repeatValues + " and the count is : " + repeatValuesCount
      )
    }
    stopWatch.stop()

    logger.info(
      "String Analyzer (Repeated Values) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }

  private def getColumnCaseType: Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    stopWatch.stop()

    logger.info(
      "String Analyzer (Case Type) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }

  private def getAbbreviationCount: Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    stopWatch.stop()

    logger.info(
      "String Analyzer (Abbreviation Count) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }
}
