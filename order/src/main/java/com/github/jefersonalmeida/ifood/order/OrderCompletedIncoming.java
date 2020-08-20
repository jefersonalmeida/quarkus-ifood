package com.github.jefersonalmeida.ifood.order;

import com.github.jefersonalmeida.ifood.order.dto.ItemOrderDTO;
import com.github.jefersonalmeida.ifood.order.dto.OrderCompletedDTO;
import org.bson.types.Decimal128;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;

@ApplicationScoped
public class OrderCompletedIncoming {

    @Incoming("orders")
    public void readOrders(OrderCompletedDTO dto) {
        System.out.println("-----------------------------");
        System.out.println("PEDIDO CONSUMIDO - " + dto);
        System.out.println("-----------------------------");

        Order order = new Order();
        order.client = dto.client;
        order.items = new ArrayList<>();
        dto.items.forEach(i -> order.items.add(from(i)));

        Restaurant restaurant = new Restaurant();
        restaurant.name = dto.restaurant.name;

        order.restaurant = restaurant;
        order.persist();
    }

    private Item from(ItemOrderDTO i) {
        Item item = new Item();
        item.name = i.name;
        item.description = i.description;
        item.price = new Decimal128(i.price);
        return item;
    }
}
