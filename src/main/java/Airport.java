public class Airport {
    private final int code;
    private final String name;

    private static final int CODE_CSV_INDEX = 0;
    private static final int NAME_CSV_INDEX = 1;

    private static final String CSV_REPLACE_REGEX = "\"";
    private static final String CSV_REPLACEMENT = "";
    private static final String CSV_SPLIT_REGEX = ",";
    private static final int CSV_SPLIT_LIMIT = 2;

    Airport(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static Airport parseCSV(String csv) {
        String[] list = csv.replaceAll(CSV_REPLACE_REGEX, CSV_REPLACEMENT).split(CSV_SPLIT_REGEX, CSV_SPLIT_LIMIT);
        return new Airport(Integer.parseInt(list[CODE_CSV_INDEX]), list[NAME_CSV_INDEX]);
    }
}
