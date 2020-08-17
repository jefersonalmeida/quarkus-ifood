create table if not exists locations
(
    id        uuid not null,
    latitude  double precision,
    longitude double precision,
    constraint locations_pkey
        primary key (id)
);
