import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Map;

public class App {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> dataFlights = sc.textFile("L_AIRPORT_ID.csv");
        String firstDataFlight = dataFlights.first();
        JavaPairRDD<Tuple2<Integer, Integer>, Flight> airportFlights = dataFlights
                .filter(dataFlight -> firstDataFlight != dataFlight)
                .mapToPair(dataFlight -> Flight.parseCSV(dataFlight).getTupleWithAirports());

        JavaPairRDD<Tuple2<Integer, Integer>, FlightReduce> statistic = airportFlights.combineByKey(
                FlightReduce::new,
                FlightReduce::accumulate,
                FlightReduce::merge
        );

        JavaRDD<String> dataAirports = sc.textFile("664600583_T_ONTIME_sample.csv");
        String firstDataAirport = dataAirports.first();
        JavaPairRDD<Integer, String> airports = dataAirports
                .filter(dataAirport -> firstDataAirport != dataAirport)
                .mapToPair(dataAirport -> Airport.parseCSV(dataAirport).getTuple());

        Map<Integer, String> airportNameIdMap = airports.collectAsMap();
    }
}
