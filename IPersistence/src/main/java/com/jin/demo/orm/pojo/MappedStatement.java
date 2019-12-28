package com.jin.demo.orm.pojo;

import lombok.Data;

/**
 * @author wangjin
 */
@Data
public class MappedStatement {
    /**
     * id
     */
    private String id;
    /**
     * sql语句
     */
    private String sql;
    /**
     * 参数类型
     */
    private String paramterType;
    /**
     * 返回结果
     */
    private String resultType;
}
