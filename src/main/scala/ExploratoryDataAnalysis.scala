import scala.util.Try
import java.io.PrintWriter
import util.control.Exception._
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

class ExploratoryDataAnalysis extends Analyzer with LazyLogging {

  val inputType = new ArrayBuffer[Array[String]]()
  val inputRows = new ArrayBuffer[Array[String]]()

  val tempFile = "/home/dineshk/Downloads/Sample CSV Files/tempProcess.csv"

  val objWriter = new PrintWriter(tempFile)

  def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B =
    try {
      f(resource)
    } finally {
      resource.close()
    }

  def analyzer(csvFilePath: String): Unit = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    using(io.Source.fromFile(csvFilePath)) { source =>
      for (inputLine <- source.getLines) {
        inputType += inputLine.split(",").map(_.trim)
      }
    }

    (inputType.transpose).foreach(validInput => {

      var transFormat = validInput.toString().replace("ArrayBuffer(", "")
      transFormat = transFormat.replace(")", "")

      objWriter.write(transFormat + "\n")
    })

    objWriter.close()

    performAnalysis(tempFile)

    stopWatch.stop()

    logger.info(
      "Analyzer Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }

  private def performAnalysis(strCSVFileContents: String): Unit = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    using(io.Source.fromFile(strCSVFileContents)) { source =>
      for (inputLine <- source.getLines) {
        inputRows += inputLine.split(",").map(_.trim)
      }
    }

    checkColumnType(inputRows)
    getColumnSize(inputRows)

//    val obj1 = new NumericAnalyzer(inputRows)
//    obj1.process()

    val obj2 = new StringAnalyzer(inputRows)
    obj2.process()

    //    produceOutput(strCSVFileContents: String)

    stopWatch.stop()

    logger.info(
      "Performance Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }

  private def produceOutput(strDataAnalyzed: String): Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    stopWatch.stop()

    logger.info(
      "Output Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

  }

  override def checkColumnType(
      strCSVFileContents: ArrayBuffer[Array[String]]
  ): Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    for (inputRow <- strCSVFileContents) {

      if (inputRow.head.forall(Character.isDigit)) {
        println("This Column is Numeric")
      } else {
        println("This Column is String")
      }
    }

    stopWatch.stop()

    logger.info(
      "Check Column Type Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

  }

  override def getColumnSize(
      strCSVFileContents: ArrayBuffer[Array[String]]
  ): Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    for (inputRow <- strCSVFileContents) {
      println("The Column Length is : " + inputRow.toList.length)
    }

    stopWatch.stop()

    logger.info(
      "Get Column Size Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

  }
}
