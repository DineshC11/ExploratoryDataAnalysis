import scala.io.StdIn.readLine
import org.scalatest.funsuite.AnyFunSuite

class TestCase extends AnyFunSuite {

  println("Enter the File Path :")
  val filePath: String = readLine()

  val objMain = new ExploratoryDataAnalysis
  objMain.analyzer(filePath)
}
