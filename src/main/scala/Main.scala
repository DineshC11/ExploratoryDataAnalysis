import scala.io.StdIn.readLine
import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

object Main extends LazyLogging {
  def main(args: Array[String]) {

    val stopWatch = new StopWatch()
    stopWatch.start()

    println("Enter the File Path :")
    val filePath: String = readLine()

    val obj = new ExploratoryDataAnalysis
    obj.analyzer(filePath)

    stopWatch.stop()

    logger.info(
      "Main Function Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }
}
