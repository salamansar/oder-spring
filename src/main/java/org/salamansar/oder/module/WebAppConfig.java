package org.salamansar.oder.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.salamansar.oder.utils.JsonMarshaller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 *
 * @author Salamansar
 */
@Configuration
@EnableWebMvc
@ComponentScan("org.salamansar.oder.module")
public class WebAppConfig implements WebMvcConfigurer {
    
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
    
    @Bean
    public ObjectMapper jsonMapper() {
        ObjectMapper mapper = new ObjectMapper();        
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        return mapper;
    }

    @Bean
    public JsonMarshaller utilsJsonMarshaller() {
        return new JsonMarshaller(jsonMapper());
    }

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/public/**")
				.addResourceLocations("/public/");
		registry.addResourceHandler("/static/**")
				.addResourceLocations("/");
	}
    
}
