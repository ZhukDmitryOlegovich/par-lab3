mvn package
hadoop fs -rm -f -r output
# export HADOOP_CLASSPATH=./target/hadoop-examples-1.0-SNAPSHOT.jar
spark-submit --class App --master yarn-client --num-executors 3 ./target/spark-examples-1.0-SNAPSHOT.jar # hadoop App L_AIRPORT_ID.csv 664600583_T_ONTIME_sample.csv output
rm -rf output
hadoop fs -copyToLocal output
