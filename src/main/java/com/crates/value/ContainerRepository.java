package com.crates.value;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ContainerRepository extends CrudRepository<Container, Integer> {

    @Query(value = "select * from Container container where container.name = ?1", nativeQuery = true)
    Container findByName(String name);

    @Query("select new com.crates.value.ContainerSimple(c.name, c.image, c.price, c.roi) from Container c")
    Iterable<ContainerSimple> findAllWithoutLinks();

    @Query(value = "select count(*) from Container container", nativeQuery = true)
    int isEmpty();
}
