import scala.util.Try
import java.io.PrintWriter
import util.control.Exception._
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

class ExploratoryDataAnalysis extends Analyzer with LazyLogging {

  val inputType = new ArrayBuffer[Array[String]]()
  val inputRows = new ArrayBuffer[Array[String]]()
  val numericCols = new ArrayBuffer[Array[String]]()
  val stringCols = new ArrayBuffer[Array[String]]()

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

    val retType = checkColumnType(inputRows)
    val retSize = getColumnSize(inputRows)

    println(retType)
    println(retSize)

    for (inputRow <- inputRows) {

      if (
        (inputRow.head
          .forall(Character.isDigit)) || parseDouble(inputRow.head).isDefined
      ) {
        numericCols += inputRow
      } else {
        stringCols += inputRow
      }
    }

    val obj1 = new NumericAnalyzer(numericCols)
    val retNum = obj1.process()

    val obj2 = new StringAnalyzer(stringCols)
    val retStr = obj2.process()

    println(retNum)
    println(retStr)

    //produceOutput(strNumericContents)

    stopWatch.stop()

    logger.info(
      "Performance Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }

  private def produceOutput(strDataAnalyzed: Any): Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    //print(strDataAnalyzed)

    stopWatch.stop()

    logger.info(
      "Output Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }
}
