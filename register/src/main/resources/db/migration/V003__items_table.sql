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
