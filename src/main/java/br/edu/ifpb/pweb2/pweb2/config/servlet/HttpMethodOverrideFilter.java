package br.edu.ifpb.pweb2.pweb2.config.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpMethodOverrideFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String httpMethod = httpServletRequest.getParameter("_method");

        if(httpMethod != null) {
            httpServletRequest = new HttpMethodRequestWrapper(httpServletRequest, httpMethod);
        }
        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
