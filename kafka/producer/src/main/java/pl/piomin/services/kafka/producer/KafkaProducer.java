package pl.piomin.services.kafka.producer;

import io.micrometer.common.KeyValues;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import io.opentelemetry.exporter.otlp.http.trace.OtlpHttpSpanExporter;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.micrometer.KafkaRecordSenderContext;
import org.springframework.kafka.support.micrometer.KafkaTemplateObservationConvention;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.piomin.services.kafka.common.Info;

import java.util.HashMap;
import java.util.Map;
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
                .partitions(1)
                .replicas(1)
                .build();
    }

//    @Bean
//    OtlpHttpSpanExporter otlpHttpSpanExporter(@Value("${management.otlp.tracing.endpoint}") String url) {
//        return OtlpHttpSpanExporter.builder()
//                .setEndpoint(url)
//                .setCompression("gzip")
//                .build();
//    }

    @Bean
    public KafkaTemplate<Long, Info> kafkaTemplate(ProducerFactory<Long, Info> producerFactory) {
        KafkaTemplate<Long, Info> t = new KafkaTemplate<>(producerFactory);
        t.setObservationEnabled(true);
        t.setObservationConvention(new KafkaTemplateObservationConvention() {
            @Override
            public KeyValues getLowCardinalityKeyValues(KafkaRecordSenderContext context) {
                return KeyValues.of("topic", context.getDestination(),
                        "id", String.valueOf(context.getRecord().key()));
            }
        });
        return t;
    }

}
