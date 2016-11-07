package conf.track

import java.io.BufferedReader

import org.scalatest.FunSpec
import conf.track.FileUtil.readResourceFile

/**
  * Created by hzhang3 on 11/7/2016.
  */
class ConferenceTrackTest extends FunSpec {

  describe("test conference trak") {

    it("conf.track.ConferenceTrackTest") {
      testConferenceSchedule()
    }


    def testConferenceSchedule() {
      val linesActual: List[String] = getActualLinesAfterScheduled("/InputFile")
//      val linesExcepted: List[String] = getExceptedLines("/OutputFile")
      //compare the size of excepted output file with the size of actual lines after scheduled.
      //      assertNotNull(linesActual)
      //      assertNotNull(linesExcepted)
      //      assertTrue(linesActual.size == linesExcepted.size)
      //Is each line equals
//      var i: Int = 0
//      while (i < linesActual.size) {
//        {
//          val lineActual: String = linesActual(i)
//          val lineExcepted: String = linesExcepted(i)
//          //          assertEquals(lineExcepted, lineActual)
//        }
//        {
//          i += 1;
//          i - 1
//        }
//      }
    }

    def getActualLinesAfterScheduled(inputFile: String): List[String] ={

      val filePath = getClass.getResource(inputFile).getFile

      val conference = ConferenceApp.schedule( filePath )

      return conference.toString.split("\n").toList
    }



    def getExceptedLines(exceptedFile: String): List[String] = readResourceFile(exceptedFile)

  }
}
