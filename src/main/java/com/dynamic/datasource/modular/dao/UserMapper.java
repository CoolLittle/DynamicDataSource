package com.dynamic.datasource.modular.dao;

import com.dynamic.datasource.modular.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: caozheng
 * @date: 2019/9/26 14:16
 */
@Component
@Mapper
public interface UserMapper {


	User findUserById(int id);

	List<User> findUser();

}
