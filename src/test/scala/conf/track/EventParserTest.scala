package conf.track

import org.scalatest.FunSpec
import conf.track.DurationUnit._
/**
  * Created by apple on 11/7/16.
  */
class EventParserTest extends FunSpec{

  describe("Parse one event from one line"){
    it("should parse 'Common Ruby Errors 45min' from one line string "){
      val line = "Common Ruby Errors 45min";
      val event = EventParser.parseLine(line);
      assert( event != null);
      assert( "Common Ruby Errors" == event.description)
      assert( 45 == event.duration )
      assert( MINUTE == event.durationUnit )
      assert( "Common Ruby Errors 45min" == event.toString )
    }

    it("should parse 'Rails for Python Developers lightning' from one line string "){
      val line = "Rails for Python Developers lightning"
      val event = EventParser.parseLine(line);
      assert( event != null);
      assert( "Rails for Python Developers"  == event.description)
      assert( 1 == event.duration )
      assert( LIGHTENING == event.durationUnit )
      assert( "Rails for Python Developers 1lightning" == event.toString )
    }
  }

  describe("Parse events from input file"){
    it("the events parsed from InputFile should not be empty."){
      val filePath = getClass.getResource("/InputFile").getFile
      val events = EventParser.parse(filePath);
      assert( !events.isEmpty );
    }
  }
}
