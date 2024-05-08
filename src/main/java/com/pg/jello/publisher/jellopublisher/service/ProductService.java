package com.pg.jello.publisher.jellopublisher.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pg.jello.publisher.jellopublisher.bean.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final Logger log = LoggerFactory.getLogger(ProductService.class);
    @Value("${stream.key}")
    private String streamKey;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public ProductService(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishProduct(Product product) {
        try {
            String jsonString = objectMapper.writeValueAsString(product);
            ObjectRecord<String, String> record = StreamRecords.objectBacked(jsonString).withStreamKey(streamKey);
            log.info("topic: {}, value: {}", record.getStream(), record.getValue());
            redisTemplate.opsForStream().add(record);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    }


