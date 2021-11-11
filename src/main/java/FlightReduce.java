import scala.Serializable;

public class FlightReduce implements Serializable {
    private float maxDelay;
    private int countDelays;
    private int countCancelled;
    private int countFlights;

    private static final String FORMAT = "max:%3.0f,cancelled(%%):%6.2f,delay(%%):%6.2f";

    public FlightReduce(Flight flight) {
        maxDelay = flight.delayTime;
        countDelays = flight.delayTime == 0 ? 0 : 1;
        countCancelled = flight.cancelled ? 0 : 1;
        countFlights = 1;
    }

    public static FlightReduce accumulate(FlightReduce accumulator, Flight flight) {
        accumulator.maxDelay = Math.max(accumulator.maxDelay, flight.delayTime);
        accumulator.countDelays += flight.delayTime == 0 ? 0 : 1;
        accumulator.countCancelled += flight.cancelled ? 0 : 1;
        accumulator.countFlights += 1;
        return accumulator;
    }

    public static FlightReduce merge(FlightReduce accumulator, FlightReduce other) {
        accumulator.maxDelay = Math.max(accumulator.maxDelay, other.maxDelay);
        accumulator.countDelays += other.countDelays;
        accumulator.countCancelled += other.countCancelled;
        accumulator.countFlights += other.countFlights;
        return accumulator;
    }

    public final String getStatistics() {
        return String.format(
                FORMAT,
                maxDelay,
                100f * countCancelled / countFlights,
                100f * countDelays / countFlights
        );
    }
}
