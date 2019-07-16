package com.ozz.springboot.util;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
  private static ThreadLocal<ObjectMapper> objectMapper = new ThreadLocal<ObjectMapper>() {
    @Override
    protected ObjectMapper initialValue() {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));// 日期格式
      return objectMapper;
    }
  };

  public static String toJson(Object bean) {
    try {
      return objectMapper.get().writeValueAsString(bean);
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T fromJson(String json, Class<T> c) {
    try {
      return objectMapper.get().readValue(json, c);
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T fromJson(String json, TypeReference<T> typeReference) {
    try {
      return objectMapper.get().readValue(json, typeReference);
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
