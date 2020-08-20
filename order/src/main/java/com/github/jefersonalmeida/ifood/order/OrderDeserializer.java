package com.github.jefersonalmeida.ifood.order;

import com.github.jefersonalmeida.ifood.order.dto.OrderCompletedDTO;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class OrderDeserializer extends ObjectMapperDeserializer<OrderCompletedDTO> {

    public OrderDeserializer() {
        super(OrderCompletedDTO.class);
    }
}
