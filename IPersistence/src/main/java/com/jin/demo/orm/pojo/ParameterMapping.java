package com.jin.demo.orm.pojo;

import lombok.Data;

/**
 * @author wangjin
 */
@Data
public class ParameterMapping {
    private String content;

    public ParameterMapping(String content) {
        this.content = content;
    }
}
