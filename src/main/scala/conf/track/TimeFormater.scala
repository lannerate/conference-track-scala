package conf.track

/**
  * Created by hzhang3 on 11/7/2016.
  */
object TimeFormater {

  def format(minutes: Int):String = {
    val maxSupportMinutes: Int = 24 * 60 - 1 // only one day
    if (minutes > maxSupportMinutes) throw new IllegalArgumentException("greater than max support minutes:" + maxSupportMinutes + "min")
    var hoursDisplay: Int = minutes / 60
    val minutesDisplay: Int = minutes % 60

    val suffix: String = if (hoursDisplay >= 12) "PM"  else "AM"

    hoursDisplay = if (hoursDisplay > 12) hoursDisplay - 12 else hoursDisplay

    String.format("%02d:%02d%s", hoursDisplay, minutesDisplay, suffix)
  }
}
