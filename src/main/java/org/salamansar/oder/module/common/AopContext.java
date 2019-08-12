package org.salamansar.oder.module.common;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Salamansar
 */
@Configuration
public class AopContext {

	@Bean
	public Pointcut fillUserInfoPointcut() {
		return AnnotationMatchingPointcut.forMethodAnnotation(FillUserInfo.class);
	}
	
	@Bean
	public Advisor fillUserInfoAdvisor(@Qualifier("fillUserInfoAdvice") Advice fillUserInfoAdvise) {
		return new DefaultPointcutAdvisor(fillUserInfoPointcut(), fillUserInfoAdvise);
	}
	
	@Bean
	public DefaultAdvisorAutoProxyCreator autoProxy() {
		return new DefaultAdvisorAutoProxyCreator();
	}
	
}
