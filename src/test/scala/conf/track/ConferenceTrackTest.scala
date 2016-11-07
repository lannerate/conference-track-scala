package conf.track

import java.io.BufferedReader

import org.scalatest.FunSpec
import conf.track.FileUtil.readResourceFile

/**
  * Created by hzhang3 on 11/7/2016.
  */
class ConferenceTrackTest extends FunSpec {

  describe("Compare excepted content with the content of scheduled conference") {

    it("The content of excepted OutputFile should be same as the content of Scheduled Conference") {
      val linesActual: List[String] = getActualLinesAfterScheduled("/InputFile")
      val linesExcepted: List[String] = getExceptedLines("/OutputFile")

      assert( !linesActual.isEmpty )
      assert( !linesExcepted.isEmpty )
      assert( linesActual.size == linesExcepted.size )

      val countOfDifferentLine = getCountOfDifferentLines(linesActual,linesExcepted);
      assert( countOfDifferentLine == 0 )
    }

    def getCountOfDifferentLines(l1:List[String], l2: List[String]) =
      l1.zip(l2).count( { case (x,y) => x != y })

    def getActualLinesAfterScheduled(inputFile: String): List[String] ={

      val filePath = getClass.getResource(inputFile).getFile

      val conference = ConferenceApp.schedule( filePath )

      return conference.toString.split("\n").toList
    }

    def getExceptedLines(exceptedFile: String): List[String] = readResourceFile(exceptedFile)

  }
}
