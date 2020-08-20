package com.github.jefersonalmeida.ifood.marketplace;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemResource {

    @Inject
    PgPool pgPool;

    @GET
    /*@APIResponse(
            responseCode = "200",
            content = @Content(
                    schema = @Schema(
                            type = SchemaType.ARRAY,
                            implementation = ItemDTO.class
                    )
            )
    )*/
    public Multi<ItemDTO> index() {
        return Item.findAll(pgPool);
    }
}
