package com.ozz.springboot.web;

import com.ozz.springboot.exception.ErrorException;
import com.ozz.springboot.model.MyModel;
import com.ozz.springboot.service.MyService;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "我的分组")
@Slf4j
@RestController
public class MyRestController {
  @Autowired
//  @Qualifier("myService")
  private MyService myService;

  @ApiOperation(value = "测试方法")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "name", value = "测试参数", required = false, dataType = "String", example = "n1")
  })
//  @io.swagger.annotations.ApiResponse(code = 500, message = "我的错误", response = MyModel.class) // response无效问题需配置 springfox.documentation.swagger.use-model-v3=false
  @ApiResponse(responseCode = "500", description = "我的错误", content = @Content(schema = @Schema(implementation = MyModel.class)))
  @GetMapping(value = "/v1/test")
  public Map<String, String> test(@RequestParam(required=true) String name) {
    return myService.myService(name);
  }

  @ApiOperation("测试方法2")
  @PostMapping(value = "/v1/test2")
  public MyModel test2(@RequestBody MyModel myModel) {
    return myModel;
  }

  @GetMapping(value = "/v1/test/{id}")
  public String testPathParam(@PathVariable Long id) {
    return "id=" + id;
  }

  @RequestMapping(value = "/v1/test_map")
  public Map<String, String> testMap(@RequestParam Map<String, String> map) {
    return map;
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
