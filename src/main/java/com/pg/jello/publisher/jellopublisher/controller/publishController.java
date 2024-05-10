package com.pg.jello.publisher.jellopublisher.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pg.jello.publisher.jellopublisher.bean.Product;
import com.pg.jello.publisher.jellopublisher.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class publishController {

    @Autowired
    ProductService productService;

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/ping",method = RequestMethod.GET)
    public String pingService(){
        return "pong";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public void publishMessageToStream(@RequestBody Product product) throws JsonProcessingException {
        productService.produce(product);
    }
}
