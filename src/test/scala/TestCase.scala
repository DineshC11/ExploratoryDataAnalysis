import org.scalatest.funsuite.AnyFunSuite

import java.io.PrintWriter
import scala.io.Source
import scala.io.StdIn.readLine

class TestCase extends AnyFunSuite {

  println("Enter the File Path :")
  val filePath: String = readLine()

  val objMain = new ExploratoryDataAnalysis
  objMain.analyzer(filePath)
}
