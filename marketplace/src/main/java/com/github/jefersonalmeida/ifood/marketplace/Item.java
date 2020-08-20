package com.github.jefersonalmeida.ifood.marketplace;

import com.github.jefersonalmeida.ifood.marketplace.dto.ItemDTO;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.StreamSupport;

public class Item {
    public UUID id;
    public String name;
    public String description;
    public Restaurant restaurant;
    public BigDecimal price;

    public static Multi<ItemDTO> findAll(PgPool pgPool) {
        Uni<RowSet<Row>> execute = pgPool.preparedQuery("select * from public.items").execute();

        return unitToMulti(execute);
    }

    private static Multi<ItemDTO> unitToMulti(Uni<RowSet<Row>> execute) {
        return execute.onItem().produceMulti(rowSet -> Multi.createFrom().items(
                () -> StreamSupport.stream(rowSet.spliterator(), false)
        )).onItem().apply(ItemDTO::from);
    }

    public static Multi<ItemDTO> findAll(PgPool pgPool, UUID restaurantId) {
        Uni<RowSet<Row>> execute = pgPool.preparedQuery(
                "select * from public.items where restaurant_id = $1 order by name"
        ).execute(Tuple.of(restaurantId));

        return unitToMulti(execute);
    }

    public static Uni<ItemDTO> findById(PgPool pgPool, UUID id) {
        return pgPool.preparedQuery("select * from public.items where id = $1")
                .execute(Tuple.of(id))
                .map(RowSet::iterator)
                .map(iterator -> iterator.hasNext()
                        ? ItemDTO.from(iterator.next())
                        : null
                );
    }
}
