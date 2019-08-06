package com.ozz.springboot.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SampleMvcController {
  @SuppressWarnings("serial")
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
      }
    });
    return "greeting";
  }
}
