package com.musinsa.assignment;

import com.musinsa.assignment.aggregate.CategoryMinMaxPrice;
import com.musinsa.assignment.aggregate.CategoryMinMaxPriceRepository;
import com.musinsa.assignment.dto.BrandPrice;
import com.musinsa.assignment.dto.MinMaxPriceItem;
import com.musinsa.assignment.item.ItemCategoryEnum;
import com.musinsa.assignment.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

/**
 * 고객은 특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 확인하고 각 브랜드 상품의 가격을 확인할 수 있어야 합니다.
 */
@Service
@RequiredArgsConstructor
public class RetrieveCheapestAndMostExpensiveItemByCategoryService {
  private final CategoryMinMaxPriceRepository categoryMinMaxPriceRepository;

  public MinMaxPriceItem retrieve(ItemCategoryEnum category) {
    CategoryMinMaxPrice item = categoryMinMaxPriceRepository.findById(category)
            .orElseThrow(() -> new NoSuchElementException("There is no CategoryMinMaxPrice data for category " + category));
    return MinMaxPriceItem.builder()
            .category(category)
            .maxItem(BrandPrice.builder().price(item.getMaxPrice()).brandName(item.getMaxPriceBrand().getName()).build())
            .minItem(BrandPrice.builder().price(item.getMinPrice()).brandName(item.getMinPriceBrand().getName()).build())
            .build();
  }
}
