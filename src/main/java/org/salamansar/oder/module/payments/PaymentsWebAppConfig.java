package org.salamansar.oder.module.payments;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 *
 * @author Salamansar
 */
@Configuration
@EnableWebMvc
@ComponentScan("org.salamansar.oder.module.payments")
public class PaymentsWebAppConfig {
    
    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setDefaultEncoding("UTF-8");
        configurer.setTemplateLoaderPath("/WEB-INF/templates");
        return configurer;
    }
    
    @Bean
    public FreeMarkerViewResolver freeMarkerResolver() {
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setContentType("text/html; charset=utf-8");
        viewResolver.setSuffix(".ftl");
        return viewResolver;
    }
    
}
