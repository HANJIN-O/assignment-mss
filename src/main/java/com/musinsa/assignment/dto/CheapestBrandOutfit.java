package com.musinsa.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CheapestBrandOutfit {
  @JsonProperty("최저가")
  private OutfitInformation data;
}