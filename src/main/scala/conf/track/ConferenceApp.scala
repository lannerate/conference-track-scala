package conf.track

import conf.track.DurationUnit._

import scala.collection.mutable.ListBuffer
import scala.runtime.Nothing$

/**
  * Created by hzhang3 on 11/7/2016.
  */
object ConferenceApp extends App {

  override def main(args: Array[String]) = {

    //1. read input files, parse files
    val inputFilePath: String = args(0)

    if (inputFilePath == null || inputFilePath.isEmpty ) {
      Logger.error("the input path is empty, please make sure your input path is valid ")
      System.exit(1);
    }
    //2. run conference schedule()
    val conferenceOption: Option[Conference] = schedule(inputFilePath);
    //3. print the context of conference
    println(conferenceOption.getOrElse("there is no conference content."));

  }


  def schedule(inputFilePath: String): Option[Conference] = {
    //        1. parse events from input files
    val events = EventParser.parse(inputFilePath)

    if (events.isEmpty) {
      Logger.warn("There is no events after parsed.")
     return None
    }
    //        2. process events
    //        > configure for Morning/Lunch/Afternoon/networking period.
    //        > populate Events to Period, consume events when the current period has enough space time.
    //        > populate Tracks to Conference
    Some(processEvents(events, Conference()));
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
      val morningPeriod: Period = Period(MORNING_SESSION_START_TIME, MORNING_SESSION_DURATION)
      populateEvents(morningPeriod, events)

      val lunchPeriod: Period = Period(LUNCH_START_TIME, LUNCH_DURATION)
      lunchPeriod.addEvents(Event("Lunch", LUNCH_DURATION, MINUTE))

      val afternoonPeriod: Period = Period(AFTERNOON_SESSION_START_TIME, AFTERNOON_SESSION_DURATION)
      populateEvents(afternoonPeriod, events)

      //adding networking period to afternoon period, specially handle the networking event.
      val netWorkingPeriod: Period = Period(NETWORK_EVENT_START_TIME, NETWORK_EVENT_DURATION)
      netWorkingPeriod.addEvents(Event("Networking Event", NETWORK_EVENT_DURATION, MINUTE))
      afternoonPeriod.addOtherPeriod(netWorkingPeriod)

      val track: Track = Track()
      track.addPeriod(morningPeriod)
      track.addPeriod(lunchPeriod)
      track.addPeriod(afternoonPeriod)
      conference.addTrack(track)
    }
    return conference
  }

  def populateEvents(period: Period, events: ListBuffer[Event]) = {
    for (event <- events) {
      if (period.hasEnoughSpaceTime(event)) {
        period.addEvents(event)
        events -= event
      }
    }
  }
}
