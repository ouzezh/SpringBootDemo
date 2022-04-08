package com.ozz.springboot.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ApiModel(value = "测试Model", description = "测试Model描述")
@Data
public class MyVo {
    @ApiModelProperty(value = "名称", notes = "my notes", example = "n1", allowableValues = "n1 名称1, n2 名称2")
    private String name;
    @ApiModelProperty(hidden = true)
    private String value;
    @ApiModelProperty("ld")
    private LocalDate ld;
    @ApiModelProperty("ldt")
    private LocalDateTime ldt;
}