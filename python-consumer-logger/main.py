from confluent_kafka import Consumer

print('Connecting...')
consumer = Consumer({
    'bootstrap.servers': 'kafka-broker:9092',
    'group.id': 'consumer-logger',
    'auto.offset.reset': 'earliest'
})
consumer.subscribe(['tweets-wordcount', 'tweets'])
print('Successfully subscribed to the topics!')

while True:
    msg = consumer.poll(1.0)

    if msg is None:
        continue
    if msg.error():
        print("Consumer error: {}".format(msg.error()))
        continue

    print('Topic: ' + msg.topic() + '\tKey: ' + str(msg.key().decode('utf-8')) + '\tValue ' + str(
        msg.value().decode('utf-8')))

consumer.close()
