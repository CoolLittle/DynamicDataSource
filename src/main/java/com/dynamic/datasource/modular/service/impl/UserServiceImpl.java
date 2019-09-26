package com.dynamic.datasource.modular.service.impl;

import com.dynamic.datasource.annotation.Master;
import com.dynamic.datasource.modular.dao.UserMapper;
import com.dynamic.datasource.modular.entity.User;
import com.dynamic.datasource.modular.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: caozheng
 * @date: 2019/9/26 11:34
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUserById(int userId) {
		return userMapper.findUserById(userId);
	}

	@Override
	public void saveUserById() {
	}

	@Master
	@Override
	public List<User> listUser() {
		return userMapper.findUser();
	}
}
