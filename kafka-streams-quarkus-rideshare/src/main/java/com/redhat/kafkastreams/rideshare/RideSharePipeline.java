package com.redhat.kafkastreams.rideshare;

import java.time.Duration;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class RideSharePipeline {
    private static final Logger log = LoggerFactory.getLogger(RideSharePipeline.class);

    final Serde<Transport> transportSerde = new Transport();

    @Produces
    public Topology buildTopology() {
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> uber = builder.stream("uber", Consumed.with(Serdes.String(), Serdes.String()));

        KTable<Windowed<Integer>, Transport> uberCost = 
        uber.map((key, value) -> {
            Transport transport = Transport.parse(value);
            transport.setCompany("uber");
            return KeyValue.pair(transport.getAvailableSpace(), transport);
        })
        .groupByKey(
            Grouped.with(Serdes.Integer(), transportSerde)
        )
        .windowedBy(
            TimeWindows.of(Duration.ofMinutes(1))
        )
        .reduce(
            (aggValue, newValue) -> newValue
        )
        ;
        uberCost
        .toStream()
        .peek(
            (key, value) -> log.info("Uber => Spaces:" + key + " Cost: " + String.format("$ %3d", value.getCost()) + " USD")
        );

        // KStream<String, String> lyft = builder.stream("lyft", Consumed.with(Serdes.String(), Serdes.String()));

        // KTable<Windowed<Integer>, Transport> lyftCost = lyft.map((key, value) -> {
        //     Transport transport = Transport.parse(value);
		// 	transport.setCompany("lyft");
        //     return KeyValue.pair(transport.getAvailableSpace(), transport);
        // }).groupByKey(
        //     Grouped.with(Serdes.Integer(), transportSerde)
        // )
        // .windowedBy(
        //     TimeWindows.of(Duration.ofMinutes(1))
        // )
        // .reduce(
		// 	(aggValue, newValue) -> newValue
        // );
        // // lyftCost.toStream()
        // // .peek( 
		// // 	(key, value) -> log.info("Lyft => Spaces:" + key + " Cost: " + String.format("$ %3d", value.getCost()) + " USD")
        // // );
        
        // KTable<Windowed<Integer>, Transport> rides = uberCost.leftJoin(
		// 	lyftCost,
		// 	new ValueJoiner<Transport, Transport, Transport>() {
		// 		@Override
		// 		public Transport apply(Transport leftValue, Transport rightValue) {
		// 		  if (leftValue == null) return new Transport("no transport",0L,0,0,false);
        //           if (rightValue == null) return leftValue;
		// 		  return leftValue.getCost() > rightValue.getCost() ? rightValue : leftValue;
		// 		}
		// 	}
		// );
		// rides.toStream().peek( 
		// 	(key, value) -> log.info("Cheapest Ride => Spaces:" + key.key() + " Company: " + value.getCompany() + " Cost: " + String.format("$ %3d", value.getCost()) + " USD")
		// );


        return builder.build();
    }

}
