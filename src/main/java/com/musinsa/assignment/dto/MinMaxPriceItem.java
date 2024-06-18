package com.musinsa.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.assignment.item.ItemCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MinMaxPriceItem {
  @JsonProperty("카테고리")
  private ItemCategoryEnum category;
  @JsonProperty("최저가")
  private BrandPrice minItem;
  @JsonProperty("최고가")
  private BrandPrice maxItem;
}
