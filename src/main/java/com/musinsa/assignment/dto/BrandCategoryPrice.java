package com.musinsa.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.assignment.item.ItemCategoryEnum;
import lombok.*;

@AllArgsConstructor
@Data
@Builder
public class BrandCategoryPrice {
  @JsonProperty("카테고리")
  private ItemCategoryEnum category;
  @JsonProperty("브랜드")
  private String brandName;
  @JsonProperty("가격")
  private int price;
}