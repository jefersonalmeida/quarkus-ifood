package com.github.jefersonalmeida.ifood.register.panache;

import com.github.jefersonalmeida.ifood.register.Restaurant;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PanacheQueries {

    public void exemplosSelects() {
        // -- Classe --

        //findAll

        PanacheQuery<Restaurant> findAll = Restaurant.findAll();
        Restaurant.findAll(Sort.by("name").and("id", Direction.Ascending));

        PanacheQuery<Restaurant> page = findAll.page(Page.of(0, 10));

        //find sem sort

        Map<String, Object> params = new HashMap<>();
        params.put("name", "");
        Restaurant.find("select r from Restaurant where name = :name", params);

        String name = "";
        Restaurant.find("select r from Restaurant where name = $1", name);

        Restaurant.find("select r from Restaurant where name = :name and id = :id",
                Parameters
                        .with("name", "")
                        .and("id", "3e67221c-7f3e-48e8-94fb-343195440c67")
        );

        //Atalho para findAll.stream, mas precisa de transacao se nao o cursor pode fechar antes

        Restaurant.stream("select r from Restaurant where name = :name", params);

        Restaurant.stream("select r from Restaurant where name = $1", name);

        Restaurant.stream("select r from Restaurant where name = :name and id = :id",
                Parameters
                        .with("name", "")
                        .and("id", "3e67221c-7f3e-48e8-94fb-343195440c67")
        );

        //find by id

        Restaurant findById = Restaurant.findById("3e67221c-7f3e-48e8-94fb-343195440c67");

        //Persist

        Restaurant.persist(findById, findById);

        //Delete

        Restaurant.delete("delete Restaurant where name = :name", params);

        Restaurant.delete("delete Restaurant where name = $1", name);

        Restaurant.delete("name = :name and id = :id",
                Parameters
                        .with("name", "")
                        .and("id", "3e67221c-7f3e-48e8-94fb-343195440c67")
        );

        //Update

        Restaurant.update("update Restaurant where name = :name", params);

        Restaurant.update("update Restaurant where name = $1", name);

        //Count

        Restaurant.count();

        //-- Métodos de objeto--

        Restaurant restaurante = new Restaurant();

        restaurante.persist();

        restaurante.isPersistent();

        restaurante.delete();

    }

}

@Entity
class MyEntity1 extends PanacheEntity {
    public String name;
    @Id
    private UUID id;
}

@Entity
class MyEntity2 extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    public String name;

    public MyEntity2() {
    }
}

@Entity
class MyEntity3 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    public String name;

    public MyEntity3() {
    }
}

@ApplicationScoped
class MyRepository implements PanacheRepository<MyEntity3> {

}
