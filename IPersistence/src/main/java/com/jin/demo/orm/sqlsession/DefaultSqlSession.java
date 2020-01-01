package com.jin.demo.orm.sqlsession;

import com.jin.demo.orm.pojo.Configuration;
import com.jin.demo.orm.pojo.MappedStatement;

import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjin
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    private Executor simpleExcutor = new SimpleExecutor();
    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... param) throws Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return simpleExcutor.query(configuration, mappedStatement, param);
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if(objects.size() ==1){
            return (T) objects.get(0);
        }else {
            throw new RuntimeException("返回结果过多");
        }
    }

    @Override
    public int insert(String statementId, Object... params) throws Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return simpleExcutor.update(configuration, mappedStatement, params);
    }

    @Override
    public int update(String statementId, Object... params) throws Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return simpleExcutor.update(configuration, mappedStatement, params);
    }

    @Override
    public int delete(String statementId, Object... params) throws Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        return simpleExcutor.update(configuration, mappedStatement, params);
    }

    /**
     * 通过jdk动态代理，生成mapper代理对象
     */
    @Override
    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 方法名
                String methodName = method.getName();
                // 类名全路径
                String className = method.getDeclaringClass().getName();
                // 方法名+类名全路径组成statementId
                String key = className+"."+methodName;
                //优化，通过sql判断
                String sql = configuration.getMappedStatementMap().get(key).getSql();
                /*
                 *简单实现：通过判断方法名来实现
                 */
                if (sql.startsWith("select")){
                    // 基于java内省判断
                    Type genericReturnType = method.getGenericReturnType();
                    // 如果返回值有泛型，查多个
                    if(genericReturnType instanceof ParameterizedType) {
                        return selectList(key, args);
                    }
                    // 否则查单个
                    return selectOne(key,args);
                }else if (sql.startsWith("insert")){
                    return insert(key,args);
                }else if (sql.startsWith("update")){
                    return update(key,args);
                }else {
                    return delete(key,args);
                }
            }
        });
    }
}
