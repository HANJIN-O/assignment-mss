package com.musinsa.assignment.aggregate;

import com.musinsa.assignment.brand.Brand;
import com.musinsa.assignment.item.ItemCategoryEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "minimum_price_item",
        indexes = @Index(name = "idx_unique_brand_category", columnList = "brand_id, category", unique = true)
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MinimumPriceItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @JoinColumn(name = "brand_id", referencedColumnName = "id")
  @ManyToOne(optional = false)
  private Brand brand;

  @Enumerated(EnumType.ORDINAL)
  private ItemCategoryEnum category;

  @Column(nullable = false)
  private Integer price;

  public void updatePrice(Integer price){
    this.price = price;
  }
}
