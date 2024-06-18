package com.musinsa.assignment;

import com.musinsa.assignment.aggregate.CategoryMinMaxPrice;
import com.musinsa.assignment.aggregate.CategoryMinMaxPriceRepository;
import com.musinsa.assignment.aggregate.MinimumPriceItem;
import com.musinsa.assignment.aggregate.MinimumPriceItemRepository;
import com.musinsa.assignment.brand.Brand;
import com.musinsa.assignment.brand.BrandRepository;
import com.musinsa.assignment.item.Item;
import com.musinsa.assignment.item.ItemRepository;
import com.musinsa.assignment.item.RegisterItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddItemService {
  private final BrandRepository brandRepository;
  private final ItemRepository itemRepository;
  private final MinimumPriceItemRepository minimumPriceItemRepository;
  private final CategoryMinMaxPriceRepository categoryMinMaxPriceRepository;

  @Transactional(rollbackFor = Exception.class)
  public void registerItem(RegisterItemRequestDto dto) {
    Brand brand = brandRepository.findById(dto.getBrandId())
            .orElseThrow(() -> new NoSuchElementException(String.format("Cannot find Brand with id : %d", dto.getBrandId())));
    Item item = new Item(dto.getPrice(), brand, dto.getCategory());
    itemRepository.save(item);

    // update aggregate table
    List<MinimumPriceItem> minimumPriceItems = minimumPriceItemRepository.findAllByBrand(brand);
    Optional<MinimumPriceItem> minimumPriceItemInSameCategory = minimumPriceItems.stream().filter(minimumPriceItem -> minimumPriceItem.getCategory().equals(dto.getCategory())).findFirst();
    minimumPriceItemInSameCategory.ifPresentOrElse(
            (minimumPriceItem) -> {
              if (minimumPriceItem.getPrice() < dto.getPrice()) minimumPriceItem.updatePrice(dto.getPrice());
            },
            () ->{
              MinimumPriceItem newEntity = MinimumPriceItem.builder().category(dto.getCategory()).brand(brand).price(dto.getPrice()).build();
              minimumPriceItems.add(newEntity);
              minimumPriceItemRepository.save(newEntity);
            }
    );

    // update brand outfit min price
    brand.updateTotalOutfitMinPrice(minimumPriceItems.stream().map(MinimumPriceItem::getPrice).reduce(0, Integer::sum));

    // update min, max item in category
    categoryMinMaxPriceRepository.findById(dto.getCategory()).ifPresentOrElse(
            (categoryMinMaxPrice) -> {
              if (categoryMinMaxPrice.getMinPrice() > dto.getPrice()) {
                categoryMinMaxPrice.updateMin(brand, dto.getPrice());
              } else if (categoryMinMaxPrice.getMaxPrice() < dto.getPrice()) {
                categoryMinMaxPrice.updateMax(brand, dto.getPrice());
              }
            },
            () -> {
              CategoryMinMaxPrice newEntity = CategoryMinMaxPrice.builder()
                      .category(dto.getCategory())
                      .build();
              newEntity.updateMin(brand, dto.getPrice());
              newEntity.updateMax(brand, dto.getPrice());
              categoryMinMaxPriceRepository.save(newEntity);
            }
    );
  }
}
