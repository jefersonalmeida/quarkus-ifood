package com.github.jefersonalmeida.ifood.register;

import com.github.jefersonalmeida.ifood.register.dto.*;
import com.github.jefersonalmeida.ifood.register.infra.ConstraintViolationResponse;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "restaurant")
@RolesAllowed({"RestaurantOwner"})
@SecurityScheme(securitySchemeName = "ifood_oauth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/ifood/protocol/openid-connect/token")))
public class RestaurantResource {

    @Inject
    RestaurantMapper restaurantMapper;

    @Inject
    ItemMapper itemMapper;

    @Inject
    @Channel("restaurants")
    Emitter<String> emitter;

    @GET
    @Counted(name = "Quantidade de buscas por Restaurantes")
    @SimplyTimed(name = "Tempo simples de busca")
    @Timed(name = "Tempo completo de busca")
    public List<RestaurantDTO> index() {
        Stream<Restaurant> restaurants = Restaurant.streamAll();
        return restaurants.map(r -> restaurantMapper.toDTO(r)).collect(Collectors.toList());
    }

    private Restaurant findRestaurant(UUID restaurantId) {
        Optional<Restaurant> restaurant = Restaurant.findByIdOptional(restaurantId);
        return restaurant.orElseThrow(NotFoundException::new);
    }

    private Item findMenu(UUID itemId) {
        Optional<Item> menu = Item.findByIdOptional(itemId);
        return menu.orElseThrow(NotFoundException::new);
    }

    @POST
    @Transactional
    @APIResponse(
            responseCode = "201",
            description = "Caso restaurante seja cadastrado com sucesso",
            content = @Content(schema = @Schema(allOf = RestaurantDTO.class))
    )
    @APIResponse(
            responseCode = "400",
            description = "Caso haja algum erro na validação dos dados",
            content = @Content(schema = @Schema(allOf = ConstraintViolationResponse.class))
    )
    public RestaurantDTO create(@Valid CreateRestaurantDTO dto) {
        Restaurant restaurant = restaurantMapper.toEntity(dto);
        restaurant.persist();

        Jsonb jsonb = JsonbBuilder.create();
        String json = jsonb.toJson(restaurant);
        emitter.send(json);

        return restaurantMapper.toDTO(restaurant);
    }

    @PUT
    @Path("{restaurantId}")
    @Transactional
    public RestaurantDTO update(@Valid @PathParam("restaurantId") UUID restaurantId, UpdateRestaurantDTO dto) {
        Restaurant restaurant = findRestaurant(restaurantId);

        // atualizando por referência
        restaurantMapper.toEntity(dto, restaurant);
        restaurant.persist();
        return restaurantMapper.toDTO(restaurant);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") UUID id) {
        Restaurant restaurant = findRestaurant(id);
        restaurant.delete();
    }

    @GET
    @Path("{restaurantId}/items")
    public List<ItemDTO> items(@PathParam("restaurantId") UUID restaurantId) {
        Restaurant restaurant = findRestaurant(restaurantId);

        Stream<Item> items = Item.stream("restaurant", restaurant);
        return items.map(p -> itemMapper.toDTO(p)).collect(Collectors.toList());
    }

    @POST
    @Path("{restaurantId}/items")
    @Transactional
    @Tag(name = "menu")
    public ItemDTO createMenu(@Valid @PathParam("restaurantId") UUID restaurantId, CreateItemDTO dto) {
        Restaurant restaurant = findRestaurant(restaurantId);

        Item item = itemMapper.toEntity(dto);
        item.restaurant = restaurant;
        item.persist();
        return itemMapper.toDTO(item);
    }

    @PUT
    @Path("{restaurantId}/items/{itemId}")
    @Transactional
    @Tag(name = "menu")
    public ItemDTO updateMenu(
            @Valid
            @PathParam("restaurantId") UUID restaurantId,
            @PathParam("itemId") UUID itemId,
            UpdateItemDTO dto) {

        Restaurant restaurant = findRestaurant(restaurantId);
        Item item = findMenu(itemId);

        if (!item.restaurant.equals(restaurant)) {
            throw new IllegalArgumentException();
        }

        itemMapper.toEntity(dto, item);
        item.persist();
        return itemMapper.toDTO(item);
    }

    @DELETE
    @Path("{restaurantId}/items/{itemId}")
    @Transactional
    @Tag(name = "menu")
    public void deleteMenu(
            @PathParam("restaurantId") UUID restaurantId,
            @PathParam("itemId") UUID itemId) {

        Restaurant restaurant = findRestaurant(restaurantId);
        Item item = findMenu(itemId);

        if (!item.restaurant.equals(restaurant)) {
            throw new IllegalArgumentException();
        }

        item.delete();
    }
}
