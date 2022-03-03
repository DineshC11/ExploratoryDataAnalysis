import scala.io.StdIn.readLine
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.PrivateMethodTester

import java.io.PrintWriter
import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.language.reflectiveCalls

class TestCase extends AnyFunSuite with Analyzer {

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

  println("Enter the File Path :")
  val filePath: String = readLine()

  val objMain = new ExploratoryDataAnalysis
  objMain.analyzer(filePath)

  val inpHeader = Source
    .fromFile(
      filePath
    )
    .getLines
    .next()
    .split(",")
    .toArray

  for (i <- inpHeader.indices) {
    columName += inpHeader(i)
  }

  using(io.Source.fromFile(filePath)) { source =>
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

  using(io.Source.fromFile(tempFile)) { source =>
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

  test("Data Type Validation") {
    assert(retType(0) == "Column Type , Numeric")
  }

  test("Column Size Validation") {
    assert(retSize(0) == "Column Size , 999")
  }

}
