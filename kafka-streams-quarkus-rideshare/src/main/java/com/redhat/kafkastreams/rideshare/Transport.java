package com.redhat.kafkastreams.rideshare;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Transport implements Serializer<Transport>, Deserializer<Transport>, Serde<Transport> {

    static Logger log = LoggerFactory.getLogger(Transport.class);

	static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
		.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    String company;
    Long pricePerMinute;
    Integer availableSpace;
    Integer timeToPickup;
    Boolean available;

    public Transport() {}

	public Transport(
        String company, 
        Long pricePerMinute,
        Integer timeToPickup,
        Integer availableSpace, 
        Boolean available) {
        this.company = company;
        this.pricePerMinute = pricePerMinute;
        this.availableSpace = availableSpace;
        this.timeToPickup = timeToPickup;
        this.available = available;
    }

    @Override
	public String toString() {
		try {
			return OBJECT_MAPPER.writeValueAsString(this);
		} catch (Exception e) {
			log.error("could not serialize", e);
		}
		return super.toString();
    }
    
    public static Transport parse(String json) {
		try {
			return OBJECT_MAPPER.readValue(json, Transport.class);
		} catch (Exception e) {
			log.error("could not parse", e);
		}
		return new Transport("No Company",0L,0,0,false);
	}

    @Override
    public Serializer<Transport> serializer() {
        return this;
    }

    @Override
    public Deserializer<Transport> deserializer() {
        return this;
    }

    @Override
    public Transport deserialize(String topic, byte[] data) {
        if (data == null) {
			return null;
		}
		try {
			return OBJECT_MAPPER.readValue(data, Transport.class);
		} catch (final IOException e) {
			throw new SerializationException(e);
        }
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override
    public byte[] serialize(String topic, Transport data) {
		if (data == null) {
			return null;
		}
		try {
			return OBJECT_MAPPER.writeValueAsBytes(data);
		} catch (final Exception e) {
			throw new SerializationException("Error serializing JSON message", e);
		}
    }

    @Override
    public void close() {

    }

    /**
     * @return the company
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company the company to set
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return the cost
     */
    public Long getCost() {
        return pricePerMinute * timeToPickup;
    }

    /**
     * @return the availableSpace
     */
    public Integer getAvailableSpace() {
        return availableSpace;
    }

    /**
     * @param availableSpace the availableSpace to set
     */
    public void setAvailableSpace(Integer availableSpace) {
        this.availableSpace = availableSpace;
    }

    /**
     * @return the timeToPickup
     */
    public Integer getTimeToPickup() {
        return timeToPickup;
    }

    /**
     * @param timeToPickup the timeToPickup to set
     */
    public void setTimeToPickup(Integer timeToPickup) {
        this.timeToPickup = timeToPickup;
    }

    /**
     * @return the isAvailable
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * @param isAvailable the isAvailable to set
     */
    public void setAvailable(Boolean isAvailable) {
        this.available = isAvailable;
    }

    /**
     * @return the pricePerMinute
     */
    public Long getPricePerMinute() {
        return pricePerMinute;
    }

    /**
     * @param pricePerMinute the pricePerMinute to set
     */
    public void setPricePerMinute(Long pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }

}