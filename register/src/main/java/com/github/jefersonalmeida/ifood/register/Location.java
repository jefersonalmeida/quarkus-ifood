package com.github.jefersonalmeida.ifood.register;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(schema = "public", name = "locations")
public class Location extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;
    public Double latitude;
    public Double longitude;
}
