package com.musinsa.assignment;

import com.musinsa.assignment.brand.Brand;
import com.musinsa.assignment.brand.BrandRepository;
import com.musinsa.assignment.brand.BrandService;
import com.musinsa.assignment.brand.RegisterBrandRequestDto;
import com.musinsa.assignment.dto.CheapestBrandOutfit;
import com.musinsa.assignment.dto.CheapestBrandPerCategoryAndTotalPrice;
import com.musinsa.assignment.dto.MinMaxPriceItem;
import com.musinsa.assignment.item.*;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 브랜드 및 상품을 추가 / 업데이트 / 삭제하는 API
 */

@RestController
@Validated
@RequiredArgsConstructor
public class Controller {
  /**
   * Seeding용 의존성
   */
  private static final String CSV_FILE = "data_set.csv";
  private static final String DELIMITER = " ";
  private final BrandRepository brandRepository;
  private final ItemRepository itemRepository;

  private static final String PREFIX = "/api/v1";

  private final AddItemService addItemService;
  private final UpdateItemService updateItemService;
  private final DeleteItemService deleteItemService;
  private final BrandService brandService;
  private final RetrieveCheapestBrandByCategoryService retrieveCheapestBrandByCategoryService;
  private final RetrieveCheapestBrandOutfitService retrieveCheapestBrandOutfitService;
  private final RetrieveCheapestAndMostExpensiveItemByCategoryService retrieveCheapestAndMostExpensiveItemByCategoryService;

  @PostMapping(PREFIX + "/brand")
  public GeneralResponse<Void> registerBrand(@Valid @RequestBody RegisterBrandRequestDto dto) {
    brandService.registerBrand(dto);
    return GeneralResponse.empty();
  }

  @PostMapping(PREFIX + "/item")
  public GeneralResponse<Void> createItem(@Valid @RequestBody RegisterItemRequestDto dto) {
    addItemService.registerItem(dto);
    return GeneralResponse.empty();
  }

  @PutMapping(PREFIX + "/item/{itemId}")
  public GeneralResponse<Void> updateItem(@Valid @RequestBody UpdateItemRequestDto dto, @PathVariable int itemId) {
    updateItemService.updateItem(itemId, dto);
    return GeneralResponse.empty();
  }

  @DeleteMapping(PREFIX + "/item/{itemId}")
  public GeneralResponse<Void> deleteItem(@PathVariable int itemId) {
    deleteItemService.deleteItem(itemId);
    return GeneralResponse.empty();
  }

  @GetMapping(PREFIX + "/cheapest-item-per-category")
  public GeneralResponse<CheapestBrandPerCategoryAndTotalPrice> getCheapestBrandPerCategoryAndTotalPrice() {
    return GeneralResponse.ok(retrieveCheapestBrandByCategoryService.retrieve());
  }

  @GetMapping(PREFIX + "/cheapest-brand-outfit")
  public GeneralResponse<CheapestBrandOutfit> getCheapestBrandOutfit() {
    return GeneralResponse.ok(retrieveCheapestBrandOutfitService.retrieve());
  }

  @GetMapping(PREFIX + "/min-max-price-item")
  public GeneralResponse<MinMaxPriceItem> getMinMaxPriceItem(
          @RequestParam String category
  ) {
    return GeneralResponse.ok(retrieveCheapestAndMostExpensiveItemByCategoryService.retrieve(ItemCategoryEnum.find(category)));
  }

  @GetMapping("/seed")
  public void seed() {
    ClassPathResource resource = new ClassPathResource("/" + CSV_FILE);
    AtomicInteger idx = new AtomicInteger(1);
    Map<String, Brand> brandMap = new HashMap<>();
    try (InputStream inputStream = resource.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      List<Item> items = reader.lines()
              .map(line -> {
                String[] row = line.split(DELIMITER);
                brandMap.putIfAbsent(row[0], new Brand(row[0]));
                return Item.builder().id(idx.getAndIncrement()).brand(brandMap.get(row[0])).price(Integer.parseInt(row[2])).category(ItemCategoryEnum.find(row[1])).build();
              })
              .toList();
      brandRepository.saveAll(items.stream().map(Item::getBrand).collect(Collectors.toSet()));
      items.forEach(item -> {
        RegisterItemRequestDto dto = new RegisterItemRequestDto();
        dto.setCategory(item.getCategory());
        dto.setBrandId(item.getBrand().getId());
        dto.setPrice(item.getPrice());
        addItemService.registerItem(dto);
      });
    } catch (IOException e) {
      System.out.println("Failed to read data set from csv file");
      throw new RuntimeException(e);
    }
  }
}
