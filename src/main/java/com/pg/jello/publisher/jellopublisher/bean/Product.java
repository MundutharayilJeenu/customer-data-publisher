package com.pg.jello.publisher.jellopublisher.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String name;
    private double price;
    private String category;

}
