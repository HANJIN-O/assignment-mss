package com.musinsa.assignment;

import com.musinsa.assignment.aggregate.CategoryMinMaxPrice;
import com.musinsa.assignment.aggregate.CategoryMinMaxPriceRepository;
import com.musinsa.assignment.dto.BrandCategoryPrice;
import com.musinsa.assignment.dto.CheapestBrandPerCategoryAndTotalPrice;
import com.musinsa.assignment.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 고객은 카테고리 별로 최저가격인 브랜드와 가격을 조회하고 총액이 얼마인지 확인할 수 있어야 합니다.
 */
@Service
@RequiredArgsConstructor
public class RetrieveCheapestBrandByCategoryService {
  private final CategoryMinMaxPriceRepository categoryMinMaxPriceRepository;

  public CheapestBrandPerCategoryAndTotalPrice retrieve() {
    List<BrandCategoryPrice> data = categoryMinMaxPriceRepository.findAll().stream().map(item -> BrandCategoryPrice.builder().brandName(item.getMinPriceBrand().getName()).category(item.getCategory()).price(item.getMinPrice()).build()).toList();
    return new CheapestBrandPerCategoryAndTotalPrice(data, data.stream().map(item -> (long) item.getPrice()).reduce(0L, Long::sum));
  }
}

