package com.musinsa.assignment;

import com.musinsa.assignment.aggregate.CategoryMinMaxPrice;
import com.musinsa.assignment.aggregate.CategoryMinMaxPriceRepository;
import com.musinsa.assignment.aggregate.MinimumPriceItem;
import com.musinsa.assignment.aggregate.MinimumPriceItemRepository;
import com.musinsa.assignment.brand.Brand;
import com.musinsa.assignment.item.Item;
import com.musinsa.assignment.item.ItemCategoryEnum;
import com.musinsa.assignment.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DeleteItemService {
  private final ItemRepository itemRepository;
  private final MinimumPriceItemRepository minimumPriceItemRepository;
  private final CategoryMinMaxPriceRepository categoryMinMaxPriceRepository;

  @Transactional(rollbackFor = Exception.class)
  public void deleteItem(int itemId) {
    Item itemToDelete = itemRepository.findById(itemId).orElseThrow(() -> new NoSuchElementException("Cannot find item with id: " + itemId));

    ItemCategoryEnum category = itemToDelete.getCategory();
    Brand brand = itemToDelete.getBrand();

    itemRepository.delete(itemToDelete);
    itemToDelete = null;


    // update aggregate table
    List<Item> items = itemRepository.findByCategoryAndBrand(category, brand);
    List<MinimumPriceItem> minimumPriceItems = minimumPriceItemRepository.findAllByBrand(brand);
    MinimumPriceItem minimumPriceItemInSameCategory = minimumPriceItems.stream().filter(minimumPriceItem -> minimumPriceItem.getCategory().equals(category)).findFirst().orElseThrow((() -> new NoSuchElementException("Cannot find MinimumPriceItem for brand " + brand.getName() + " category " + category)));
    minimumPriceItemInSameCategory.updatePrice(items.stream().mapToInt(Item::getPrice).min().orElse(0));

    // update brand outfit min price
    brand.updateTotalOutfitMinPrice(minimumPriceItems.stream().map(MinimumPriceItem::getPrice).reduce(0, Integer::sum));

    // update min, max item in category
    List<Item> itemsByCategory = itemRepository.findMinMaxItem(category);
    boolean firstIsMin = itemsByCategory.getFirst().getPrice() < itemsByCategory.getLast().getPrice();
    Item minPriceItem = itemsByCategory.get(firstIsMin ? 0 : 1);
    Item maxPriceItem = itemsByCategory.get(firstIsMin ? 1 : 0);
    CategoryMinMaxPrice categoryMinMaxPrice = categoryMinMaxPriceRepository.findById(category).orElseThrow(() -> new NoSuchElementException("Cannot find CategoryMinMaxPrice for category " + category));
    categoryMinMaxPrice.updateMax(maxPriceItem.getBrand(), maxPriceItem.getPrice());
    categoryMinMaxPrice.updateMin(minPriceItem.getBrand(), minPriceItem.getPrice());
  }
}
