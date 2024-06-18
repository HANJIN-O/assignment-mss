package com.musinsa.assignment.aggregate;

import com.musinsa.assignment.brand.Brand;
import com.musinsa.assignment.item.ItemCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MinimumPriceItemRepository extends JpaRepository<MinimumPriceItem, Integer> {
  List<MinimumPriceItem> findAllByBrand(Brand brand);

  Optional<MinimumPriceItem> findAllByBrandAndCategory(Brand brand, ItemCategoryEnum category);
}
