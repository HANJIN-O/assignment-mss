package com.musinsa.assignment.aggregate;

import com.musinsa.assignment.brand.Brand;
import com.musinsa.assignment.item.ItemCategoryEnum;
import com.musinsa.assignment.item.RegisterItemRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMinMaxPrice {
  @Id
  @Enumerated(EnumType.ORDINAL)
  private ItemCategoryEnum category;

  @JoinColumn(name = "min_price_brand_id", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Brand minPriceBrand;

  @Column(nullable = false)
  private Integer minPrice;

  @JoinColumn(name = "max_price_brand_id", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Brand maxPriceBrand;

  @Column(nullable = false)
  private Integer maxPrice;

  public void updateMax(Brand brand, int price){
    this.maxPrice = price;
    this.maxPriceBrand = brand;
  }

  public void updateMin(Brand brand, int price){
    this.minPrice = price;
    this.minPriceBrand = brand;
  }
}
