package com.musinsa.assignment.item;

import com.musinsa.assignment.brand.Brand;
import com.musinsa.assignment.dto.BrandCategoryPrice;
import com.musinsa.assignment.dto.CategoryPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
  List<Item> findByCategoryAndBrand(ItemCategoryEnum category, Brand brand);

  @Query("SELECT i FROM Item i WHERE i.category = :category ORDER BY i.price ASC LIMIT 1 UNION SELECT i FROM Item i WHERE i.category = :category ORDER BY i.price DESC LIMIT 1 ")
  List<Item> findMinMaxItem(ItemCategoryEnum category);
}

