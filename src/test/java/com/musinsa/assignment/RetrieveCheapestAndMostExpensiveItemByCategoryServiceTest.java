package com.musinsa.assignment;

import com.musinsa.assignment.aggregate.CategoryMinMaxPrice;
import com.musinsa.assignment.aggregate.CategoryMinMaxPriceRepository;
import com.musinsa.assignment.brand.Brand;
import com.musinsa.assignment.dto.MinMaxPriceItem;
import com.musinsa.assignment.item.ItemCategoryEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RetrieveCheapestAndMostExpensiveItemByCategoryServiceTest {
  @InjectMocks
  private RetrieveCheapestAndMostExpensiveItemByCategoryService retrieveCheapestAndMostExpensiveItemByCategoryService;

  @Mock
  private CategoryMinMaxPriceRepository categoryMinMaxPriceRepository;

  @Test
  public void testRetrieve_succeed() {
    // given
    ItemCategoryEnum category = ItemCategoryEnum.TOP;
    int minPrice = 14000;
    int maxPrice = 29300;
    String minBrandName = "min";
    String maxBrandName = "MAX";
    CategoryMinMaxPrice minMaxPrice = CategoryMinMaxPrice.builder()
            .category(category)
            .minPrice(minPrice)
            .minPriceBrand(Brand.builder().name(minBrandName).build())
            .maxPrice(maxPrice)
            .maxPriceBrand(Brand.builder().name(maxBrandName).build())
            .build();
    when(categoryMinMaxPriceRepository.findById(category)).thenReturn(Optional.of(minMaxPrice));

    // when
    MinMaxPriceItem minMaxPriceItem = retrieveCheapestAndMostExpensiveItemByCategoryService.retrieve(category);

    // then
    Assertions.assertEquals(category, minMaxPriceItem.getCategory());
    Assertions.assertEquals(minPrice, minMaxPriceItem.getMinItem().getPrice());
    Assertions.assertEquals(maxPrice, minMaxPriceItem.getMaxItem().getPrice());
    Assertions.assertEquals(minBrandName, minMaxPriceItem.getMinItem().getBrandName());
    Assertions.assertEquals(maxBrandName, minMaxPriceItem.getMaxItem().getBrandName());
  }

  @Test
  public void testRetrieve_must_throw_exception_when_no_data() {
    // given
    ItemCategoryEnum category = ItemCategoryEnum.TOP;
    when(categoryMinMaxPriceRepository.findById(category)).thenReturn(Optional.empty());
    // when
    Executable ex = () -> retrieveCheapestAndMostExpensiveItemByCategoryService.retrieve(category);
    // then
    Assertions.assertThrows(NoSuchElementException.class, ex);
  }

  @Test
  public void testRetrieve_exception_must_contain_message() {
    // given
    ItemCategoryEnum category = ItemCategoryEnum.TOP;
    when(categoryMinMaxPriceRepository.findById(category)).thenReturn(Optional.empty());
    // when
    try {
      retrieveCheapestAndMostExpensiveItemByCategoryService.retrieve(category);
    } catch (NoSuchElementException e) {
      // then
      Assertions.assertFalse(e.getMessage() == null || e.getMessage().isBlank());
    }
  }
}