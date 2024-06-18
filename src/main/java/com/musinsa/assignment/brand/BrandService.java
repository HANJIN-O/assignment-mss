package com.musinsa.assignment.brand;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BrandService {
  private final BrandRepository brandRepository;

  public void registerBrand(RegisterBrandRequestDto dto) {
    Brand brand = new Brand(dto.getName());
    brandRepository.save(brand);
  }
}
