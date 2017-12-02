package org.salamansar.oder.module.payments;

import java.nio.charset.StandardCharsets;
import javax.servlet.Filter;
import org.salamansar.oder.RootAppConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 *
 * @author Salamansar
 */
public class PaymentsWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] {RootAppConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{PaymentsWebAppConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/payments/*"};
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
