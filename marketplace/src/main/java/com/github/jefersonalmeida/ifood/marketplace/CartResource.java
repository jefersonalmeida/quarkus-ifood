package com.github.jefersonalmeida.ifood.marketplace;

import com.github.jefersonalmeida.ifood.marketplace.dto.ItemDTO;
import com.github.jefersonalmeida.ifood.marketplace.dto.ItemOrderDTO;
import com.github.jefersonalmeida.ifood.marketplace.dto.OrderCompletedDTO;
import com.github.jefersonalmeida.ifood.marketplace.dto.RestaurantDTO;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Path(value = "cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    private static final String CLIENT = "a";

    @Inject
    io.vertx.mutiny.pgclient.PgPool pgPool;

    @Inject
    @Channel("orders")
    Emitter<OrderCompletedDTO> emitterOrder;

    @GET
    public Uni<List<Cart>> index() {
        return Cart.find(pgPool, CLIENT);
    }

    @POST
    @Path("/item/{itemId}")
    public Uni<UUID> addItem(@PathParam("itemId") UUID itemId) {
        //poderia retornar o pedido atual
        Cart pc = new Cart();
        pc.client = CLIENT;
        pc.item = itemId;
        return Cart.save(pgPool, CLIENT, itemId);
    }

    @POST
    @Path("place-order")
    public Uni<Boolean> finish() {
        OrderCompletedDTO pedido = new OrderCompletedDTO();
        String client = CLIENT;
        pedido.client = client;
        List<Cart> cart = Cart.find(this.pgPool, client).await().indefinitely();
        //Utilizar mapstruts
        pedido.items = cart.stream().map(this::from).collect(Collectors.toList());

        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.name = "nome restaurante";
        pedido.restaurant = restaurantDTO;
        emitterOrder.send(pedido);
        return Cart.delete(this.pgPool, client);
    }

    private ItemOrderDTO from(Cart cart) {
        ItemDTO dto = Item.findById(pgPool, cart.item).await().indefinitely();
        return new ItemOrderDTO(dto.name, dto.description, dto.price);
    }

}
