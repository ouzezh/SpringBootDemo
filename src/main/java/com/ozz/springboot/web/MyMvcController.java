package com.ozz.springboot.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "我的分组2")
@Controller
public class MyMvcController {

  @ApiOperation("欢迎页面")
  @GetMapping("/greeting")
  public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
    model.addAttribute("name", name);
    model.addAttribute("books", new ArrayList<Map<String, String>>() {
      {
        add(new HashMap<String, String>() {
          {
            put("title", "b1");
            put("author", "a1");
          }
        });
        add(new HashMap<String, String>() {
          {
            put("title", "b2");
            put("author", "a2");
          }
        });
        add(new HashMap<String, String>() {
          {
            put("title", "");
            put("author", "");
          }
        });
      }
    });
    return "greeting";
  }
}
