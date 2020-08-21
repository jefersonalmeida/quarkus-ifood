package com.github.jefersonalmeida.ifood.order;

import io.vertx.core.Vertx;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.bson.types.ObjectId;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path(value = "/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    Vertx vertx;

    @Inject
    EventBus eventBus;

    void startup(@Observes Router router) {
        router.route("/locations*").handler(locationHandler());
    }

    private SockJSHandler locationHandler() {
        SockJSHandler handler = SockJSHandler.create(vertx);

        PermittedOptions permitted = new PermittedOptions();
        permitted.setAddress("newLocation");
        SockJSBridgeOptions bridgeOptions = new SockJSBridgeOptions().addOutboundPermitted(permitted);
        handler.bridge(bridgeOptions);

        return handler;
    }

    @GET
    public List<Order> index() {
        return Order.listAll();
    }

    @POST
    @Path(value = "{orderId}/location")
    public Order newLocation(@PathParam("orderId") String orderId, Location location) {
        Order order = Order.findById(new ObjectId(orderId));

        order.location = location;

        String json = JsonbBuilder.create().toJson(location);
        eventBus.sendAndForget("newLocation", json);

        order.persistOrUpdate();
        return order;
    }
}
