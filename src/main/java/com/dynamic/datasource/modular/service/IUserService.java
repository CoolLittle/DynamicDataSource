package com.dynamic.datasource.modular.service;

import com.dynamic.datasource.modular.entity.User;

import java.util.List;

/**
 * @description:
 * @author: caozheng
 * @date: 2019/9/26 11:34
 */
public interface IUserService {

	User getUserById(int userId);

	void saveUserById();

	List<User> listUser();
}
