package com.redhat;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.kafka.KafkaRecord;

@ApplicationScoped
public class RegistryExampleJSONSchema {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistryExampleJSONSchema.class);

    private Random random = new Random();
    private String[] symbols = new String[] { "RHT", "IBM", "MSFT", "AMZN" };

    @Outgoing("price-out")
    public Flowable<KafkaRecord<String, StockPrice>> generate() throws IOException {
        return Flowable.interval(10, TimeUnit.SECONDS)
                .onBackpressureDrop()
                .map(tick -> {
                    StockPrice message = new StockPrice();
                    message.setSymbol(symbols[random.nextInt(4)]);
                    message.setPrice( random.nextDouble() * -100);
                    LOGGER.info("=====> Sending message {} to topic {}", message, "price-out");
                    return KafkaRecord.of(message.getSymbol(), message);
                });
    }

    @Incoming("price-in")
    public CompletionStage<Void> receive(KafkaRecord<String,StockPrice> record) throws IOException {
        return CompletableFuture.runAsync(() -> {
            LOGGER.info("=====> {}", record.getPayload());
        });
    }
}