package com.github.jefersonalmeida.ifood.marketplace;

import io.vertx.mutiny.pgclient.PgPool;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@ApplicationScoped
public class CreateRestaurant {

    @Inject
    PgPool pgPool;

    @Incoming("restaurants")
    public void receiveRestaurant(String json) {
        Jsonb create = JsonbBuilder.create();
        Restaurant restaurant = create.fromJson(json, Restaurant.class);

        System.out.println("-----------------------");
        System.out.println(json);
        System.out.println("-----------------------");
        System.out.println(restaurant);

        restaurant.persist(pgPool);
    }
}
