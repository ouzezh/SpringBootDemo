package com.ozz.springboot.web;

import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.text.csv.CsvWriter;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import com.ozz.springboot.service.MyService;
import com.ozz.springboot.vo.MyDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Api(tags = "我的分组")
@Slf4j
@RestController
public class MyRestController {
    @Autowired
//  @Qualifier("myService")
    private MyService myService;

    @ApiOperation(value = "测试方法", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "测试参数", required = false, dataType = "String", example = "n1")
    })
//  @io.swagger.annotations.ApiResponse(code = 500, message = "我的错误", response = MyModel.class) // response无效问题需配置
//  springfox.documentation.swagger.use-model-v3=false
    @ApiResponse(responseCode = "500", description = "返回数据", content = @Content(schema = @Schema(implementation =
            MyDto.class)))
    @GetMapping(value = "/v1/test")
    public Map<String, String> test(@RequestParam(required = true) String name) {
        return myService.myService(name);
    }

    @ApiOperation("测试方法2")
    @PostMapping(value = "/v1/test2")
    public MyDto test2(@RequestBody MyDto myModel) {
        return myModel;
    }

    @GetMapping(value = "/v1/test/{id}")
    public String testPathParam(@PathVariable Long id) {
        return "id=" + id;
    }

    @GetMapping(value = "/v1/test_map")
    public Map<String, String> testMap(@RequestParam Map<String, String> map) {
        return map;
    }

    @SneakyThrows
    @PostMapping(value = "/v1/test/upload")
    public void testUpload(@RequestPart MultipartFile file) {
        log.info(file.getName());
        log.info(String.valueOf(file.getSize()));
        StaticLog.info(String.format("--Start--\r\n%s\r\n--End--", StrUtil.str(file.getBytes(), CharsetUtil.CHARSET_UTF_8)));
    }

    @SneakyThrows
    @GetMapping(value = "/v1/test/download")
    public void testDownload(HttpServletResponse response) {
        response.reset();
        response.setContentType("application/vnd.ms-excel; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("test.csv", "UTF-8"));

        PrintWriter out = response.getWriter();
        String bomStr = new String(new byte[]{(byte) 0xef, (byte) 0xbb, (byte) 0xbf}, StandardCharsets.UTF_8);
        out.print(bomStr);

        CsvWriter w = CsvUtil.getWriter(response.getWriter());
        w.writeLine("1", "john");
        w.writeLine("2", "bob");
    }
}
