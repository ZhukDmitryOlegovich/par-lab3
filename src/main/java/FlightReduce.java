import scala.Serializable;

public class FlightReduce implements Serializable {
    private float maxDelay;
    private int countDelays;
    private int countCancelled;
    private int countFlights;

    public FlightReduce(float delay, boolean canceled) {
        maxDelay = delay;
        countDelays = delay == 0 ? 0 : 1;
        countCancelled = canceled ? 0 : 1;
        countFlights = 1;
    }

    public static FlightReduce merge(FlightReduce accumulate, FlightReduce other) {
        accumulate.maxDelay = Math.max(accumulate.maxDelay, other.maxDelay);
        accumulate.countDelays += other.countDelays;
        accumulate.countCancelled += other.countCancelled;
        accumulate.countFlights += other.countFlights;
        return accumulate;
    }

    public String getStatistics() {
        return 
    }
}
