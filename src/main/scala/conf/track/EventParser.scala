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
    val pattern = """^(.+)\s(\d+)?\s?((min)|(lightning))$""".r

    val matched = pattern.findFirstMatchIn(line);

    return  matched match {
      case Some(m) => Event(
        m.group(1),
        if (m.group(2) == null || m.group(2).isEmpty) 1 else m.group(2).toInt,
        if (m.group(3).equalsIgnoreCase(MINUTE.name)) MINUTE else LIGHTENING
      )
      case None => null
    }
  }
}
