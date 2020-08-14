package com.github.jefersonalmeida.ifood.register;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "restaurant")
public class RestaurantResource {

    @GET
    public List<Restaurant> index() {
        return Restaurant.listAll();
    }

    private Restaurant findRestaurant(UUID restaurantId) {
        Optional<Restaurant> restaurant = Restaurant.findByIdOptional(restaurantId);
        return restaurant.orElseThrow(NotFoundException::new);
    }

    public Menu findMenu(UUID menuId) {
        Optional<Menu> menu = Menu.findByIdOptional(menuId);
        return menu.orElseThrow(NotFoundException::new);
    }

    @POST
    @Transactional
    public Restaurant create(Restaurant dto) {
        dto.persist();
        return dto;
    }

    @PUT
    @Path("{restaurantId}")
    @Transactional
    public Restaurant update(@PathParam("restaurantId") UUID restaurantId, Restaurant dto) {
        Restaurant restaurant = findRestaurant(restaurantId);
        restaurant.name = dto.name;
        restaurant.persist();
        return restaurant;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") UUID id) {
        Restaurant restaurant = findRestaurant(id);
        restaurant.delete();
    }

    @GET
    @Path("{restaurantId}/menus")
    public List<Restaurant> menus(@PathParam("restaurantId") UUID restaurantId) {
        Restaurant restaurant = findRestaurant(restaurantId);
        return Menu.list("restaurant", restaurant);
    }

    @POST
    @Path("{restaurantId}/menus")
    @Transactional
    @Tag(name = "menu")
    public Menu createMenu(@PathParam("restaurantId") UUID restaurantId, Menu dto) {
        Restaurant restaurant = findRestaurant(restaurantId);
        Menu menu = new Menu();
        menu.name = dto.name;
        menu.description = dto.description;
        menu.price = dto.price;
        menu.restaurant = restaurant;
        menu.persist();
        return menu;
    }

    @PUT
    @Path("{restaurantId}/menus/{menuId}")
    @Transactional
    @Tag(name = "menu")
    public Menu updateMenu(
            @PathParam("restaurantId") UUID restaurantId,
            @PathParam("menuId") UUID menuId,
            Menu dto) {

        Restaurant restaurant = findRestaurant(restaurantId);
        Menu menu = findMenu(menuId);

        if (!menu.restaurant.equals(restaurant)) {
            throw new IllegalArgumentException();
        }

        menu.price = dto.price;
        menu.persist();
        return menu;
    }

    @DELETE
    @Path("{restaurantId}/menus/{menuId}")
    @Transactional
    @Tag(name = "menu")
    public void deleteMenu(
            @PathParam("restaurantId") UUID restaurantId,
            @PathParam("menuId") UUID menuId) {

        Restaurant restaurant = findRestaurant(restaurantId);
        Menu menu = findMenu(menuId);

        if (!menu.restaurant.equals(restaurant)) {
            throw new IllegalArgumentException();
        }

        menu.delete();
    }
}
