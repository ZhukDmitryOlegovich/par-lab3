import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Objects;

public class App {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> dataFlights = sc.textFile("L_AIRPORT_ID.csv");
        String firstFlight = dataFlights.first();
        dataFlights = dataFlights.filter(e -> firstFlight != e);
        dataFlights.mapToPair(dataFlight -> {
            Flight flight = Flight.parseCSV(dataFlight);
            return new Tuple2<>(
                    new Tuple2<>(flight.getOriginAirportId(), flight.getDestAirportId()),
                    flight
            );
        });

        JavaRDD<String> airports = sc.textFile("664600583_T_ONTIME_sample.csv");
        String firstAirport = airports.first();
        airports = airports.filter(e -> firstAirport != e);
    }
}
