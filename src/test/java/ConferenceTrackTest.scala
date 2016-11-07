import java.io.{BufferedReader, FileNotFoundException, IOException}
import java.util.Arrays

import conf.track.{Conference, ConferenceApp, FileUtil}
import org.scalatest.FunSpec

/**
  * Created by hzhang3 on 11/7/2016.
  */
class ConferenceTrackTest extends FunSpec{

  describe("test conference trak"){

    it("ConferenceTrackTest"){
      testConferenceSchedule()
    }


    def testConferenceSchedule() {
      val linesActual: List[String] = getActualLinesAfterScheduled("InputFile")
      val linesExcepted: List[String] = getExceptedLines("OutputFile")
      //compare the size of excepted output file with the size of actual lines after scheduled.
//      assertNotNull(linesActual)
//      assertNotNull(linesExcepted)
//      assertTrue(linesActual.size == linesExcepted.size)
      //Is each line equals
      var i: Int = 0
      while (i < linesActual.size ) {
        {
          val lineActual: String = linesActual(i)
          val lineExcepted: String = linesExcepted(i)
//          assertEquals(lineExcepted, lineActual)
        }
        {
          i += 1; i - 1
        }
      }
    }

    def getActualLinesAfterScheduled(inputFile: String): List[String] =
    {
      val app:ConferenceApp = new ConferenceApp();
      val conference: Conference = app.schedule(findResourceFile(inputFile))
      return conference.toString.split("\n").toList
    }


    def getExceptedLines(exceptedFile: String): List[String] =
    {
      val linesExcepted: List[String] = List[String]()
      val outputReader: BufferedReader = FileUtil.readFile(findResourceFile(exceptedFile))
      var lineExcepted: String = null
      while ((lineExcepted = outputReader.readLine) != null) lineExcepted :: linesExcepted

      linesExcepted
    }

    def findResourceFile(fileName: String): String =
    {
      return getClass.getClassLoader.getResource(fileName).getFile
    }
  }
}
