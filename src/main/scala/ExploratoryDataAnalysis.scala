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

    var colMin = retNum._1.toList
    var colMax = retNum._2.toList
    var colMean = retNum._3.toList

    var colUniq = retStr._1.toList
    var colRep = retStr._2.toList
    var colCase = retStr._3.toList
    var colAbbr = retStr._4.toList

    val outRes = ArrayBuffer[String]()

    for (i <- retType.indices) {

      outRes += retType(i).toString
      outRes += retSize(i).toString

      if (retType(i) == "Column Type , String") {

        outRes += colUniq.head
        outRes += colRep.head
        outRes += colCase.head
        outRes += colAbbr.head

        colUniq = colUniq.drop(1)
        colRep = colRep.drop(1)
        colCase = colCase.drop(1)
        colAbbr = colAbbr.drop(1)

      } else {

        outRes += colMin.head.toString
        outRes += colMax.head.toString
        outRes += colMean.head.toString

        colMin = colMin.drop(1)
        colMax = colMax.drop(1)
        colMean = colMean.drop(1)

      }
    }

    produceOutput(outRes)

    stopWatch.stop()

    logger.info(
      "Performance Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }

  private def produceOutput(strDataAnalyzed: ArrayBuffer[String]) {

    val stopWatch = new StopWatch()
    stopWatch.start()

    val outFile = "/home/dineshk/Downloads/Sample CSV Files/finalOut.csv"
    val objWriter = new PrintWriter(outFile)

    var j = 1
    for (i <- strDataAnalyzed) {

      if (i == "Column Type , String" || i == "Column Type , Numeric") {
        objWriter.write("\n")
        objWriter.write("Column No \t " + j + "\n\n")
        j = j + 1
      }

      objWriter.write(i + "\n")
    }
    objWriter.close()

    stopWatch.stop()

    logger.info(
      "Output Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }
}
