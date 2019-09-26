package com.dynamic.datasource.modular.service.impl;

import com.dynamic.datasource.DataSourceApplicationTests;
import com.dynamic.datasource.modular.entity.User;
import com.dynamic.datasource.modular.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @description:
 * @author: caozheng
 * @date: 2019/9/26 12:36
 */
@Slf4j
public class UserServiceImplTest extends DataSourceApplicationTests {


	@Autowired
	private IUserService userService;

	@Test
	public void getUserById() {
		User user = userService.getUserById(22);
		log.info("{}",user);
	}

	@Test
	public void saveUserById() {
		userService.saveUserById();
	}

	@Test
	public void listUser() {
		List list = userService.listUser();
		log.info("{}",list);
	}
}