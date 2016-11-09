package conf.track

import java.util.logging.Level

/**
  * Created by apple on 11/10/16.
  */
object Logger {

  def log(level:Level, msg:String) {
    println("%s-%s".format(level.name, msg))
  }

  def info(msg:String) = log(Level.INFO,msg)

  def warn(msg:String) = log(Level.WARNING,msg)

  def error(msg:String) = log(Level.ERROR,msg)

}

case class Level(name:String){
  override def toString: String = name
}

object Level {
  val INFO = Level("INFO")
  val WARNING = Level("WARNING")
  val ERROR = Level("ERROR")
}

