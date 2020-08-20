create table if not exists public.restaurants
(
    id          uuid not null,
    location_id uuid,
    name        varchar(255),
    owner       varchar(255),
    document    varchar(255),
    created_at  timestamp,
    updated_at  timestamp,
    constraint restaurants_pkey
        primary key (id),
    constraint restaurants_fk_location_id
        foreign key (location_id) references public.locations
);


create unique index if not exists restaurants_unique
    on public.restaurants (id);

create index if not exists restaurants_index
    on public.restaurants (id);
