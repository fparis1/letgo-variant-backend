package com.example.springsocial.repository;

import com.example.springsocial.model.Item;
import com.example.springsocial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i LEFT JOIN FETCH i.photos WHERE i.id = :id")
    Optional<Item> findItemWithPhotosById(@Param("id") Long id);

}
