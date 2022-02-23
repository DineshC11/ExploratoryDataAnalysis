import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

class StringAnalyzer(strCSVFileContents: String) extends LazyLogging {

  def process(): Any = {

    println("\t\t\t String Process Method Call \n")

    val stopWatch = new StopWatch()
    stopWatch.start()

    getColumnUniqueValues
    getColumnRepeatedValues
    getColumnCaseType
    getAbbreviationCount

    stopWatch.stop()

    logger.info(
      "String Analyzer Time Taken To Complete" + stopWatch
        .getTime() + " ms "
    )
  }

  private def getColumnUniqueValues: Any = {
    println("\t\t\t\t Get Column Unique Values Method Call")

    val stopWatch = new StopWatch()
    stopWatch.start()

    stopWatch.stop()

    logger.info(
      "String Analyzer (Unique Values) Time Taken To Complete" + stopWatch
        .getTime() + " ms "
    )

  }

  private def getColumnRepeatedValues: Any = {
    println("\t\t\t\t Get Column Repeated Values Method Call")

    val stopWatch = new StopWatch()
    stopWatch.start()

    stopWatch.stop()

    logger.info(
      "String Analyzer (Repeated Values) Time Taken To Complete" + stopWatch
        .getTime() + " ms "
    )
  }

  private def getColumnCaseType: Any = {
    println("\t\t\t\t Get Column Case Type Method Call")

    val stopWatch = new StopWatch()
    stopWatch.start()

    stopWatch.stop()

    logger.info(
      "String Analyzer (Case Type) Time Taken To Complete" + stopWatch
        .getTime() + " ms "
    )
  }

  private def getAbbreviationCount: Any = {
    println("\t\t\t\t Get Abbreviation Count Method Call \n")

    val stopWatch = new StopWatch()
    stopWatch.start()

    stopWatch.stop()

    logger.info(
      "String Analyzer (Abbreviation Count) Time Taken To Complete" + stopWatch
        .getTime() + " ms "
    )
  }
}
