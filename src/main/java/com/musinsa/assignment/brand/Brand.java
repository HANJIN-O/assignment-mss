package com.musinsa.assignment.brand;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class Brand {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Integer totalOutfitMinPrice;

  public Brand(String name) {
    this.name = name;
    this.totalOutfitMinPrice = 0;
  }

  public void updateTotalOutfitMinPrice(Integer totalOutfitMinPrice) {
    this.totalOutfitMinPrice = totalOutfitMinPrice;
  }
}
