package com.musinsa.assignment;

import com.musinsa.assignment.aggregate.CategoryMinMaxPrice;
import com.musinsa.assignment.aggregate.CategoryMinMaxPriceRepository;
import com.musinsa.assignment.brand.Brand;
import com.musinsa.assignment.dto.BrandCategoryPrice;
import com.musinsa.assignment.dto.CheapestBrandPerCategoryAndTotalPrice;
import com.musinsa.assignment.item.ItemCategoryEnum;
import com.musinsa.assignment.item.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RetrieveCheapestBrandByCategoryServiceTest {
  @InjectMocks
  private RetrieveCheapestBrandByCategoryService retrieveCheapestBrandByCategoryService;

  @Mock
  private CategoryMinMaxPriceRepository categoryMinMaxPriceRepository;


  @Test
  public void testRetrieve(){
    // given
    List<CategoryMinMaxPrice> mockData = List.of(
            CategoryMinMaxPrice.builder()
                    .minPrice(1).minPriceBrand(Brand.builder().name("B1").build()).maxPriceBrand(Brand.builder().name("B8").build()).maxPrice(100)
                    .build(),
            CategoryMinMaxPrice.builder()
                    .minPrice(2).minPriceBrand(Brand.builder().name("B2").build()).maxPriceBrand(Brand.builder().name("B7").build()).maxPrice(99)
                    .build(),
            CategoryMinMaxPrice.builder()
                    .minPrice(3).minPriceBrand(Brand.builder().name("B3").build()).maxPriceBrand(Brand.builder().name("B6").build()).maxPrice(98)
                    .build(),
            CategoryMinMaxPrice.builder()
                    .minPrice(4).minPriceBrand(Brand.builder().name("B4").build()).maxPriceBrand(Brand.builder().name("B5").build()).maxPrice(97)
                    .build(),
            CategoryMinMaxPrice.builder()
                    .minPrice(5).minPriceBrand(Brand.builder().name("B5").build()).maxPriceBrand(Brand.builder().name("B4").build()).maxPrice(96)
                    .build(),
            CategoryMinMaxPrice.builder()
                    .minPrice(6).minPriceBrand(Brand.builder().name("B6").build()).maxPriceBrand(Brand.builder().name("B3").build()).maxPrice(95)
                    .build(),
            CategoryMinMaxPrice.builder()
                    .minPrice(7).minPriceBrand(Brand.builder().name("B7").build()).maxPriceBrand(Brand.builder().name("B2").build()).maxPrice(94)
                    .build(),
            CategoryMinMaxPrice.builder()
                    .minPrice(8).minPriceBrand(Brand.builder().name("B8").build()).maxPriceBrand(Brand.builder().name("B1").build()).maxPrice(93)
                    .build()
    );
    when(categoryMinMaxPriceRepository.findAll()).thenReturn(mockData);

    // when
    CheapestBrandPerCategoryAndTotalPrice cheapestBrandPerCategoryAndTotalPrice = retrieveCheapestBrandByCategoryService.retrieve();

    // then
    Assertions.assertEquals(8, cheapestBrandPerCategoryAndTotalPrice.getItems().size());
    Assertions.assertEquals(36, cheapestBrandPerCategoryAndTotalPrice.getTotalPrice());
  }
}
