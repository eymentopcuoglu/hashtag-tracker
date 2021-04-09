package com.topcuoglu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.topcuoglu.pojo.Tweet;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class App {

    public static final String INPUT_TOPIC = "tweets";
    public static final String OUTPUT_TOPIC = "tweets-wordcount";

    public static void main(String[] args) {

        Properties properties = getProperties();

        if (properties == null)
            System.exit(-1);

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        createWordCountStream(streamsBuilder);

        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);
        CountDownLatch latch = new CountDownLatch(1);

        Runtime.getRuntime().addShutdownHook(new Thread("tweets-wordcount-shutdown-hook") {
            @Override
            public void run() {
                kafkaStreams.close();
                latch.countDown();
            }
        });

        try {
            kafkaStreams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(5);
        }
        System.exit(0);
    }

    private static Properties getProperties() {

        try (InputStream input = App.class.getResourceAsStream("/kafka-streams.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
            properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
            return properties;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static void createWordCountStream(final StreamsBuilder builder) {
        final KStream<String, String> source = builder.stream(INPUT_TOPIC);

        final KTable<String, Long> counts = source
                .mapValues(App::parseJson)
                .flatMapValues(value -> Arrays.asList(value.getText().toLowerCase(Locale.getDefault()).split(" ")))
                .groupBy((key, value) -> value)
                .count();

        counts.toStream()
                .map((key, value) -> KeyValue.pair(key, value.toString()))
                .to(OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.String()));
    }

    private static Tweet parseJson(String json) {
        System.out.println(json);
        ObjectMapper objectMapper = new ObjectMapper();
        String tweetJSON = null;
        try {
            tweetJSON = objectMapper.readTree(json).get("tweet").toString();
            return objectMapper.readValue(tweetJSON, Tweet.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
