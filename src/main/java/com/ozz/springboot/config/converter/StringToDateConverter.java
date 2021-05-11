package com.ozz.springboot.config.converter;

import com.google.common.base.Strings;
import com.ozz.springboot.exception.ErrorException;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String, Date> {

  public String[] PATTERNS = new String[] {"yyyy", "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS"};

  @Override
  public Date convert(String value) {
    if (Strings.isNullOrEmpty(value)) {
      return null;
    }
    value = value.trim();
    try {
      if (value.contains("-")) {
        return DateUtils.parseDate(value, PATTERNS);
      } else if (value.matches("^\\d+$")) {
        return new Date(Long.valueOf(value));
      }
    } catch (Exception e) {
      throw new ErrorException(String.format("parser %s to Date fail", value));
    }
    throw new ErrorException(String.format("parser %s to Date fail", value));
  }

}
