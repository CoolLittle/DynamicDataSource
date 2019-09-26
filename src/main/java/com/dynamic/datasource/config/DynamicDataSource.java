package com.dynamic.datasource.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @description:
 * @author: caozheng
 * @date: 2019/7/23 10:27
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * Determine the current lookup key. This will typically be
	 * implemented to check a thread-bound transaction context.
	 * <p>Allows for arbitrary keys. The returned key needs
	 * to match the stored lookup key type, as resolved by the
	 * {@link #resolveSpecifiedLookupKey} method.
	 */
	/**
	 * 绑定数据源到当前线程，如果 DynamicDataSourceHolder.getDataSource()返回null，则使用默认数据源
	 * @return
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceHolder.getDataSource();
	}
}
