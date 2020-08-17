create table if not exists menus
(
    id            uuid not null,
    description   varchar(255),
    name          varchar(255),
    price         numeric(19, 2),
    restaurant_id uuid,
    constraint menus_pkey
        primary key (id),
    constraint fk49thmnytvo47ttv4ggtwo9rrj
        foreign key (restaurant_id) references restaurants
);

