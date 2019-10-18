package com.dynamic.datasource.aop;

import com.dynamic.datasource.config.DynamicDataSourceHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @description:	代理切入点
 * @author: caozheng
 * @date: 2019/7/23 14:28
 */
@Aspect
@Component
@Slf4j
public class DataSourceAop {

	/**
	 * 只读：
	 * 不是Master注解的对象或方法  && select开头的方法  ||  get开头的方法
	 */
	@Pointcut("!@annotation(com.dynamic.datasource.annotation.Master) "       +
			 " && !@annotation(org.springframework.transaction.annotation.Transactional) " +
			 " && ( @annotation(com.dynamic.datasource.annotation.Slave) " +
			 " || execution(* com.dynamic.datasource.modular.service..*.select*(..))" +
			 " || execution(* com.dynamic.datasource.modular.service..*.get*(..))"   +
			 " || execution(* com.dynamic.datasource.modular.service..*.find*(..))"   +
			 " || execution(* com.dynamic.datasource.modular.service..*.check*(..))"  +
			 " || execution(* com.dynamic.datasource.modular.service..*.verify*(..))" +
			 " || execution(* com.dynamic.datasource.modular.service..*.list*(..))"   +
			 " || execution(* com.dynamic.datasource.modular.service..*.count*(..))"  +
			 " || execution(* com.dynamic.datasource.modular.service..*.query*(..)))")
	public void readPointcut() {

	}

	/**
	 * 写：
	 * Master注解的对象或方法 || insert开头的方法  ||  add开头的方法 || update开头的方法
	 * || edlt开头的方法 || delete开头的方法 || remove开头的方法
	 */
	@Pointcut("!@annotation(com.dynamic.datasource.annotation.Slave) "       +
			"&& (@annotation(com.dynamic.datasource.annotation.Master) " +
			"|| @annotation(org.springframework.transaction.annotation.Transactional)  " +
			"|| execution(* com.dynamic.datasource.modular.service..*.gen*(..)) " +
			"|| execution(* com.dynamic.datasource.modular.service..*.refund*(..)) " +
			"|| execution(* com.dynamic.datasource.modular.service..*.clean*(..)) " +
			"|| execution(* com.dynamic.datasource.modular.service..*.cancel*(..)) " +
			"|| execution(* com.dynamic.datasource.modular.service..*.disband*(..)) " +
			"|| execution(* com.dynamic.datasource.modular.service..*.syn*(..)) " +
			"|| execution(* com.dynamic.datasource.modular.service..*.save*(..)) " +
			"|| execution(* com.dynamic.datasource.modular.service..*.insert*(..)) " +
			"|| execution(* com.dynamic.datasource.modular.service..*.add*(..)) " +
			"|| execution(* com.dynamic.datasource.modular.service..*.update*(..)) " +
			"|| execution(* com.dynamic.datasource.modular.service..*.edit*(..)) " +
			"|| execution(* com.dynamic.datasource.modular.service..*.delete*(..)) " +
			"|| execution(* com.dynamic.datasource.modular.service..*.create*(..)) " +
			"|| execution(* com.dynamic.datasource.modular.service..*.remove*(..)))")
	public void writePointcut() {

	}

	/**
	 * 读取从库数据
	 * @param joinPoint
	 */
	@Before("readPointcut()")
	public void read(JoinPoint joinPoint) {

		log.debug("从库：{}",joinPoint.getSignature().getDeclaringTypeName()+ "." + joinPoint.getSignature().getName()+"()");
		DynamicDataSourceHolder.slave();
	}

	/**
	 * 读取主库数据
	 * @param joinPoint
	 */
	@Before("writePointcut()")
	public void write(JoinPoint joinPoint) {

		log.debug("主库：{}",joinPoint.getSignature().getDeclaringTypeName()+ "." + joinPoint.getSignature().getName()+"()");

		DynamicDataSourceHolder.master();
	}

	/**
	 * 修复ThreadLocal未清空导致诡异事情--写操作会写到主库
	 */
	@AfterReturning("readPointcut()")
	public void readAfterReturning(){
		DynamicDataSourceHolder.clear();
	}
	@AfterReturning("writePointcut()")
	public void writeAfterReturning(){
		DynamicDataSourceHolder.clear();
	}

}
