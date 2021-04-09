FROM python:3.8-slim-buster
WORKDIR /app
RUN pip3 install confluent-kafka
COPY . .
CMD [ "python3", "-u", "main.py"]