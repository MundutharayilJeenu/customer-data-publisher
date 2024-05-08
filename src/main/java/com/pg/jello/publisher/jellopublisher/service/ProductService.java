package com.pg.jello.publisher.jellopublisher.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pg.jello.publisher.jellopublisher.bean.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);
    @Value("${stream.key}")
    private String streamKey;

    private final RedisTemplate<String, String> redisTemplate;


//    private final ObjectMapper objectMapper;
//
    public ProductService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;

    }

//    public void publishProduct(Product product) {
//        try {
//            String jsonString = objectMapper.writeValueAsString(product);
//            ObjectRecord<String, String> record = StreamRecords.objectBacked(jsonString).withStreamKey(streamKey);
//            log.info("topic: {}, value: {}", record.getStream(), record.getValue());
//            redisTemplate.opsForStream().add(record);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }

    public RecordId produce(Product product) throws JsonProcessingException{
//        String jsonString = objectMapper.writeValueAsString(product);
        ObjectRecord<String, Product> record = StreamRecords.newRecord().ofObject(product).withStreamKey(streamKey);
        RecordId recordId = this.redisTemplate.opsForStream().add(record);
        if(Objects.isNull(recordId)){
            log.info("error sending event: {}", product);
            return null;
        }
        log.info("topic: {}, value: {}", record.getStream(), record.getValue());
        return recordId;
    }

    }


