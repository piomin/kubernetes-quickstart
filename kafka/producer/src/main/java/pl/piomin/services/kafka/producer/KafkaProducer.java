package pl.piomin.services.kafka.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.piomin.services.kafka.producer.message.Info;

@SpringBootApplication
@EnableScheduling
public class KafkaProducer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);

    public static void main(String[] args) {
        SpringApplication.run(KafkaProducer.class, args);
    }

    @Bean
    public NewTopic infoTopic() {
        return TopicBuilder.name("info")
                .partitions(10)
                .replicas(3)
                .build();
    }

    @Autowired
    KafkaTemplate<Long, Info> template;

    @Scheduled(fixedRate = 1000)
    public void send() {
        System.out.println(
                "Fixed rate task - " + System.currentTimeMillis() / 1000);
    }
}
