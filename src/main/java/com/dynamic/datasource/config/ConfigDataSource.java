package com.dynamic.datasource.config;

import com.dynamic.datasource.constant.DBType;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * 		配置动态数据源
 * @author: caozheng
 * @date: 2019/9/26 11:38
 */
@Configuration
@MapperScan(basePackages = "com.dynamic.datasource.modular.dao")
@Slf4j
public class ConfigDataSource {

	/**
	 * 创建主库数据源
	 * @return
	 */
	@Bean(name="masterDataSource")
	@Primary
	@ConfigurationProperties(prefix = "master.datasource.druid")
	public DataSource masterDataSource(){
		return DataSourceBuilder.create().build();
	}

	/**
	 * 创建从库数据源
	 * @return
	 */
	@Bean(name="slaveDataSource1")
	@ConfigurationProperties(prefix = "slave1.datasource.druid")
	public DataSource slaveDataSource1(){
		return DataSourceBuilder.create().build();
	}
	/**
	 * 创建从库数据源
	 * @return
	 */
	@Bean(name="slaveDataSource2")
	@ConfigurationProperties(prefix = "slave1.datasource.druid")
	public DataSource slaveDataSource2(){
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "dataSource")
	public DynamicDataSource dataSource() {

		//这个地方是比较核心的targetDataSource 集合是我们数据库和名字之间的映射
		Map<Object, Object> targetDataSource = new HashMap<>();
		targetDataSource.put(DBType.MASTER, masterDataSource());
		targetDataSource.put(DBType.SLAVE1, slaveDataSource1());
		targetDataSource.put(DBType.SLAVE2, slaveDataSource2());
		DynamicDataSource dataSource = new DynamicDataSource();
		//设置默认数据源
		dataSource.setDefaultTargetDataSource(masterDataSource());
		dataSource.setTargetDataSources(targetDataSource);
		return dataSource;
	}

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory()
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource());
	   bean.setMapperLocations(
				new PathMatchingResourcePatternResolver()
						.getResources("classpath*:com/dynamic/datasource/modular/dao/mapping/*Mapper.xml"));
		//设置我们的xml文件路径
		return bean.getObject();
	}

}
