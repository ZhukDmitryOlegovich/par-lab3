public class Flight {
    private final int originAirportId;
    private final int destAirportId;
    private final float delayTime;
    private final boolean cancelled;

    private static final int ORIGIN_AIRPORT_ID_CSV_INDEX = 11;
    private static final int DEST_AIRPORT_ID_CSV_INDEX = 14;
    private static final int DELAY_CSV_INDEX = 18;
    private static final int CANCELLED_CSV_INDEX = 19;

    private static final String CSV_REPLACE_REGEX = "\"";
    private static final String CSV_REPLACEMENT = "";
    private static final String CSV_SPLIT_REGEX = ",";

    public Flight(int originAirportId, int destAirportId, float delayTime, boolean cancelled) {
        this.originAirportId = originAirportId;
        this.destAirportId = destAirportId;
        this.delayTime = delayTime;
        this.cancelled = cancelled;
    }

    public float getDelay() {
        return delay;
    }

    public int getCode() {
        return code;
    }

    public static Flight parseCSV(String csv) {
        String[] list = csv.replaceAll(CSV_REPLACE_REGEX, CSV_REPLACEMENT).split(CSV_SPLIT_REGEX);
        return new Flight(
                list[CANCELLED_CSV_INDEX].isEmpty(),
                list[DELAY_CSV_INDEX].length() > 0 ? Float.parseFloat(list[DELAY_CSV_INDEX]) : 0
        );
    }
}
