import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Objects;

public class App {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> dataFlights = sc.textFile("L_AIRPORT_ID.csv");
        String firstDataFlight = dataFlights.first();
        JavaPairRDD<Tuple2<Integer, Integer>, Flight> airportFlights = dataFlights
                .filter(dataFlight -> firstDataFlight != dataFlight)
                .mapToPair(dataFlight -> Flight.parseCSV(dataFlight).getAirports());

        JavaRDD<String> airports = sc.textFile("664600583_T_ONTIME_sample.csv");
        String firstAirport = airports.first();
        airports = airports.filter(e -> firstAirport != e);
    }
}
