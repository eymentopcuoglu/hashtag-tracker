FROM python:3.8-slim-buster
WORKDIR /app
RUN pip3 install confluent-kafka
RUN pip3 install schedule
RUN pip3 install XlsxWriter
RUN mkdir output
COPY . .
CMD [ "python3", "-u", "main.py"]