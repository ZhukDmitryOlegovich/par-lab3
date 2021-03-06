import scala.Serializable;
import scala.Tuple2;

public class Airport implements Serializable {
    private final int code;
    private final String name;

    private static final int CODE_CSV_INDEX = 0;
    private static final int NAME_CSV_INDEX = 1;

    private static final String CSV_REPLACE_REGEX = "\"";
    private static final String CSV_REPLACEMENT = "";
    private static final String CSV_SPLIT_REGEX = ",";
    private static final int CSV_SPLIT_LIMIT = 2;

    public Airport(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Airport parseCSV(final String csv) {
        String[] list = csv.split(CSV_SPLIT_REGEX, CSV_SPLIT_LIMIT);
        return new Airport(
                Integer.parseInt(list[CODE_CSV_INDEX].replaceAll(CSV_REPLACE_REGEX, CSV_REPLACEMENT)),
                list[NAME_CSV_INDEX]
        );
    }

    public final Tuple2<Integer, String> getTuple() {
        return new Tuple2<>(code, name);
    }
}
