package br.edu.ifpb.pweb2.pweb2.config.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class HttpMethodRequestWrapper extends HttpServletRequestWrapper {

    private final String method;

    public HttpMethodRequestWrapper(HttpServletRequest request, String method) {
        super(request);
        this.method = method.toUpperCase();
    }

    @Override
    public String getMethod() {
        return method;
    }
}
