public class Flight {
    private final int code;
    private final float delay;

    private static final int DEST_AIRPORT_ID_CSV_INDEX = 14;
    private static final int DELAY_CSV_INDEX = 18;

    private static final String CSV_REPLACE_REGEX = "\"";
    private static final String CSV_REPLACEMENT = "";
    private static final String CSV_SPLIT_REGEX = ",";

    public Flight(int code, float delay) {
        this.code = code;
        this.delay = delay;
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
                Integer.parseInt(list[DEST_AIRPORT_ID_CSV_INDEX]),
                list[DELAY_CSV_INDEX].length() > 0 ? Float.parseFloat(list[DELAY_CSV_INDEX]) : 0
        );
    }
}
