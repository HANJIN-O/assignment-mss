package com.musinsa.assignment.item;


import com.musinsa.assignment.brand.Brand;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "brand_id", referencedColumnName = "id")
  private Brand brand;

  @Enumerated(EnumType.ORDINAL)
  private ItemCategoryEnum category;

  @Column(nullable = false)
  private Integer price;

  public void update(UpdateItemRequestDto dto) {
    this.price = dto.getPrice();
  }

  public Item(int price, Brand brand, ItemCategoryEnum category) {
    this.brand = brand;
    this.category = category;
    this.price = price;
  }
}
