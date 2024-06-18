package com.musinsa.assignment.aggregate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryMinMaxPriceService {
  private final CategoryMinMaxPriceRepository categoryMinMaxPriceRepository;
}
