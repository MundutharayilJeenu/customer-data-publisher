//package com.pg.jello.publisher.jellopublisher.service;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.pg.jello.publisher.jellopublisher.bean.Product;
//import com.pg.jello.publisher.jellopublisher.repository.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.connection.stream.ObjectRecord;
//import org.springframework.data.redis.connection.stream.StreamRecords;
//import org.springframework.data.redis.core.ReactiveRedisTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Service
//public class PurchaseEventProducer {
//
//    private AtomicInteger atomicInteger = new AtomicInteger(0);
//
//    @Value("${stream.key}")
//    private String streamKey;
//
//    @Autowired
//    private ProductRepository repository;
//
//    @Autowired
//    private ReactiveRedisTemplate<String, String> redisTemplate;
//
//    @Scheduled(fixedRateString= "${publish.rate}")
//    public void publishEvent(JsonNode jsonNode){
//        ObjectMapper objectMapper = new ObjectMapper();
//        Product product = this.repository.getRandomProduct();
//        try {
//            ObjectRecord<String, String> record = StreamRecords.newRecord()
//                    .ofObject(objectMapper.writeValueAsString(jsonNode))
//                    .withStreamKey(streamKey);
//
//            this.redisTemplate
//                    .opsForStream()
//                    .add(record)
//                    .subscribe(System.out::println);
//            atomicInteger.incrementAndGet();
//        } catch (Exception exception){
//            System.out.println("Exception:"+ exception.toString());
//        }
//    }
//
//    @Scheduled(fixedRate = 10000)
//    public void showPublishedEventsSoFar(){
//        System.out.println(
//                "Total Events :: " + atomicInteger.get()
//        );
//    }
//
//}