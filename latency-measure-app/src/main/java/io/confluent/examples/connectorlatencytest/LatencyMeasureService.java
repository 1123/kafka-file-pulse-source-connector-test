package io.confluent.examples.connectorlatencytest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

@Service
@Slf4j
public class LatencyMeasureService {

    private final UUID uuid = UUID.randomUUID();

    @KafkaListener(topics = "EventLog")
    public void measure(String key, String value) {
        // Some garbage characters are padded to the left of the key/value. They need to be removed.
        long produceTimestamp = Long.parseLong(value.substring(7));
        long currentTimestamp = System.currentTimeMillis();
        long latency = currentTimestamp - produceTimestamp;
        log.info("produced: {}, read: {}; latency: {}",
                produceTimestamp,
                currentTimestamp,
                latency
        );
    }

    @Scheduled(fixedDelay = 1000)
    public void writeMessagetoFileSystem() throws IOException {
        log.info("Writing message to file system to be picked up by the connector");
        Files.writeString(
                Path.of("/tmp/kafka-connect/examples/" + uuid + ".log"),
                System.currentTimeMillis() + System.lineSeparator(),
                CREATE, APPEND
        );
    }

}
