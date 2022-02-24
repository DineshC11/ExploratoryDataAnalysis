import scala.collection.mutable.ArrayBuffer
import com.typesafe.scalalogging.LazyLogging
import jdk.nashorn.internal.objects.NativeString.toLowerCase
import org.apache.commons.lang3.time.StopWatch

import java.lang.Character.isLowerCase
import scala.Console.in

class StringAnalyzer(strCSVFileContents: ArrayBuffer[Array[String]])
    extends LazyLogging {

  def process(): Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    getColumnUniqueValues
    getColumnRepeatedValues
    getColumnCaseType
    getAbbreviationCount

    stopWatch.stop()

    logger.info(
      "String Analyzer Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }

  private def getColumnUniqueValues: Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    for (inputRow <- strCSVFileContents) {
      println(
        "The Unique Column Value Count is : " + inputRow.toList.distinct.length
      )
    }

    stopWatch.stop()

    logger.info(
      "String Analyzer (Unique Values) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )

  }

  private def getColumnRepeatedValues: Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    val MAX_CHARS = 256
    val ctr = new Array[Int](MAX_CHARS)
    for (inputRow <- strCSVFileContents) {

      val repeatValues =
        inputRow.groupBy(identity).collect { case (x, Array(_, _, _*)) =>
          x
        }
      val repeatValuesCount = repeatValues.toList.length

      if (repeatValues.nonEmpty) {
        println(
          "The Column Repeated Value is : " + repeatValues + " and the count is : " + repeatValuesCount
        )
      } else {
        println(
          "The Column has no Repeated Values"
        )
      }
    }

    stopWatch.stop()

    logger.info(
      "String Analyzer (Repeated Values) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }

  private def getColumnCaseType: Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    var checkType = ""

    var upperCase = 0
    var lowerCase = 0
    var mixedCase = 0

    for (inputRow <- strCSVFileContents) {

      for (x <- inputRow) {
        checkType = x.split(",").mkString

        if (checkType == checkType.toLowerCase) {
          lowerCase += 1
        } else if (checkType == checkType.toUpperCase) {
          upperCase += 1
        } else
          mixedCase += 1
      }
    }

    println(
      "\n Upper Case Count is : " + upperCase + "\n Lower Case Count is : " + lowerCase + "\n Mixed Case Count is : " + mixedCase
    )

    stopWatch.stop()

    logger.info(
      "String Analyzer (Case Type) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }

  private def getAbbreviationCount: Any = {

    val stopWatch = new StopWatch()
    stopWatch.start()

    var checkType = ""
    var countAbbr = 0

    for (inputRow <- strCSVFileContents) {

      for (x <- inputRow) {
        checkType = x.split(",").mkString

        if (checkType.takeRight(1) == ".") {
          countAbbr += 1
        }
      }
    }

    if (countAbbr != 0) {
      println(
        "\n Total Number of Abbreviation Count in This File is : " + countAbbr + "\n"
      )
    } else {
      println(
        "\n No Abbreviation found in this file \n"
      )
    }

    stopWatch.stop()

    logger.info(
      "String Analyzer (Abbreviation Count) Time Taken To Complete : " + stopWatch
        .getTime() + " ms "
    )
  }
}
