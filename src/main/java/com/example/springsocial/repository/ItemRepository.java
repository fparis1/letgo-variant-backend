package com.example.springsocial.repository;

import com.example.springsocial.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i LEFT JOIN FETCH i.photos WHERE i.id = :id")
    Optional<Item> findItemWithPhotosById(@Param("id") Long id);

    Page<Item> getAllBy(Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.category = :category")
    Page<Item> findByCategory(@Param("category") String category, Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.category = :category AND i.subcategory = :subcategory")
    Page<Item> findByCategoryAndSubcategory(@Param("category") String category, @Param("subcategory") String subcategory, Pageable pageable);

}
