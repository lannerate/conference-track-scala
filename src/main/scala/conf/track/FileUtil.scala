package conf.track

import java.io.{BufferedReader, File, FileNotFoundException, FileReader}

/**
  * Created by hzhang3 on 11/7/2016.
  */
object FileUtil {

  def readResourceFile(path: String): List[String] =
    Option(getClass.getResourceAsStream(path)).map(scala.io.Source.fromInputStream)
      .map(_.getLines.toList)
      .getOrElse(throw new FileNotFoundException(path))

}
