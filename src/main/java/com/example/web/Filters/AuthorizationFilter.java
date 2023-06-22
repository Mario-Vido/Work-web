package com.example.web.Filters;

import com.example.web.Service.LoginService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/index.jsp"})
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException{
        LoginService service = new LoginService();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String isAuthorized = service.checkUserAuthorization(request);

        if (isAuthorized.equals("Admin")) {
            response.sendRedirect(request.getContextPath() + "/table");
        } else if(isAuthorized.equals("User")) {
            response.sendRedirect(request.getContextPath() + "/table");
        }
    }
}

