# par-lab3

<!-- 1. Инициализируем Spark
   ```java
   SparkConf conf = new SparkConf().setAppName("lab5");
   JavaSparkContext sc = new JavaSparkContext(conf);
   ``` -->
<!-- 2. Загружаем исходные наборы данных в RDD с помощью метода `JavaSparkContext.textFile` -->
<!-- 3. Преобразуем RDD в RDD пару ключ значение с помощью метода `mapToPair` -->
<!-- 4. Создаем Java объекты для хранения данных — простые объекты реализующие интерфейс `Serializable` -->
<!-- 5. В качестве ключа для пары аэропортов используем класс `Tuple2` c помощью функции `reduce` или аналогичных расчитываем максимальное время опоздания, процент опоздавших + отмененных рейсов -->
<!-- 6. Для связывания с таблицей аэропортов — предварительно выкачиваем список аэропортов в главную функцию с помощью метода `collectAsMap` -->
<!-- 7. Создаем в основном методе main переменную broadcast
```java
final Broadcast<Map<String, AirportData>> airportsBroadcasted =
sc.broadcast(stringAirportDataMap);
``` -->
8. В методе map преобразуем итоговый RDD содержащий статистические данные — обогащаем его именами аэропортов, обращаясь внутри функций к объекту `airportsBroadcasted.value()`
