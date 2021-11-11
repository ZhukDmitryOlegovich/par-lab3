import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Objects;

public class App {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> flights = sc.textFile("L_AIRPORT_ID.csv");
        String firstFlight = flights.first();
        flights = flights.filter(e -> firstFlight != e);
        flights.mapToPair(flight -> {
            Flight.parseCSV(flight);
        });

        JavaRDD<String> airports = sc.textFile("664600583_T_ONTIME_sample.csv");
        String firstAirport = airports.first();
        airports = airports.filter(e -> firstAirport != e);
    }
}
