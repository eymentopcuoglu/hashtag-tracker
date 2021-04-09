from builtins import print

from confluent_kafka import Consumer
import schedule
import xlsxwriter

FILE_GENERATION_INTERVAL = 60
messages = {}

print('Connecting...')
consumer = Consumer({
    'bootstrap.servers': 'kafka-broker:9092',
    'group.id': 'top-words',
    'auto.offset.reset': 'earliest'
})
consumer.subscribe(['tweets-wordcount'])
print('Successfully subscribed to the topic!')

def write_to_excel():
    print('Writing to excel file')
    list_of_messages = []
    for key, value in messages.items():
        if key == '':
            continue
        list_of_messages.append({
            'text': key,
            'count': value
        })
    list_of_messages.sort(reverse=True, key=lambda e: e.get('count'))

    workbook = xlsxwriter.Workbook('./output/word-count.xlsx')
    worksheet = workbook.add_worksheet()

    row = 0
    col = 0

    for item in list_of_messages:
        worksheet.write(row, col, item['text'])
        worksheet.write(row, col + 1, item['count'])
        row += 1

    workbook.close()


schedule.every(FILE_GENERATION_INTERVAL).seconds.do(write_to_excel)

while True:
    schedule.run_pending()
    msg = consumer.poll(1.0)

    if msg is None:
        continue
    if msg.error():
        print("Consumer error: {}".format(msg.error()))
        continue

    messages[msg.key().decode('utf-8')] = int(msg.value())

consumer.close()
