package com.musinsa.assignment;

import com.musinsa.assignment.aggregate.CategoryMinMaxPrice;
import com.musinsa.assignment.aggregate.CategoryMinMaxPriceRepository;
import com.musinsa.assignment.aggregate.MinimumPriceItem;
import com.musinsa.assignment.aggregate.MinimumPriceItemRepository;
import com.musinsa.assignment.item.Item;
import com.musinsa.assignment.item.ItemRepository;
import com.musinsa.assignment.item.UpdateItemRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateItemService {
  private final ItemRepository itemRepository;
  private final MinimumPriceItemRepository minimumPriceItemRepository;
  private final CategoryMinMaxPriceRepository categoryMinMaxPriceRepository;

  @Transactional(rollbackFor = Exception.class)
  public void updateItem(int itemId, UpdateItemRequestDto dto) {
    Item itemToUpdate = itemRepository.findById(itemId).orElseThrow(() -> new NoSuchElementException("Cannot find item with id: " + itemId));
    itemToUpdate.update(dto);
    itemRepository.save(itemToUpdate);

    // update aggregate table
    List<MinimumPriceItem> minimumPriceItems = minimumPriceItemRepository.findAllByBrand(itemToUpdate.getBrand());
    Optional<MinimumPriceItem> minimumPriceItemInSameCategory = minimumPriceItems.stream().filter(minimumPriceItem -> minimumPriceItem.getCategory().equals(itemToUpdate.getCategory())).findFirst();
    minimumPriceItemInSameCategory.ifPresentOrElse(
            (minimumPriceItem) -> {
              if (minimumPriceItem.getPrice() < dto.getPrice()) minimumPriceItem.updatePrice(dto.getPrice());
            },
            () -> {}
    );

    // update brand outfit min price
    itemToUpdate.getBrand().updateTotalOutfitMinPrice(minimumPriceItems.stream().map(MinimumPriceItem::getPrice).reduce(0, Integer::sum));

    // update min, max item in category
    categoryMinMaxPriceRepository.findById(itemToUpdate.getCategory()).ifPresentOrElse(
            (categoryMinMaxPrice) -> {
              if (categoryMinMaxPrice.getMinPrice() > dto.getPrice()) {
                categoryMinMaxPrice.updateMin(itemToUpdate.getBrand(), dto.getPrice());
              } else if (categoryMinMaxPrice.getMaxPrice() < dto.getPrice()) {
                categoryMinMaxPrice.updateMax(itemToUpdate.getBrand(), dto.getPrice());
              }
            },
            () -> {}
    );
  }
}
