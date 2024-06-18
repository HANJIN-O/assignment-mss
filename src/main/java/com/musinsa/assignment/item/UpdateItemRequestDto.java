package com.musinsa.assignment.item;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class UpdateItemRequestDto {
  @PositiveOrZero
  protected int price;
}
