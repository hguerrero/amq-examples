package com.redhat.kafkademo;

import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class ProducerApp {

	public static void main(String[] args) throws Exception {

		final Properties props = new Properties();
		props.put("bootstrap.servers", "my-cluster-kafka-bootstrap-myproject.192.168.99.100.nip.io:443");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		props.put("security.protocol", "SSL");
		props.put("ssl.keystore.location", "src/main/resources/keystore.jks");
		props.put("ssl.keystore.password", "password");
		props.put("ssl.truststore.location", "src/main/resources/keystore.jks");
		props.put("ssl.truststore.password", "password");

		try (final Producer<String, String> producer = new KafkaProducer<>(props)) {
			while (true) {
				final String date = new Date().toString();
				System.out.println("Sending message: " + date);
				producer.send(new ProducerRecord<>("my-topic", "date", date));
				Thread.sleep(2000);
			}
		}
	}

}