package com.jin.demo.orm.sqlsession;

import java.util.List;

/**
 * @author wangjin
 */
public interface SqlSession {
    /**
     * 查询多个
     */
    <E> List<E> selectList(String statementId, Object... param) throws Exception;

    /**
     * 查询单个
     */
    <T> T selectOne(String statementId,Object... params) throws Exception;

    /**
     * 新增
     */
    int insert(String statementId,Object... params) throws Exception;
    /**
     * 修改
     */
    int update(String statementId,Object... params) throws Exception;
    /**
     * 删除
     */
    int delete(String statementId,Object... params) throws Exception;
    /**
     * 获取mapper代理对象
     */
    <T> T getMapper(Class<T> clazz);
}
