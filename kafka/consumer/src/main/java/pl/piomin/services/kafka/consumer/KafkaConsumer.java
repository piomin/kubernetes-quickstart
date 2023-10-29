package pl.piomin.services.kafka.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class KafkaConsumer {

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumer.class, args);
    }

}
