package org.salamansar.oder.module.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author Salamansar
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		return new UserDetailsService();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() //todo: enable when login page will be ready
			.authorizeRequests()
				.anyRequest().authenticated()
				.and()
			.httpBasic()
				.and()
			.logout()
				.logoutSuccessUrl("/")
//				.deleteCookies("JSESSIONID")
				;
			
	}
}
