package com.musinsa.assignment.aggregate;

import com.musinsa.assignment.item.ItemCategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMinMaxPriceRepository extends JpaRepository<CategoryMinMaxPrice, ItemCategoryEnum> {
}
