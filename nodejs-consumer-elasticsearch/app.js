const { Kafka, logLevel } = require('kafkajs');
const { Client } = require('@elastic/elasticsearch');
const elasticSearchClient = new Client({ node: 'http://es01:9200' });

const kafka = new Kafka({
    logLevel: logLevel.INFO,
    brokers: ['kafka-broker:9092'],
    clientId: 'elastic-search-consumer',
})

const consumer = kafka.consumer({ groupId: 'elastic-search' });

async function start() {
    await consumer.connect();
    await consumer.subscribe({ topic: 'tweets', fromBeginning: true });

    await consumer.run({
        eachMessage: async ({ topic, partition, message }) => {
            const messageJson = JSON.parse(message.value.toString());
            const tweet = {
                id: messageJson.tweet.id,
                text: messageJson.tweet.text
            };
            await elasticSearchClient.index({
                index: 'tweets',
                id: tweet.id,
                body: {
                    text: tweet.text
                }
            })
        },
    })
}

start()
    .catch(e => {
        console.error(e);
    });