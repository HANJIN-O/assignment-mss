package com.musinsa.assignment.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class RegisterItemRequestDto extends UpdateItemRequestDto {
  @Positive
  @NotNull
  private Integer brandId;
  @NotNull
  private ItemCategoryEnum category;
}
