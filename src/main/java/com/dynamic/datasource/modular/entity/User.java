package com.dynamic.datasource.modular.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: caozheng
 * @date: 2019/9/26 14:26
 */
@Data
public class User implements Serializable {

	private Integer userId;

	private String userName;

}
