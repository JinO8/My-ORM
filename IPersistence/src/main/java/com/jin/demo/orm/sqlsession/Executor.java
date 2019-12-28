package com.jin.demo.orm.sqlsession;

import com.jin.demo.orm.pojo.Configuration;
import com.jin.demo.orm.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @author wangjin
 */
public interface Executor {
    /**
     * 查询（多个/单个）
     */
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object[] param) throws Exception;

    /**
     * 增、删、改
     */
    int update(Configuration configuration, MappedStatement mappedStatement, Object[] param) throws Exception;
}
