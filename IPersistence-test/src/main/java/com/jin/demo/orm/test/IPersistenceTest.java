package com.jin.demo.orm.test;

import com.jin.demo.orm.io.Resources;
import com.jin.demo.orm.sqlsession.SqlSession;
import com.jin.demo.orm.sqlsession.SqlSessionFactory;
import com.jin.demo.orm.sqlsession.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

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
        user.setId(2);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> o = mapper.selectList(user);
        o.forEach(System.out::println);

    }
    @Test
    public void test1() throws Exception {
        User user = new User();
        user.setId(2);
        user.setUsername("jack");
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int insert = mapper.insert(user);
        System.out.println(insert);

    }
    @Test
    public void test2() throws Exception {
        User user = new User();
        user.setId(2);
        user.setUsername("tom");
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
