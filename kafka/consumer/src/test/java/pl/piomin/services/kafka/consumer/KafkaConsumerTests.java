package pl.piomin.services.kafka.consumer;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;
import pl.piomin.services.kafka.common.Info;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootTest
@EmbeddedKafka(topics = "info",
        partitions = 10,
        bootstrapServersProperty = "spring.kafka.bootstrap-servers")
@ActiveProfiles("test")
public class KafkaConsumerTests {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaConsumerTests.class);

    @Autowired
    private EmbeddedKafkaBroker kafka;
    @Autowired
    private KafkaTemplate<Long, Info> template;

    @Test
    void eventReceive() throws ExecutionException, InterruptedException, TimeoutException {
        Info info = new Info(1L, "", "", "", "");
        SendResult<Long, Info> r = template.send("info", info.getId(), info)
                .get(1000, TimeUnit.MILLISECONDS);
        LOG.info("Sent: {}", r.getProducerRecord().value());
    }

    @TestConfiguration
    static class KafkaTestConfig {
        @Bean
        public ProducerFactory<Long, Info> producerFactory(EmbeddedKafkaBroker embeddedKafka) {
            Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafka);
            producerProps.put("key.serializer", "org.apache.kafka.common.serialization.LongSerializer");
            producerProps.put("value.serializer", "org.springframework.kafka.support.serializer.JsonSerializer");
            producerProps.put("spring.json.trusted.packages", "*");
            return new DefaultKafkaProducerFactory<>(producerProps);
        }

        @Bean
        public KafkaTemplate<Long, Info> kafkaTemplate(ProducerFactory<Long, Info> producerFactory) {
            return new KafkaTemplate<>(producerFactory);
        }
    }
}
