package conf.track

import org.scalatest.FunSpec
import conf.track.TimeFormater.format
/**
  * Created by apple on 11/7/16.
  */
class TimeFormaterTest extends FunSpec{

  describe("test format time"){
    it("12:00 should be formatted 12:00PM"){
      assert(format(12 * 60) == "12:00PM" )
    }

    it("15:30 should be formatted 03:30PM"){
      assert(format(15 * 60 + 30) == "03:30PM" )
    }

    it("9:40 should be formatted 09:40AM"){
      assert(format(9 * 60 + 40) == "09:40AM" )
    }
  }
}
