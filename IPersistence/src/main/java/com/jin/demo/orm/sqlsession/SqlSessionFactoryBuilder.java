package com.jin.demo.orm.sqlsession;

import com.jin.demo.orm.config.XMLConfigerBuilder;
import com.jin.demo.orm.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @author wangjin
 */
public class SqlSessionFactoryBuilder {

    private Configuration configuration;

    public SqlSessionFactoryBuilder() {
        this.configuration = new Configuration();
    }

    public SqlSessionFactory build(InputStream inputStream) throws PropertyVetoException, DocumentException {
        XMLConfigerBuilder xmlConfigerBuilder = new XMLConfigerBuilder(configuration);
        Configuration configuration = xmlConfigerBuilder.parseConfiguration(inputStream);
        return new DefaultSqlSessionFactory(configuration);
    }
}
