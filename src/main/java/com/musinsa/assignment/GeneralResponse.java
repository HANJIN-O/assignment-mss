package com.musinsa.assignment;

import lombok.Data;

@Data
public class GeneralResponse<T> {
  private T data;
  private boolean error;
  private String reason;

  GeneralResponse() {
    this.error = false;
  }

  GeneralResponse(T data) {
    this.data = data;
    this.error = false;
  }

  GeneralResponse(String errorReason) {
    this.error = true;
    this.reason = errorReason;
  }

  public static GeneralResponse ok(Object data){
    return new GeneralResponse<>(data);
  }

  public static GeneralResponse<Void> error(Exception e) {
    return new GeneralResponse<>(e.getMessage());
  }

  public static GeneralResponse<Void> error() {
    return new GeneralResponse<>("");
  }

  public static GeneralResponse<Void> empty() {
    return new GeneralResponse<Void>();
  }
}
