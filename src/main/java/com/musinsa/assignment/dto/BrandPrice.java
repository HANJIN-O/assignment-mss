package com.musinsa.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class BrandPrice {
  @JsonProperty("브랜드")
  private String brandName;
  @JsonProperty("가격")
  private int price;
}
