import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

import scala.collection.immutable.Nil.head
import scala.collection.mutable.ArrayBuffer

trait Analyzer extends LazyLogging {

  val stopWatch = new StopWatch()
  stopWatch.start()

  def parseDouble(s: String) = try { Some(s.toDouble) }
  catch { case _ => None }

  def checkColumnType(
      strCSVFileContents: ArrayBuffer[Array[String]]
  ): Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    for (inputRow <- strCSVFileContents) {

      if (
        (inputRow.head
          .forall(Character.isDigit)) || parseDouble(inputRow.head).isDefined
      ) {
        println("This Column is of Numeric")
      } else {
        println("This Column is of String")
      }
    }

    stopWatch.stop()

    logger.info(
      "Check Column Type Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }

  def getColumnSize(
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

  stopWatch.stop()

  logger.info(
    "Trait Time Taken To Complete : " + stopWatch
      .getTime() + " ms "
  )
}
