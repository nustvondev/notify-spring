package vn.com.notification.api.config;

import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import vn.com.notification.api.handler.HeaderCifNumberArgumentResolver;
import vn.com.notification.common.filter.RequestIdMdcFilter;

@Configuration
public class FilterBeanConfiguration implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<RequestIdMdcFilter> requestIdMdcFilter() {
        FilterRegistrationBean<RequestIdMdcFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RequestIdMdcFilter());
        registrationBean.addUrlPatterns("*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new HeaderCifNumberArgumentResolver());
    }
}
