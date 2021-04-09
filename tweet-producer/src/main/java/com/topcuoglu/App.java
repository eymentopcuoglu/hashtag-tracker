package com.topcuoglu;

import com.fasterxml.jackson.core.type.TypeReference;
import com.topcuoglu.twitter.Rule;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.topcuoglu.twitter.Tweet;
import com.topcuoglu.twitter.User;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {

        String bearerToken = System.getenv("BEARER_TOKEN");

        if (bearerToken == null || bearerToken.equals("")) {
            System.err.println("BEARER_TOKEN is not set. Exiting...");
            System.exit(-3);
        }

        List<Rule> theRules = getRules(bearerToken);
//        deleteRules(bearerToken, theRules);
//
//        Rule rule = new Rule("#Survivor2021 -is:retweet");
//        createRule(bearerToken, rule);

        Producer<String, String> tweetProducer = setupProducer();

        if (tweetProducer == null) {
            System.exit(-1);
        }

        connectToStream(bearerToken, tweetProducer);
        tweetProducer.close();
    }

    private static Producer<String, String> setupProducer() {

        System.out.println("Setting up the producer...");
        try (InputStream input = App.class.getResourceAsStream("/producer.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            if (properties.isEmpty()) {
                System.err.println("Could not load the properties. Exiting...");
                System.exit(-3);
            }
            System.out.println(properties);
            return new KafkaProducer<>(properties);
        } catch (IOException ex) {
            System.err.println("Error while setting up the producer!");
            ex.printStackTrace();
        }
        return null;
    }

    private static List<Rule> getRules(String bearerToken) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.twitter.com/2/tweets/search/stream/rules"))
                    .header("content-type", "application/json")
                    .headers("Authorization", String.format("Bearer %s", bearerToken))
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        System.out.println("Sending the HTTP request to get the current rules...");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Got the response...");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.body()).get("data");

        if (jsonNode == null) {
            System.out.println("There is no rule!");
            return null;
        } else {
            String rulesJSON = jsonNode.toString();
            List<Rule> theRules = objectMapper.readValue(rulesJSON, new TypeReference<List<Rule>>() {
            });
            System.out.println("Current rules: " + theRules);
            return theRules;
        }
    }

    private static void createRule(String bearerToken, Rule rule) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        String body = "{ \"add\": [ {\"value\": \"" + rule.getValue() + "\"}] }";

        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.twitter.com/2/tweets/search/stream/rules"))
                    .header("content-type", "application/json")
                    .headers("Authorization", String.format("Bearer %s", bearerToken))
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        System.out.println("Sending the HTTP request for creating the rule...");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Got the response...");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.body()).get("data");

        if (jsonNode == null) {
            System.out.println("There is no rule!");
        } else {
            String rulesJSON = jsonNode.asText();
            System.out.println("Before the parse: " + rulesJSON);
            List<Rule> theRules = objectMapper.readValue(rulesJSON, new TypeReference<List<Rule>>() {
            });
            System.out.println(theRules);
        }
    }

    private static void deleteRules(String bearerToken, List<Rule> rules) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.twitter.com/2/tweets/search/stream/rules"))
                    .header("content-type", "application/json")
                    .headers("Authorization", String.format("Bearer %s", bearerToken))
                    .POST(HttpRequest.BodyPublishers.ofString(getFormattedString(rules)))
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        System.out.println("Sending the HTTP request to delete the rules...");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Got the response...");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.body()).get("data");

        if (jsonNode == null) {
            System.out.println("There is no rule!");
        } else {
            String rulesJSON = jsonNode.asText();
            List<Rule> theRules = objectMapper.readValue(rulesJSON, new TypeReference<List<Rule>>() {
            });
            System.out.println(theRules);
        }
    }

    private static void connectToStream(String bearerToken, Producer<String, String> producer) throws IOException,
            InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.twitter.com/2/tweets/search/stream?expansions=author_id&user" +
                            ".fields=created_at,description,verified,profile_image_url,protected," +
                            "public_metrics&tweet.fields=lang,created_at"))
                    .header("content-type", "application/json")
                    .headers("Authorization", String.format("Bearer %s", bearerToken))
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        System.out.println("Sending the HTTP request to connect to stream...");
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.body()));
        String line = reader.readLine();

        ObjectMapper objectMapper = new ObjectMapper();

        while (line != null) {
            System.out.println("DATA: " + line);
            if (!line.equals("")) {
                Map<String, Object> dataToPublish = new HashMap<>();

                JsonNode jsonNode = objectMapper.readTree(line).get("data");
                Tweet tweet = objectMapper.readValue(jsonNode.toString(), Tweet.class);

                dataToPublish.put("tweet", tweet);

                JsonNode userNode = objectMapper.readTree(line).get("includes").get("users");
                if (userNode.isArray()) {
                    for (final JsonNode objNode : userNode) {
                        User user = objectMapper.readValue(objNode.toString(), User.class);
                        dataToPublish.put("user", user);
                    }
                }

                producer.send(new ProducerRecord<String, String>("tweets", String.valueOf(tweet.getId()),
                        objectMapper.writeValueAsString(dataToPublish)));

            }
            line = reader.readLine();
        }

        System.out.println("Got the response...");
    }


    private static String getFormattedString(List<Rule> rules) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{\"delete\": { \"ids\": [");

        for (Rule rule : rules) {
            stringBuilder.append("\"").append(rule.getId()).append("\",");
        }
        String result = stringBuilder.substring(0, stringBuilder.length() - 1);
        return result + "] } }";
    }
}
