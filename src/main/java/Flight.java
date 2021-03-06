import scala.Serializable;
import scala.Tuple2;

public class Flight implements Serializable {
    public final int originAirportId;
    public final int destAirportId;
    public final float delayTime;
    public final boolean cancelled;

    private static final int ORIGIN_AIRPORT_ID_CSV_INDEX = 11;
    private static final int DEST_AIRPORT_ID_CSV_INDEX = 14;
    private static final int DELAY_CSV_INDEX = 18;
    private static final int CANCELLATION_CODE_CSV_INDEX = 20;

    private static final String CSV_REPLACE_REGEX = "\"";
    private static final String CSV_REPLACEMENT = "";
    private static final String CSV_SPLIT_REGEX = ",";

    public Flight(int originAirportId, int destAirportId, float delayTime, boolean cancelled) {
        this.originAirportId = originAirportId;
        this.destAirportId = destAirportId;
        this.delayTime = delayTime;
        this.cancelled = cancelled;
    }

    public static Flight parseCSV(final String csv) {
        String[] list = csv.replaceAll(CSV_REPLACE_REGEX, CSV_REPLACEMENT).split(CSV_SPLIT_REGEX);
        return new Flight(
                Integer.parseInt(list[ORIGIN_AIRPORT_ID_CSV_INDEX]),
                Integer.parseInt(list[DEST_AIRPORT_ID_CSV_INDEX]),
                list[DELAY_CSV_INDEX].isEmpty() ? 0 : Float.parseFloat(list[DELAY_CSV_INDEX]),
                list[CANCELLATION_CODE_CSV_INDEX].isEmpty()
        );
    }

    public final Tuple2<Tuple2<Integer, Integer>, Flight> getTupleWithAirports() {
        return new Tuple2<>(new Tuple2<>(originAirportId, destAirportId), this);
    }
}
