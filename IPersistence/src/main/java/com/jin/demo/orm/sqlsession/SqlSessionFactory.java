package com.jin.demo.orm.sqlsession;

/**
 * @author wangjin
 */
public interface SqlSessionFactory {
    /**
     * 创建SqlSession对象
     * @return
     */
    SqlSession openSession();
}
