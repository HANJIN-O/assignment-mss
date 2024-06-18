package com.musinsa.assignment;


import com.musinsa.assignment.brand.Brand;
import com.musinsa.assignment.brand.BrandRepository;
import com.musinsa.assignment.brand.BrandService;
import com.musinsa.assignment.brand.RegisterBrandRequestDto;
import com.musinsa.assignment.item.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {
  @InjectMocks
  private BrandService brandService;

  @Mock
  private BrandRepository brandRepository;


  @Test
  public void testRegisterBrand() {
    // given
    RegisterBrandRequestDto dto = new RegisterBrandRequestDto();
    String brandName = "테스트 브랜드";
    dto.setName(brandName);
    ArgumentCaptor<Brand> captor = ArgumentCaptor.forClass(Brand.class);

    // when
    brandService.registerBrand(dto);

    // then
    verify(brandRepository, times(1)).save(captor.capture());
    Assertions.assertEquals(1, captor.getAllValues().size());
    Assertions.assertEquals(brandName, captor.getValue().getName());
    Assertions.assertNull(captor.getValue().getId());
  }
}
