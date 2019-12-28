package com.jin.demo.orm.test;

import java.util.List;

public interface UserMapper {
    int insert(User user);
    List<User> selectList(User user);
    User selectOne(User user);
    int update(User user);
    int delete(User user);
}
