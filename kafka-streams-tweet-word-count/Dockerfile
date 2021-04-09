FROM openjdk:11.0.10-jre-slim
ARG JAR_PATH=out/artifacts/kafka_streams_tweet_word_count_jar/kafka-streams-tweet-word-count.jar
COPY $JAR_PATH kafka-streams-tweet-word-count.jar
CMD ["java", "-jar", "kafka-streams-tweet-word-count.jar"]