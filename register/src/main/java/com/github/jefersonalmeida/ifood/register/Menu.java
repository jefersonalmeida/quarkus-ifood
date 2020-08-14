package com.github.jefersonalmeida.ifood.register;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(schema = "public", name = "menus")
public class Menu extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    public String name;
    public String description;

    @ManyToOne
    public Restaurant restaurant;
    public BigDecimal price;
}
