package com.musinsa.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.musinsa.assignment.item.ItemCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
public class CategoryPrice {
  @JsonProperty("카테고리")
  private ItemCategoryEnum category;
  @JsonProperty("가격")
  private int price;
}
