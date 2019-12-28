package com.jin.demo.orm.pojo;


import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangjin
 */
@Data
public class Configuration {
    /**
     * 数据源
     */
    private DataSource dataSource;
    /**
     * key:statementId
     * value:mappedStatement
     */
    private Map<String,MappedStatement> mappedStatementMap = new HashMap<>();
}
