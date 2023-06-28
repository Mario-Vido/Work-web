package com.example.web.Filters;

import com.example.web.Service.ServerService;
import com.example.web.Service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebFilter(urlPatterns = {"/cypher-log-for-admin"})
public class CypherLogForAdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        List<String> userRole = (List<String>) request.getSession().getAttribute("role");

        if(userRole.contains("Admin")){
            response.sendRedirect("/table");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
