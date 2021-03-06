package conf.track

/**
  * Created by hzhang3 on 11/7/2016.
  */
object TimeFormater {

  def format(minutes: Int):String = {
    val maxSupportMinutes: Int = 24 * 60 - 1 // only one day
    if (minutes > maxSupportMinutes) {
      val msg = "greater than max support minutes:" + maxSupportMinutes + "min"
      Logger.error(msg)
      throw new IllegalArgumentException(msg)
    }

    var hoursDisplay: Int = minutes / 60
    val minutesDisplay: Int = minutes % 60

    val suffix: String = if (hoursDisplay >= 12) "PM"  else "AM"

    hoursDisplay = if (hoursDisplay > 12) hoursDisplay - 12 else hoursDisplay

    "%02d:%02d%s".format(hoursDisplay, minutesDisplay, suffix)
  }
}
