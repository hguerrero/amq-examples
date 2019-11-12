package com.redhat.kafkastreams.producer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.Flowable;
import io.smallrye.reactive.messaging.kafka.KafkaMessage;
import io.vertx.core.json.Json;

@ApplicationScoped
public class VehicleGenerator 
{
    private static final Logger LOG = LoggerFactory.getLogger(VehicleGenerator.class);

    private AtomicLong vehicleIds = new AtomicLong();

    @Outgoing("uber")
    public Flowable<KafkaMessage<String, String>> generateUber() 
    {
        return Flowable.interval(500, TimeUnit.MILLISECONDS)
                .onBackpressureDrop()
                .map(tick -> {
                    VehicleInfo v = randomVehicle();
                    v.setProvider("Uber");
                    LOG.info("dispatching vehicle: {}", v);
                    return KafkaMessage.of(null, Json.encode(v));
                });
    }

    @Outgoing("lyft")
    public Flowable<KafkaMessage<String, String>> generateLyft() 
    {
        return Flowable.interval(750, TimeUnit.MILLISECONDS)
                .onBackpressureDrop()
                .map(tick -> {
                    VehicleInfo v = randomVehicle();
                    v.setProvider("Lyft");
                    LOG.info("dispatching vehicle: {}", v);
                    return KafkaMessage.of(null, Json.encode(v));
                });
    }
    
    public VehicleInfo randomVehicle() 
    {
        VehicleInfo info = new VehicleInfo();
        info.setAvailableSpace(2 + (int)(Math.random() * 7)); // 2 - 8
        info.setPricePerMinute(1.0 + Math.random() * 9); // 1.0 - 9.99
        info.setTimeToPickup(1 + (long)(Math.random() * 21)); // 1 - 20
        info.setVehicleId(vehicleIds.incrementAndGet());
        info.setAvailable(true);

        return info;
    }

    private static class VehicleInfo {

        @JsonIgnore
        private String provider;
        private long vehicleId;
        private double pricePerMinute;
        private long timeToPickup;
        private int availableSpace;
        private boolean available;

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public long getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(long vehicleId) {
            this.vehicleId = vehicleId;
        }

        public double getPricePerMinute() {
            return pricePerMinute;
        }

        public void setPricePerMinute(double pricePerMinute) {
            this.pricePerMinute = pricePerMinute;
        }

        public long getTimeToPickup() {
            return timeToPickup;
        }

        public void setTimeToPickup(long timeToPickup) {
            this.timeToPickup = timeToPickup;
        }

        public int getAvailableSpace() {
            return availableSpace;
        }

        public void setAvailableSpace(int availableSpace) {
            this.availableSpace = availableSpace;
        }

        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        @Override
        public String toString() {
            return "VehicleInfo{" +
                    "provider='" + getProvider() + '\'' +
                    ", vehicleId=" + getVehicleId() +
                    ", pricePerMinute=" + getPricePerMinute() +
                    ", timeToPickup=" + getTimeToPickup() +
                    ", availableSpace=" + getAvailableSpace() +
                    ", available=" + isAvailable() +
                    '}';
        }
    }
}