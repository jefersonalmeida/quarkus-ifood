package com.github.jefersonalmeida.ifood.marketplace;

import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;

import java.util.UUID;

public class Restaurant {
    public UUID id;
    public String name;
    public Location location;

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location=" + location +
                '}';
    }

    public void persist(PgPool pgPool) {
        pgPool.preparedQuery(
                "insert into public.locations (id, latitude, longitude) values ($1, $2, $3)"
        ).executeAndAwait(Tuple.of(location.id, location.latitude, location.longitude));

        pgPool.preparedQuery(
                "insert into public.restaurants (id, location_id, name) values ($1, $2, $3)"
        ).executeAndAwait(Tuple.of(id, location.id, name));
    }
}
