package com.github.jefersonalmeida.ifood.order.dto;

import java.util.List;

public class OrderCompletedDTO {
    public List<ItemOrderDTO> items;
    public RestaurantDTO restaurant;
    public String client;
}
