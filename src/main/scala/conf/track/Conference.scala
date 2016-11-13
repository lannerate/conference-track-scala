package conf.track

import scala.collection.mutable.ListBuffer

/**
  * Created by hzhang3 on 11/7/2016.
  */
case class Conference() {
  val tracks: ListBuffer[Track] = ListBuffer[Track]();

  def addTrack(track: Track) {
    tracks += track
  }

  override def toString: String = {
    val builder: StringBuilder = new StringBuilder

    if (!tracks.isEmpty) {
      var index = 0;
      for (track <- tracks) {
        index = index + 1
        builder.append("Track " + index + ":" + "\n");
        builder.append(track);
        builder.append("\n");
      }
    }
    return builder.toString();
  }
}


case class Track() {

  val periods = ListBuffer[Period]()

  def addPeriod(period: Period) {
    periods += period
  }

  override def toString: String = {
    val builder: StringBuilder = new StringBuilder

    for (period <- periods) {
      builder.append(period)
    }
    return builder.toString
  }
}


case class Period(startTime: Int, sessionDuration: Int) {
  val events = ListBuffer[Event]()

  var otherPeriod: Period = null;

  //firstly init period, for morning session period, lunch period, afternoon session period, and networking period.
  var totalRemainingTime = sessionDuration

  def addOtherPeriod(newPeriod: Period) = {
    otherPeriod = newPeriod
  }
  def hasEnoughSpaceTime(event: Event) = totalRemainingTime >= event.getDurationMinutes

  def addEvents(event: Event): Unit = {
    if (null != event) {
      if (totalRemainingTime < event.getDurationMinutes) {
        val msg ="Not enough space time to take the event:" + event.toString
        Logger.warn(msg)
        throw new IllegalArgumentException(msg)
      }
      events += event

      //consume this event, total remaining time need to minus this event duration time
      totalRemainingTime -= event.getDurationMinutes
    }
  }

  def addEventSchedule(events: ListBuffer[Event], startTime: Int, collectedResult: StringBuilder): Int = {
    var nextStartTime: Int = startTime

    for (event <- events) {
      //format the output for each event.
      val outputForEvent = TimeFormater.format(nextStartTime) + " " + event + "\n"
      collectedResult.append(outputForEvent)
      //add the current event duration to next start time
      nextStartTime += event.getDurationMinutes
    }

    nextStartTime
  }

  override def toString: String = {
    val collectedResult: StringBuilder = new StringBuilder
    var nextStartTime: Int = addEventSchedule(events, startTime, collectedResult)

    //if have networking period for this problem, need to re-calculate next start time.
    if (otherPeriod != null) {
      var otherStartTime: Int = otherPeriod.startTime
      if (nextStartTime > otherStartTime) otherStartTime = nextStartTime

      nextStartTime = addEventSchedule(otherPeriod.events, otherStartTime, collectedResult)
    }
    collectedResult.toString
  }

}

case class Event(description: String, duration: Int, durationUnit: DurationUnit) {

  val getDurationMinutes = durationUnit.toMinutes(duration)

  override def toString: String = description + " " + duration + durationUnit
}

case class DurationUnit (base: Int, name: String) {

  def toMinutes(duration: Int): Int = duration * base

  override def toString: String = name
}

object DurationUnit extends Enumeration{

  val MINUTE: DurationUnit = DurationUnit(1, "min")

  val LIGHTENING: DurationUnit =  DurationUnit(5, "lightning")
}