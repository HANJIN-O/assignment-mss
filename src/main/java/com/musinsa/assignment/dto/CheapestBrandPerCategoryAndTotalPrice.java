package com.musinsa.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CheapestBrandPerCategoryAndTotalPrice {
  @JsonProperty("상품")
  private List<BrandCategoryPrice> items;
  @JsonProperty("총액")
  private long totalPrice;
}
