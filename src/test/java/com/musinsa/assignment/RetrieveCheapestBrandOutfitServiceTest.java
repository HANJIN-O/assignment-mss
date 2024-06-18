package com.musinsa.assignment;

import com.musinsa.assignment.aggregate.MinimumPriceItem;
import com.musinsa.assignment.aggregate.MinimumPriceItemRepository;
import com.musinsa.assignment.brand.Brand;
import com.musinsa.assignment.brand.BrandRepository;
import com.musinsa.assignment.dto.CheapestBrandOutfit;
import com.musinsa.assignment.item.ItemCategoryEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RetrieveCheapestBrandOutfitServiceTest {
  @InjectMocks
  private RetrieveCheapestBrandOutfitService retrieveCheapestBrandOutfitService;

  @Mock
  private BrandRepository brandRepository;
  @Mock
  private MinimumPriceItemRepository minimumPriceItemRepository;

  @Test
  public void testRetrieve() {
    // given
    String brandName = "TEST";
    int totalPrice = 45000;
    Brand brandHsMinimumTotalOutfitPrice = Brand.builder().id(1).totalOutfitMinPrice(totalPrice).name(brandName).build();
    List<MinimumPriceItem> mockData = List.of(
            MinimumPriceItem.builder().id(1).price(10000).brand(brandHsMinimumTotalOutfitPrice).category(ItemCategoryEnum.TOP).build(),
            MinimumPriceItem.builder().id(2).price(11000).brand(brandHsMinimumTotalOutfitPrice).category(ItemCategoryEnum.OUTER).build(),
            MinimumPriceItem.builder().id(3).price(12000).brand(brandHsMinimumTotalOutfitPrice).category(ItemCategoryEnum.PANTS).build(),
            MinimumPriceItem.builder().id(4).price(3000).brand(brandHsMinimumTotalOutfitPrice).category(ItemCategoryEnum.HATS).build(),
            MinimumPriceItem.builder().id(5).price(4000).brand(brandHsMinimumTotalOutfitPrice).category(ItemCategoryEnum.BAGS).build(),
            MinimumPriceItem.builder().id(6).price(1500).brand(brandHsMinimumTotalOutfitPrice).category(ItemCategoryEnum.SOCKS).build(),
            MinimumPriceItem.builder().id(7).price(2000).brand(brandHsMinimumTotalOutfitPrice).category(ItemCategoryEnum.SNEAKERS).build(),
            MinimumPriceItem.builder().id(8).price(1500).brand(brandHsMinimumTotalOutfitPrice).category(ItemCategoryEnum.ACCESSORY).build()
    );
    when(brandRepository.findBrandHasMinimumTotalOutfitPrice()).thenReturn(Optional.of(brandHsMinimumTotalOutfitPrice));
    when(minimumPriceItemRepository.findAllByBrand(brandHsMinimumTotalOutfitPrice)).thenReturn(mockData);

    // when
    CheapestBrandOutfit cheapestBrandOutfit = retrieveCheapestBrandOutfitService.retrieve();

    // then
    Assertions.assertEquals(brandName, cheapestBrandOutfit.getData().getBrandName());
    Assertions.assertEquals(totalPrice, cheapestBrandOutfit.getData().getTotalPrice());
    Assertions.assertEquals(mockData.size(), cheapestBrandOutfit.getData().getItems().size());
  }
}
