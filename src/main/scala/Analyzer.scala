import scala.collection.mutable.ArrayBuffer
import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

trait Analyzer extends LazyLogging {

  val stopWatch = new StopWatch()
  stopWatch.start()

  def parseDouble(s: String) = try { Some(s.toDouble) }
  catch { case _ => None }

  def checkColumnType(
      strCSVFileContents: ArrayBuffer[Array[String]]
  ): ArrayBuffer[String] = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    val columType = new ArrayBuffer[String]()

    for (inputRow <- strCSVFileContents) {

      if (
        (inputRow.head
          .forall(Character.isDigit)) || parseDouble(inputRow.head).isDefined
      ) {
        columType += "Numeric"
      } else {
        columType += "String"
      }
    }

    stopWatch.stop()

    logger.info(
      "Check Column Type Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

    return columType
  }

  def getColumnSize(
      strCSVFileContents: ArrayBuffer[Array[String]]
  ): ArrayBuffer[Int] = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    val columSize = new ArrayBuffer[Int]()

    for (inputRow <- strCSVFileContents) {
      columSize += inputRow.toList.length.toInt
    }

    stopWatch.stop()

    logger.info(
      "Get Column Size Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

    return columSize
  }

  stopWatch.stop()

  logger.info(
    "Trait Time Taken To Complete : " + stopWatch
      .getTime() + " ms "
  )
}
