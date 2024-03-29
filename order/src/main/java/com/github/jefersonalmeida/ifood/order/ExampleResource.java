package com.github.jefersonalmeida.ifood.order;

import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;

@Path("/hello")
public class ExampleResource {

    private static final Logger LOG = Logger.getLogger(ExampleResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        LOG.info("Olá Quarkus");
        LOG.infov("Olá Quarkus {0},", LocalDateTime.now());
        return "hello";
    }
}
