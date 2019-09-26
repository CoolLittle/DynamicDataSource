package com.dynamic.datasource.config;

import com.dynamic.datasource.constant.DBType;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: caozheng
 * @date: 2019/7/23 10:28
 */
@Slf4j
public class DynamicDataSourceHolder {

	public static final ThreadLocal<DBType> contextHolder = new ThreadLocal<>();

	/**
	 * 主库访问计数
	 */
	private static final AtomicInteger masterCounter = new AtomicInteger(-1);

	/**
	 * 主库数量
	 */
	private static final int masterCount = 1;
	/**
	 * 从库访问计数
	 */
	private static final AtomicInteger slaveCounter = new AtomicInteger(-1);

	/**
	 * 从库数量,暂时设置为2个从库
	 */
	private static final int slaveCount = 2;

	/**
	 * 设置数据源
	 * @param name
	 */
	public static void putDataSource(DBType name) {
		contextHolder.set(name);
	}

	/**
	 * 获取数据源
	 * @return
	 */
	public static DBType getDataSource() {
		return contextHolder.get();
	}


	/**
	 * 切换到主库，只支持一主配置，多个主库需要自己实现访问规则
	 */
	public static void master() {
		putDataSource(DBType.MASTER);
		if(log.isDebugEnabled()){
			log.debug("切换到master");
		}
	}

	/**
	 * 切换到从库，从库轮询访问
	 */
	public static void slave() {
		//  轮询访问从库
		int index = slaveCounter.getAndIncrement() % slaveCount;
		if (slaveCounter.get() > 9999) {
			slaveCounter.set(-1);
		}

		if (index == 0) {
			putDataSource(DBType.SLAVE1);
			if(log.isDebugEnabled()){
				log.debug("切换到slave1");
			}
		}else {
			putDataSource(DBType.SLAVE2);
			if(log.isDebugEnabled()){
				log.debug("切换到slave2");
			}
		}
	}

}
