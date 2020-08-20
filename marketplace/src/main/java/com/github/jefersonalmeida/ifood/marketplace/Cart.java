package com.github.jefersonalmeida.ifood.marketplace;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cart {
    public String client;
    public UUID item;

    public static Uni<UUID> save(PgPool pgPool, String client, UUID item) {
        return pgPool
                .preparedQuery("insert into public.items_clients (item, client) values ($1, $2) returning (item)")
                .execute(Tuple.of(item, client))
                .map(pgRowSet -> pgRowSet.iterator().next().getUUID("item"));
    }

    public static Uni<List<Cart>> find(PgPool pgPool, String client) {
        return pgPool.preparedQuery("select * from public.items_clients where client = $1")
                .execute(Tuple.of(client))
                .map(pgRowSet -> {
                    List<Cart> list = new ArrayList<>(pgRowSet.size());
                    for (Row row : pgRowSet) {
                        list.add(toCart(row));
                    }
                    return list;
                });
    }

    private static Cart toCart(Row row) {
        Cart pc = new Cart();
        pc.client = row.getString("client");
        pc.item = row.getUUID("item");
        return pc;
    }

    public static Uni<Boolean> delete(PgPool pgPool, String client) {
        return pgPool.preparedQuery("delete from public.items_clients WHERE client = $1")
                .execute(Tuple.of(client))
                .map(pgRowSet -> pgRowSet.rowCount() >= 1);

    }
}
