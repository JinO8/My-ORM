package com.jin.demo.orm.test;

import com.jin.demo.orm.io.Resources;
import com.jin.demo.orm.sqlsession.SqlSession;
import com.jin.demo.orm.sqlsession.SqlSessionFactory;
import com.jin.demo.orm.sqlsession.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

/**
 * @author wangjin
 */
public class IPersistenceTest {

    private SqlSession sqlSession;

    @Before
    public void test0() throws Exception {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        sqlSession = sqlSessionFactory.openSession();
    }

    @Test
    public void test() throws Exception {
        User user = new User();
        user.setId(1);
        User o = sqlSession.selectOne("user.selectOne", user);
        System.out.println(o);

    }
    @Test
    public void test1() throws Exception {
        User user = new User();
        user.setId(1);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int insert = mapper.insert(user);
        System.out.println(insert);

    }
    @Test
    public void test2() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("haha");
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int update = mapper.update(user);
        System.out.println(update);
    }
    @Test
    public void test3() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("haha");
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int update = mapper.delete(user);
        System.out.println(update);
    }
}
