package com.musinsa.assignment.brand;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterBrandRequestDto {
  @NotBlank
  private String name;
}
