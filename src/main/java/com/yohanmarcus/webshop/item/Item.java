package com.yohanmarcus.webshop.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder(toBuilder = true)
@AllArgsConstructor(staticName = "of")
public class Item {
  private Integer id;
  private String name;
  private Integer price;
  private Integer quantity;
  private String description;
  private String category;
}
