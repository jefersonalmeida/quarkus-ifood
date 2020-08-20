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
