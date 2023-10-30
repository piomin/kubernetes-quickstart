package pl.piomin.services.kafka.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.piomin.services.kafka.common.Info;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

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

    AtomicLong id = new AtomicLong();
    @Autowired
    KafkaTemplate<Long, Info> template;

    @Value("${POD:kafka-producer}")
    private String pod;
    @Value("${NAMESPACE:empty}")
    private String namespace;
    @Value("${CLUSTER:localhost}")
    private String cluster;
    @Value("${TOPIC:test}")
    private String topic;

    @Scheduled(fixedRate = 1000)
    public void send() {
        Info info = new Info(id.incrementAndGet(), pod, namespace, cluster, "HELLO");
        CompletableFuture<SendResult<Long, Info>> result = template.send(topic, info.getId(), info);
        result.whenComplete((sr, ex) ->
                LOG.info("Sent({}): {}", sr.getProducerRecord().key(), sr.getProducerRecord().value()));
    }
}
