FROM openjdk:11.0.10-jre-slim
ARG JAR_PATH=out/artifacts/tweet_producer_jar/tweet-producer.jar
COPY $JAR_PATH tweet-producer.jar
CMD ["java", "-jar", "tweet-producer.jar"]