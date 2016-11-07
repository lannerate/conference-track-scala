package conf.track

import conf.track.DurationUnit._

import scala.collection.mutable.ListBuffer

/**
  * Created by hzhang3 on 11/7/2016.
  */
object ConferenceApp extends App {

  override def main(args: Array[String]) = {

    //1. read input files, parse files
    val inputFilePath: String = args(0)

    if (inputFilePath == null || inputFilePath.isEmpty()) {
      System.exit(1);
    }
    //2. run conference schedule()
    val conference: Conference = schedule(inputFilePath);
    //3. print the context of conference
    println(conference);

  }



  def schedule(inputFilePath: String): Conference = {
    //        1. parse events from input files
    var events = EventParser.parse(inputFilePath)

    if (events.isEmpty) {
      return null;
    }
    //        2. process events
    //        > configure for Morning/Lunch/Afternoon/networking period.
    //        > populate Events to Period, consume events when the current period has enough space time.
    //        > populate Tracks to Conference
    val conference: Conference = new Conference();
    processEvents(events, conference);
    return conference;
  }


  def processEvents(events: ListBuffer[Event], conference: Conference): Conference = {
    val MORNING_SESSION_DURATION: Int = 180 //minutes
    val LUNCH_DURATION: Int = 60
    val AFTERNOON_SESSION_DURATION: Int = 240
    val NETWORK_EVENT_DURATION: Int = 60

    val MORNING_SESSION_START_TIME: Int = 9 * 60
    val LUNCH_START_TIME: Int = MORNING_SESSION_START_TIME + MORNING_SESSION_DURATION
    val AFTERNOON_SESSION_START_TIME: Int = LUNCH_START_TIME + LUNCH_DURATION
    val NETWORK_EVENT_START_TIME: Int = (12 * 60) + (5 * 60) // 5 PM

    while (events != null && !events.isEmpty) {
      //config periods
      val morningPeriod: Period = new Period(MORNING_SESSION_START_TIME, MORNING_SESSION_DURATION)
      populateEvents(morningPeriod, events)
      val lunchPeriod: Period = new Period(LUNCH_START_TIME, LUNCH_DURATION)
      lunchPeriod.addEvents(new Event("Lunch", LUNCH_DURATION, MINUTE))
      val afternoonPeriod: Period = new Period(AFTERNOON_SESSION_START_TIME, AFTERNOON_SESSION_DURATION)
      populateEvents(afternoonPeriod, events)
      //adding networking period to afternoon period, specially handle the networking event.
      val netWorkingPeriod: Period = new Period(NETWORK_EVENT_START_TIME, NETWORK_EVENT_DURATION)
      netWorkingPeriod.addEvents(new Event("Networking Event", NETWORK_EVENT_DURATION, MINUTE))
      afternoonPeriod.addOtherPeriod(netWorkingPeriod)

      var track: Track = new Track
      track.addPeriod(morningPeriod)
      track.addPeriod(lunchPeriod)
      track.addPeriod(afternoonPeriod)
      conference.addTrack(track)
    }
    return conference
  }

  def populateEvents(morningPeriod: Period, events: ListBuffer[Event]) = {
    for (event <- events) {
      if (morningPeriod.hasEnoughSpaceTime(event)) {
        morningPeriod.addEvents(event)
        events -= event
      }
    }
  }
}
