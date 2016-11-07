package conf.track

import java.io.{BufferedReader, File, FileNotFoundException, FileReader}

/**
  * Created by hzhang3 on 11/7/2016.
  */
object FileUtil {

  def readFile(filePath: String): BufferedReader = {
    if( filePath == null || filePath.isEmpty() ) throw new FileNotFoundException("file path is empty, can not find the file")

    return new BufferedReader( new FileReader( new File(filePath) ));
  }

}
