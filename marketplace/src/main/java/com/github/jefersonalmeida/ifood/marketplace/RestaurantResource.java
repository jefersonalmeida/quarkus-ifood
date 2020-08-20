package com.github.jefersonalmeida.ifood.marketplace;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.UUID;

@Path("restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RestaurantResource {

    @Inject
    PgPool pgPool;

    @GET
    @Path(value = "{restaurantId}/items")
    public Multi<ItemDTO> index(@PathParam("restaurantId") UUID restaurantId) {
        return Item.findAll(pgPool, restaurantId);
    }
}
