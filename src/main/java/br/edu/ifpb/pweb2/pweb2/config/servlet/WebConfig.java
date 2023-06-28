package br.edu.ifpb.pweb2.pweb2.config.servlet;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private HttpMethodOverrideFilter httpMethodOverrideFilter;

    public WebConfig(HttpMethodOverrideFilter httpMethodOverrideFilter) {
        this.httpMethodOverrideFilter = httpMethodOverrideFilter;
    }

    @Bean
    public FilterRegistrationBean<HttpMethodOverrideFilter> httpMethodOverrideFilterFilterRegistrationBean() {
        FilterRegistrationBean<HttpMethodOverrideFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(httpMethodOverrideFilter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}
