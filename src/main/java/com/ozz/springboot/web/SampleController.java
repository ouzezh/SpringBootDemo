package com.ozz.springboot.web;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ozz.springboot.service.SampleDao;

@RestController
public class SampleController {
  private Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private SampleDao dao;

  @RequestMapping(value = "/home_page")
  public String homePage() {
    return "welcome home";
  }

  @RequestMapping(value = "/test")
  public Map<String, String> test(String p) {
    log.debug("p=" + p);
    return dao.sevice(p);
  }

  @RequestMapping(value = "/test")
  public Map<String, String> testMap(@RequestParam Map<String, String> map) {
    return map;
  }

  @GetMapping(value = "/test/{id}")
  public String testPathParam(@PathVariable Long id) {
    return "id=" + id;
  }

  @RequestMapping(value = "/test/upload")
  public void testUpload(MultipartFile file) {
    System.out.println(file.getName());
    System.out.println(file.getSize());
  }

  @RequestMapping(value = "/test/download")
  public void testDownload(HttpServletResponse response) {
    try {
      response.reset();
      response.setContentType("application/octet-stream; charset=utf-8");
      response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("test.csv", "UTF-8"));

      PrintWriter out = response.getWriter();
      String bomStr = new String(new byte[] {(byte) 0xef, (byte) 0xbb, (byte) 0xbf}, "UTF-8");
      out.print(bomStr);

      CSVFormat format = CSVFormat.EXCEL.withHeader(new String[] {"name"});
      try(CSVPrinter printer = new CSVPrinter(out, format);) {
        printer.printRecord("john");
        printer.print("bob");
      }
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
