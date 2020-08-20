package com.github.jefersonalmeida.ifood.order;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.PanacheMongoEntity;

import java.util.List;

@MongoEntity(collection = "orders", database = "orders")
public class Order extends PanacheMongoEntity {

    public String client;
    public String restaurant;
    public List<Item> items;
    public String deliveryman;
    public Location location;
}
