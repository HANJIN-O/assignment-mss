package com.musinsa.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OutfitInformation {
  @JsonProperty("브랜드")
  private String brandName;
  @JsonProperty("카테고리")
  private List<CategoryPrice> items;
  @JsonProperty("총액")
  private long totalPrice;
}
