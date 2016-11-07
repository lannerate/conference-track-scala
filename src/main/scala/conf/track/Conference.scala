package conf.track

/**
  * Created by hzhang3 on 11/7/2016.
  */
class Conference() {
  val tracks: List[Track] = List[Track]()

  def addTrack(track: Track) {
    track :: tracks
  }

  override def toString: String = {
    val builder: StringBuilder = new StringBuilder

    if( !tracks.isEmpty ){
      var index = 0;
      for (track <- tracks){
        index = index + 1
        builder.append("Track " + index +":" +"\n");
        builder.append(track);
        builder.append("\n");
      }
    }
    return builder.toString();
  }
}


class Track() {

  val periods: List[Period] = List[Period]()

  def addPeriod(period: Period) {
    period :: periods
  }

  override def toString: String = {
    val builder: StringBuilder = new StringBuilder

    for(period <- periods){
      builder.append(period)
    }
    return builder.toString
  }
}

class Period(startTime:Int, sessionDuration:Int) {
  val events = List[Event]()

  var otherPeriod: Period;

  var totalRemainingTime = startTime

  def addOtherPeriod(newPeriod:Period){
    otherPeriod = newPeriod
  }
  def addEvents(event: Event):Unit = {
    if (null != event) {
      if (totalRemainingTime < event.getDurationMinutes) throw new IllegalArgumentException("Not enough space time to take the event:" + event.getDescription)

       event :: events

      //consume this event, total remaining time need to minus this event duration time
      totalRemainingTime -= event.getDurationMinutes
    }
  }

  def hasEnoughSpaceTime(event: Event) = totalRemainingTime >= event.getDurationMinutes

  def addEventSchedule(events: List[Event], startTime: Int, collectedResult: StringBuilder): Int = {
    var nextStartTime: Int = startTime

    for (event <- events) {
      //format the output for each event.
      val outputForEvent: String = TimeFormater.format(nextStartTime) + " " + event + "\n"
      collectedResult.append(outputForEvent)
      //add the current event duration to next start time
      nextStartTime += event.getDurationMinutes
    }

    return nextStartTime
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
    return collectedResult.toString
  }

}

class Event(description: String, duration: Int, durationUnit: DurationUnit) {

  val getDurationMinutes = durationUnit.toMinutes(duration)

  override def toString: String = description + " " + duration + durationUnit
}

case class DurationUnit(base: Int, name: String){

  def toMinutes(duration: Int):Int =  duration * base

  override def toString: String = name
}

object DurationUnit {

  val MINUTE: DurationUnit = new DurationUnit(1, "min")

  val LIGHTENING: DurationUnit = new DurationUnit(5, "lightning")
}