package conf.track

import java.io.BufferedReader
import java.util.regex.{Matcher, Pattern}

import conf.track.DurationUnit._

import scala.collection.mutable.ListBuffer
import scala.io.Source
/**
  * Created by hzhang3 on 11/7/2016.
  */
object EventParser {

  def parse(filePath: String): ListBuffer[Event] = {

    val events = new ListBuffer[Event]()

    for (line <- Source.fromFile(filePath).getLines()) {
      events += parseLine(line)
    }

    return events
  }

  def parseLine(line: String): Event = {
    val INDEX_EVENET_DESC: Int = 1
    val INDEX_EVENET_DURATION: Int = 2
    val INDEX_EVENET_DURATION_UNIT: Int = 3

    if (line == null || line.isEmpty) return null

    /** the sample
      * Common Ruby Errors 45min
      * Rails for Python Developers lightning
      * Communicating Over Distance 60min
      */
    //       Using the regex expression to parse event's fields.
    //      ^(.+)\               : event description
    //      (\d+)?               : event duration
    //      ((min)|(lightning))$ : event duration unit
    val matcher: Matcher = Pattern.compile("^(.+)\\s(\\d+)?\\s?((min)|(lightning))$").matcher(line)

    if (!matcher.find) return null

    val durationUnitStr: String = matcher.group(INDEX_EVENET_DURATION_UNIT)
    if (durationUnitStr == null || durationUnitStr.isEmpty) return null

    val description: String = matcher.group(INDEX_EVENET_DESC)
    val duration: String = matcher.group(INDEX_EVENET_DURATION)

    return Event(
      description,
      if (duration == null || duration.isEmpty) 1 else duration.toInt,
      if (durationUnitStr.equalsIgnoreCase( MINUTE.name )) MINUTE else  LIGHTENING
    )
  }
}
