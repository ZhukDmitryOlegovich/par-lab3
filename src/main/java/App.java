import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

import java.util.Map;
import java.util.Objects;

public class App {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("lab3");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> dataAirports = sc.textFile("L_AIRPORT_ID.csv");
        String firstDataAirport = dataAirports.first();
        JavaPairRDD<Integer, String> airportsRDD = dataAirports
                .filter(dataAirport -> !Objects.equals(firstDataAirport, dataAirport))
                .mapToPair(dataAirport -> Airport.parseCSV(dataAirport).getTuple());

        final Broadcast<Map<Integer, String>> airportsBroadcaster = sc.broadcast(airportsRDD.collectAsMap());

        JavaRDD<String> dataFlights = sc.textFile("664600583_T_ONTIME_sample.csv");
        String firstDataFlight = dataFlights.first();
        JavaPairRDD<Tuple2<Integer, Integer>, Flight> airportFlights = dataFlights
                .filter(dataFlight -> !Objects.equals(firstDataFlight, dataFlight))
                .mapToPair(dataFlight -> Flight.parseCSV(dataFlight).getTupleWithAirports());

        JavaPairRDD<Tuple2<Integer, Integer>, FlightReduce> flightStatistics = airportFlights.combineByKey(
                FlightReduce::new,
                FlightReduce::accumulate,
                FlightReduce::merge
        );

        flightStatistics.mapToPair(statistics -> new Tuple2<>(
                String.format(
                        "from:\"%83s\",to:\"%83s\"",
                        airportsBroadcaster.value().get(statistics._1._1),
                        airportsBroadcaster.value().get(statistics._1._2)
                ),
                statistics._2.getStatistics()
        )).saveAsTextFile("output");
    }
}
