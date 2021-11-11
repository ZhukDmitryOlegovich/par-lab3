import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

import java.util.Map;
import java.util.Objects;

public class App {
    private static final String APP_NAME = "lab3";
    private static final String INPUT_AIRPORT_CSV_PATH = "L_AIRPORT_ID.csv";
    private static final String INPUT_FLIGHT_CSV_PATH = "664600583_T_ONTIME_sample.csv";
    private static final String FORMAT = "from:%80s,to:%80s";
    private static final String OUTPUT_PATH = "output";

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName(APP_NAME);
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> dataAirports = sc.textFile(INPUT_AIRPORT_CSV_PATH);
        String firstDataAirport = dataAirports.first();

        final Broadcast<Map<Integer, String>> airportsBroadcaster = sc.broadcast(
                dataAirports
                        .filter(dataAirport -> !Objects.equals(firstDataAirport, dataAirport))
                        .mapToPair(dataAirport -> Airport.parseCSV(dataAirport).getTuple())
                        .collectAsMap()
        );

        JavaRDD<String> dataFlights = sc.textFile(INPUT_FLIGHT_CSV_PATH);
        String firstDataFlight = dataFlights.first();

        dataFlights
                .filter(dataFlight -> !Objects.equals(firstDataFlight, dataFlight))
                .mapToPair(dataFlight -> Flight.parseCSV(dataFlight).getTupleWithAirports())
                .combineByKey(
                        FlightReduce::new,
                        FlightReduce::accumulate,
                        FlightReduce::merge
                )
                .mapToPair(statistics -> new Tuple2<>(
                        statistics._2.getStatistics(),
                        String.format(
                                FORMAT,
                                airportsBroadcaster.value().get(statistics._1._1),
                                airportsBroadcaster.value().get(statistics._1._2)
                        )
                ))
                .saveAsTextFile(OUTPUT_PATH);
    }
}
