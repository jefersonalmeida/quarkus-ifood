package com.github.jefersonalmeida.ifood.register;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(schema = "public", name = "restaurants")
public class Restaurant extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    public String name;
    public String owner;
    public String document;

    @ManyToOne
    public Location location;

    @CreationTimestamp
    public OffsetDateTime createdAt;

    @UpdateTimestamp
    public OffsetDateTime updatedAt;
}
