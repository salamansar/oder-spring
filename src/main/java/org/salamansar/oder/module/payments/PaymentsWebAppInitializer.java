package org.salamansar.oder.module.payments;

import org.salamansar.oder.RootAppConfig;
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
}
