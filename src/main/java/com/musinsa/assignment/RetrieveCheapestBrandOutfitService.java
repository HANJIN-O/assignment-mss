package com.musinsa.assignment;

import com.musinsa.assignment.aggregate.MinimumPriceItem;
import com.musinsa.assignment.aggregate.MinimumPriceItemRepository;
import com.musinsa.assignment.brand.Brand;
import com.musinsa.assignment.brand.BrandRepository;
import com.musinsa.assignment.dto.BrandCategoryPrice;
import com.musinsa.assignment.dto.CategoryPrice;
import com.musinsa.assignment.dto.CheapestBrandOutfit;
import com.musinsa.assignment.dto.OutfitInformation;
import com.musinsa.assignment.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 고객은 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액이 얼마인지 확인할 수 있어야 합니다.
 */
@Service
@RequiredArgsConstructor
public class RetrieveCheapestBrandOutfitService {
  private final BrandRepository brandRepository;
  private final MinimumPriceItemRepository minimumPriceItemRepository;

  @Transactional(readOnly = true)
  public CheapestBrandOutfit retrieve() {
    Brand brand = brandRepository.findBrandHasMinimumTotalOutfitPrice()
            .orElseThrow(() -> new NoSuchElementException("No brand data"));
    List<MinimumPriceItem> items = minimumPriceItemRepository.findAllByBrand(brand);

    return CheapestBrandOutfit.builder()
            .data(OutfitInformation.builder()
                    .totalPrice(brand.getTotalOutfitMinPrice())
                    .items(items.stream().map(item -> CategoryPrice.builder().price(item.getPrice()).category(item.getCategory()).build()).toList())
                    .brandName(brand.getName())
                    .build())
            .build();
  }
}
