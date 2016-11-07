## Implementing the solution for Problem1 by scala language

##The rule for Problem 1
- The conference has multiple tracks each of which has a morning and afternoon session.
Each session contains multiple talks.
- Morning sessions begin at 9am and must finish before 12 noon, for lunch.
- Afternoon sessions begin at 1pm and must finish in time for the networking event.
- The networking event can start no earlier than 4:00 and no later than 5:00.
- No talk title has numbers in it.
- All talk lengths are either in minutes (not hours) or lightning (5 minutes).
- Presenters will be very punctual; there needs to be no gap between sessions.

##The Solution
Refer to the First-fit algorithm https://en.wikipedia.org/wiki/Bin_packing_problem

## Design
Based on Object-Oriented Analysis, OOA, which includes the following entities.

The relationship of entities as below
> `Conference -- 1 * n --> Track -- 1 * n --> Period -- 1 * n --> Event `

#### Conference 
The conference can contains multiple tracks.
#### Track
Each track can contains multiple event periods, that may include morning session period, lunch period, or afternoon session period, or networking period.
#### Period
Each Period can contains multiple events(talk or networking). 
#### Event
Each event means every talk session or networking, which has event description, and event duration.

### The processing of schedule conference.
The main method is in `ConferenceApp`, the basic processes as below.
> 1. Read input files, and parse files to events.

> 2. Run the method `ConferenceApp.schedule()` to get the scheduled conference.
> > * Configure Morning/Lunch/Afternoon/Networking Period.
> > * Populate Events to Period, consuming event when the current period has enough space time.
> > * Populate Tracks to Conference.

> 3. Print the context of conference.

##How to Run it

> 1. Switch the root project directory, execute the following cmd to build it.
> `> sbt compile`
> `> sbt package`

> 2. After build it successfully,in the folder `$rootproject/target/scala-2.xx`, you will find the build jar `conference-track-scala_2.xx.jar`
> and then run the command to get conference result 
>`> scala conference-track-scala_2.xx.jar your_input_file`
> For example
>`> scala target/scala-2.11/conference-track-scala_2.11-1.0.jar ./src/test/resources/InputFile`

