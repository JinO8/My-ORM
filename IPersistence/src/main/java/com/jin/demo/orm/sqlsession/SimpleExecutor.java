package com.jin.demo.orm.sqlsession;

import com.jin.demo.orm.pojo.Configuration;
import com.jin.demo.orm.pojo.MappedStatement;
import com.jin.demo.orm.pojo.ParameterMapping;
import com.jin.demo.orm.utils.ParameterMappingTokenHandler;
import com.jin.demo.orm.utils.TokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjin
 */
public class SimpleExecutor implements Executor {

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object[] param) throws Exception {
        PreparedStatement preparedStatement = execute(configuration, mappedStatement, param);
        //查询
        ResultSet resultSet = preparedStatement.executeQuery();
        String resultType = mappedStatement.getResultType();
        Class<?> resultClass = getParamterClass(resultType);
        List<E> results = new ArrayList<E>();
        //遍历返回结果集
        while (resultSet.next()){
            //获取元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            //返回结果对象
            E o = (E) resultClass.newInstance();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                //数据库列名
                String columnName = metaData.getColumnName(i);
                //数据库列对应的值
                Object value = resultSet.getObject(columnName);
                //通过java内省，封装返回结果对象
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o,value);
            }
            results.add(o);
        }
        return results;
    }

    @Override
    public int update(Configuration configuration, MappedStatement mappedStatement, Object[] param) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        PreparedStatement preparedStatement = execute(configuration, mappedStatement, param);
        return preparedStatement.executeUpdate();
    }

    /**
     * 增、删、改、查 都调用此方法，返回PreparedStatement对象
     */
    private PreparedStatement execute(Configuration configuration, MappedStatement mappedStatement, Object[] param) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        //获取连接
        Connection connection = configuration.getDataSource().getConnection();
        //获取原sql
        String sql = mappedStatement.getSql();
        //解析为BoundSql
        BoundSql boundsql = getBoundSql(sql);
        //带？占位符的sql
        String finalSql = boundsql.getSqlText();
        //参数类型全路径名
        String paramterType = mappedStatement.getParamterType();
        //参数类型对象
        Class<?> paramterClass = getParamterClass(paramterType);
        //sql预编译
        PreparedStatement preparedStatement = connection.prepareStatement(finalSql);
        //参数类型的list集合
        List<ParameterMapping> parameterMappingList = boundsql.getParameterMappingList();
        //循环遍历
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String name = parameterMapping.getContent();
            //反射获取属性
            Field declaredField = paramterClass.getDeclaredField(name);
            //强制访问
            declaredField.setAccessible(true);
            //反射赋值
            Object o = declaredField.get(param[0]);
            //替换？占位符
            preparedStatement.setObject(i+1,o);
        }
        return preparedStatement;
    }

    /**
     * 根据全路径类名，获取反射对象
     */
    private Class<?> getParamterClass(String paramterType) throws ClassNotFoundException {
        if (null != paramterType){
            return Class.forName(paramterType);
        }
        return null;
    }

    /**
     * sql解析&封装
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        //替换占位符
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{","}",parameterMappingTokenHandler);
        String parse = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        return new BoundSql(parse, parameterMappings);
    }

}
