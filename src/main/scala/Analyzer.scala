import scala.collection.mutable.ArrayBuffer
import com.typesafe.scalalogging.LazyLogging
import org.apache.commons.lang3.time.StopWatch

trait Analyzer extends LazyLogging {

  val stopWatch = new StopWatch()
  stopWatch.start()

  def parseDouble(s: String) = try { Some(s.toDouble) }
  catch { case _ => None }

  /** #1 Func : Column Type Identification for each column given.
    *
    * @param : File contents as array buffer of string
    * @return : Basically, what it does was figure out the
    * data type of each column.
    */

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
        columType += "Column Type , Numeric"
      } else {
        columType += "Column Type , String"
      }
    }

    stopWatch.stop()

    logger.info(
      "Check Column Type Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

    return columType
  }

  /** #2 Func : Column Size Identification for each column given.
    *
    * @param : File contents as array buffer of string
    * @return : Basically, what it does was figure out the
    * size of each column.
    */

  def getColumnSize(
      strCSVFileContents: ArrayBuffer[Array[String]]
  ): ArrayBuffer[String] = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    val columSize = new ArrayBuffer[String]()

    for (inputRow <- strCSVFileContents) {
      columSize += "Column Size , " + inputRow.toList.length.toInt.toString
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
