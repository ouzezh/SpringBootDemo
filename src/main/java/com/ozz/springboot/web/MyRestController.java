package com.ozz.springboot.web;

import com.ozz.springboot.exception.ErrorException;
import com.ozz.springboot.service.MyService;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class MyRestController {
  @Autowired
//  @Qualifier("myService")
  private MyService myService;

  @RequestMapping(value = "/v1/test")
  public Map<String, String> test(@RequestParam(required=true) String p) {
    return myService.myService(p);
  }

  @RequestMapping(value = "/v1/test_map")
  public Map<String, String> testMap(@RequestParam Map<String, String> map) {
    return map;
  }

  @GetMapping(value = "/v1/test/{id}")
  public String testPathParam(@PathVariable Long id) {
    return "id=" + id;
  }

  @RequestMapping(value = "/v1/test/upload")
  public void testUpload(MultipartFile file) {
    log.info(file.getName());
    log.info(String.valueOf(file.getSize()));
    try {
      System.out.println(String.format("--Start--\r\n%s\r\n--End--", StringUtils.toEncodedString(file.getBytes(), Charset.defaultCharset())));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @RequestMapping(value = "/v1/test/download")
  public void testDownload(HttpServletResponse response) {
    try {
      response.reset();
      response.setContentType("application/vnd.ms-excel; charset=utf-8");
      response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("test.csv", "UTF-8"));

      PrintWriter out = response.getWriter();
      String bomStr = new String(new byte[] {(byte) 0xef, (byte) 0xbb, (byte) 0xbf}, StandardCharsets.UTF_8);
      out.print(bomStr);

      CSVFormat format = CSVFormat.EXCEL.withHeader("id", "name");
      try(CSVPrinter printer = new CSVPrinter(out, format);) {
        printer.printRecord("1", "john");
        printer.print("2");
        printer.print("bob");
      }
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new ErrorException(e);
    }
  }
}
