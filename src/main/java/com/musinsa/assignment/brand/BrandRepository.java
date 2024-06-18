package com.musinsa.assignment.brand;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
  @Query("Select b FROM Brand b ORDER BY b.totalOutfitMinPrice ASC LIMIT 1")
  Optional<Brand> findBrandHasMinimumTotalOutfitPrice();
}
