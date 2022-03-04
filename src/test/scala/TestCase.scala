import scala.io.Source
import java.io.PrintWriter
import scala.io.StdIn.readLine
import scala.language.reflectiveCalls
import org.scalatest.funsuite.AnyFunSuite
import scala.collection.mutable.ArrayBuffer

class TestCase extends AnyFunSuite with Analyzer {

  val inputRows = new ArrayBuffer[Array[String]]()
  var columnTypeNum: String = ""
  var columnTypeStr: String = ""

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

  using(io.Source.fromFile(tempFile)) { source =>
    for (inputLine <- source.getLines) {
      inputRows += inputLine.split(",").map(_.trim)
    }
  }

  for (inputRow <- inputRows) {

    if (
      (inputRow.head
        .forall(Character.isDigit)) || parseDouble(inputRow.head).isDefined
    ) {
      columnTypeNum = "Numeric"
    } else {
      columnTypeStr = "String"
    }
  }

  val retSizeVal: Boolean = inputRows.length match {
    case x if 0 until 1000 contains x => true
    case _                            => false
  }

  test("Column Size Validation") {
    assert(retSizeVal)
  }

  test("Numeric Input Rows Validation") {
    assert(columnTypeNum == "Numeric")
  }

  test("String Input Rows Validation") {
    assert(columnTypeStr == "String")
  }
}
