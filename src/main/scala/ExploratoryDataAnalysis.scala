import scala.io.Source
import java.io.PrintWriter
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

class ExploratoryDataAnalysis extends Analyzer with LazyLogging {

  val inputType = new ArrayBuffer[Array[String]]()
  val inputRows = new ArrayBuffer[Array[String]]()
  val numericCols = new ArrayBuffer[Array[String]]()
  val stringCols = new ArrayBuffer[Array[String]]()
  val columName = new ArrayBuffer[String]()

  val tempFile = "/home/dineshk/Downloads/Sample CSV Files/tempProcess.csv"

  val objWriter = new PrintWriter(tempFile)

  def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B =
    try {
      f(resource)
    } finally {
      resource.close()
    }

  /** #1 Func : Analyze the initial file possibilities.
    *
    * @param : File path as string
    * @return : The initial operation of opening the file and performing the matrix
    * transpose and store it into a temporary file to continue further processing. So that
    * it won’t affect the original data given. From here the performAnalysis method gets
    * call’ ed.
    */

  def analyzer(csvFilePath: String) {

    val stopWatch = new StopWatch()
    stopWatch.start()

    val inpHeader = Source
      .fromFile(
        csvFilePath
      )
      .getLines
      .next()
      .split(",")
      .toArray

    for (i <- inpHeader.indices) {
      columName += inpHeader(i)
    }

    using(io.Source.fromFile(csvFilePath)) { source =>
      for (inputLine <- source.getLines.drop(1)) {
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

  /** #2 Func : Perform the analyzed file possibilities.
    *
    * @param : File contents as string
    * @return : Where in this method it performs the operation like splitting the Numeric columns and String columns and
    * from here the identified columns are sent to their respective Numeric Analyzer
    * and String Analyzer class. Gets back the values returned from the different classes
    * and consolidates them into a formatted output. From here the produceOutput
    * method gets call’ ed.
    */

  private def performAnalysis(strCSVFileContents: String) {

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

  /** #3 Func : Produce the final output as an csv file format.
    *
    * @param : Processed file contents as string
    * @return : Where in this method it performs the
    * operation of producing the output in a formatted way and writing it into a csv file.
    * Thus concludes the final operation.
    */

  private def produceOutput(strDataAnalyzed: ArrayBuffer[String]) {

    val stopWatch = new StopWatch()
    stopWatch.start()

    var countIterator = 1

    val outFile = "/home/dineshk/Downloads/Sample CSV Files/finalOut.csv"
    val objWriter = new PrintWriter(outFile)

    for (i <- strDataAnalyzed) {

      if (i == "Column Type , String" || i == "Column Type , Numeric") {

        if (columName.nonEmpty) {

          objWriter.write("\n")
          objWriter.write("Column No \t " + countIterator + "\n")
          objWriter.write(
            "Column Name \t " + columName(countIterator - 1) + "\n\n"
          )
        } else {

          objWriter.write("\n")
          objWriter.write("Column No \t " + countIterator + "\n\n")
        }
        countIterator = countIterator + 1
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
