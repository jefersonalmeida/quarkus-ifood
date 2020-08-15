package com.github.jefersonalmeida.ifood.register;

import com.github.jefersonalmeida.ifood.register.dto.*;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.transaction.Transactional;
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
public class RestaurantResource {

    @Inject
    RestaurantMapper restaurantMapper;

    @Inject
    MenuMapper menuMapper;

    @GET
    public List<RestaurantDTO> index() {
        Stream<Restaurant> restaurants = Restaurant.streamAll();
        return restaurants.map(r -> restaurantMapper.toDTO(r)).collect(Collectors.toList());
    }

    private Restaurant findRestaurant(UUID restaurantId) {
        Optional<Restaurant> restaurant = Restaurant.findByIdOptional(restaurantId);
        return restaurant.orElseThrow(NotFoundException::new);
    }

    private Menu findMenu(UUID menuId) {
        Optional<Menu> menu = Menu.findByIdOptional(menuId);
        return menu.orElseThrow(NotFoundException::new);
    }

    @POST
    @Transactional
    public RestaurantDTO create(CreateRestaurantDTO dto) {
        Restaurant restaurant = restaurantMapper.toEntity(dto);
        restaurant.persist();
        return restaurantMapper.toDTO(restaurant);
    }

    @PUT
    @Path("{restaurantId}")
    @Transactional
    public RestaurantDTO update(@PathParam("restaurantId") UUID restaurantId, UpdateRestaurantDTO dto) {
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
    @Path("{restaurantId}/menus")
    public List<MenuDTO> menus(@PathParam("restaurantId") UUID restaurantId) {
        Restaurant restaurant = findRestaurant(restaurantId);

        Stream<Menu> menus = Menu.stream("restaurant", restaurant);
        return menus.map(p -> menuMapper.toDTO(p)).collect(Collectors.toList());
    }

    @POST
    @Path("{restaurantId}/menus")
    @Transactional
    @Tag(name = "menu")
    public MenuDTO createMenu(@PathParam("restaurantId") UUID restaurantId, CreateMenuDTO dto) {
        Restaurant restaurant = findRestaurant(restaurantId);

        Menu menu = menuMapper.toEntity(dto);
        menu.restaurant = restaurant;
        menu.persist();
        return menuMapper.toDTO(menu);
    }

    @PUT
    @Path("{restaurantId}/menus/{menuId}")
    @Transactional
    @Tag(name = "menu")
    public MenuDTO updateMenu(
            @PathParam("restaurantId") UUID restaurantId,
            @PathParam("menuId") UUID menuId,
            UpdateMenuDTO dto) {

        Restaurant restaurant = findRestaurant(restaurantId);
        Menu menu = findMenu(menuId);

        if (!menu.restaurant.equals(restaurant)) {
            throw new IllegalArgumentException();
        }

        menuMapper.toEntity(dto, menu);
        menu.persist();
        return menuMapper.toDTO(menu);
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
