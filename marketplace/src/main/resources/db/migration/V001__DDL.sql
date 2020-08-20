create table if not exists public.locations
(
    id        uuid not null,
    latitude  double precision,
    longitude double precision,
    constraint locations_pkey
        primary key (id)
);

create unique index if not exists locations_unique
    on public.locations (id);

create index if not exists locations_index
    on public.locations (id);


create table if not exists public.restaurants
(
    id          uuid not null,
    location_id uuid,
    name        varchar(255),
    constraint restaurants_pkey
        primary key (id),
    constraint restaurants_fk_location_id
        foreign key (location_id) references public.locations
);


create unique index if not exists restaurants_unique
    on public.restaurants (id);

create index if not exists restaurants_index
    on public.restaurants (id);

create table if not exists public.items
(
    id            uuid not null,
    restaurant_id uuid,
    name          varchar(255),
    description   varchar(255),
    price         numeric(19, 2),
    constraint items_pkey
        primary key (id),
    constraint items_fk_restaurant_id
        foreign key (restaurant_id) references public.restaurants
);

create unique index if not exists items_unique
    on public.items (id);

create index if not exists items_index
    on public.items (id);


create table if not exists public.items_clients
(
    item   uuid not null,
    client varchar(255)
);

INSERT INTO public.locations (id, latitude, longitude)
VALUES ('b326eba3-e23a-4c55-9310-3706b34b837f', -15.817759, -47.836959);

INSERT INTO public.restaurants (id, location_id, name)
VALUES ('3e67221c-7f3e-48e8-94fb-343195440c67', 'b326eba3-e23a-4c55-9310-3706b34b837f', 'Manguai');

INSERT INTO public.items
    (id, name, description, restaurant_id, price)
VALUES ('325e1eea-e06c-4c7f-aaec-15547912ff5f', 'Cuscuz com Ovo', 'Bom demais no café da manhã',
        '3e67221c-7f3e-48e8-94fb-343195440c67', 3.99);

INSERT INTO public.items
    (id, name, description, restaurant_id, price)
VALUES ('b6c0cf61-1c5d-45bc-a079-cc7e17a66f4a', 'Peixe frito', 'Agulhinha frita, excelente com Cerveja',
        '3e67221c-7f3e-48e8-94fb-343195440c67', 99.99);
