package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import models.Message;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaMessageListner implements MessageListner {
    Properties kafkaProps;
    Producer<Long, String> producer;
    String message_topic;
    public KafkaMessageListner(String propertiesFilename) throws IOException {
        InputStream in = KafkaMessageListner.class.getResourceAsStream("/" + propertiesFilename);
        kafkaProps.load(in);
        producer = createProducer();
        message_topic = kafkaProps.getProperty("message_topic");

    }

    private Producer<Long, String> createProducer(){
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProps.getProperty("kafka_broker"));
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProps.getProperty("cliend_id"));
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, NumberSerializers.LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return new KafkaProducer<Long, String>(props);
    }


    public void persist(Message message) throws ExecutionException, InterruptedException {
        ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(message_topic, message.text);
        RecordMetadata meta = producer.send(record).get();
    }

    public byte[] serialize(Message message){
        byte[] msg = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            msg = objectMapper.writeValueAsString(message).getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
//        Properties props = new Properties();
//        InputStream input = KafkaMessageListner.class.getResourceAsStream("/kafka.properties");
//        props.load(input);
//        System.out.println(props.getProperty("client_id"));

        KafkaMessageListner kml = new KafkaMessageListner("kafka.properties");
        kml.persist(new Message("hello world"));

    }
}
