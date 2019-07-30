package org.salamansar.oder.module;

import java.nio.charset.StandardCharsets;
import javax.servlet.Filter;
import org.salamansar.oder.RootAppConfig;
import org.salamansar.oder.security.SecurityConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 *
 * @author Salamansar
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {SecurityConfig.class, RootAppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebAppConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/*"};
    }    

    @Override
    protected Filter[] getServletFilters() {        
        return new Filter[]{encodingFilter()};
    }
	
	
    
    private CharacterEncodingFilter encodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding(StandardCharsets.UTF_8.name());
        filter.setForceEncoding(true);
        return filter;
    }
    
}
