package com.musinsa.assignment.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ItemCategoryEnum {
  TOP("상의"),
  OUTER("아우터"),
  PANTS("바지"),
  SNEAKERS("스니커즈"),
  BAGS("가방"),
  HATS("모자"),
  SOCKS("양말"),
  ACCESSORY("액세서리");

  @JsonValue
  private final String name;

  private static final Map<String, ItemCategoryEnum> findHelperMap =
          Stream.of(values()).collect(Collectors.toUnmodifiableMap(ItemCategoryEnum::getName, Function.identity()));


  public static ItemCategoryEnum find(String name) throws NoSuchElementException {
    return findHelperMap.get(name);
  }

  @JsonCreator
  public static ItemCategoryEnum of(String category){
    return find(category);
  }
}