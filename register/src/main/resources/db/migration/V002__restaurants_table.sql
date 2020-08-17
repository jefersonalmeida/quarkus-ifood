create table if not exists restaurants
(
    id          uuid not null,
    createdat   timestamp,
    document    varchar(255),
    name        varchar(255),
    owner       varchar(255),
    updatedat   timestamp,
    location_id uuid,
    constraint restaurants_pkey
        primary key (id),
    constraint fk4yrawo6eulwjhc58gt5ndl5k4
        foreign key (location_id) references locations
);

