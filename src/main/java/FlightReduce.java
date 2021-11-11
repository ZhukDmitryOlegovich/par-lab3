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
    }
}
