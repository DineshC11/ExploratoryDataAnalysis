import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

class ExploratoryDataAnalysis extends Analyzer with LazyLogging {

  def analyzer(csvFilePath: String): Any = {

    println("Analyzer Method Call \n")

    val stopWatch = new StopWatch()
    stopWatch.start()

    performAnalysis(csvFilePath: String): Any {}

    stopWatch.stop()

    logger.info(
      "Analyzer Time Taken To Complete" + stopWatch
        .getTime() + " ms "
    )
  }

  private def performAnalysis(strCSVFileContents: String): Any = {

    println("\t Perform Analysis Method Call \n")

    val stopWatch = new StopWatch()
    stopWatch.start()

    checkColumnType()
    getColumnSize()

    val obj1 = new NumericAnalyzer("Test")
    obj1.process()

    val obj2 = new StringAnalyzer("Test")
    obj2.process()

    produceOutput(strCSVFileContents: String)

    stopWatch.stop()

    logger.info(
      "Performance Time Taken To Complete" + stopWatch
        .getTime() + " ms "
    )
  }

  private def produceOutput(strDataAnalyzed: String): Any = {

    println("\t\t Produce Output Method Call \n")

    val stopWatch = new StopWatch()
    stopWatch.start()

    stopWatch.stop()

    logger.info(
      "Output Time Taken To Complete" + stopWatch
        .getTime() + " ms "
    )

  }

  override def checkColumnType() = {

    println("\t\t Check Column Type Method Call")

    val stopWatch = new StopWatch()
    stopWatch.start()

    stopWatch.stop()

    logger.info(
      "Check Column Type Time Taken To Complete" + stopWatch
        .getTime() + " ms "
    )

  }
  override def getColumnSize() = {

    println("\t\t Get Column Size Method Call \n")

    val stopWatch = new StopWatch()
    stopWatch.start()

    stopWatch.stop()

    logger.info(
      "Get Column Size Time Taken To Complete" + stopWatch
        .getTime() + " ms "
    )

  }
}
