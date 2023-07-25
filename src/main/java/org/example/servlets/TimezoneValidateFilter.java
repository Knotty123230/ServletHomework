package org.example.servlets;


import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Set;

@WebFilter("/time")
public class TimezoneValidateFilter implements jakarta.servlet.Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        jakarta.servlet.Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        String timezone = servletRequest.getParameter("timezone");
        if (timezone == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        boolean b = validTimeZone(timezone);
        if (!b) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            response.setStatus(400);
            response.getWriter().write("Invalid timezone!" + " STATUS CODE = " + response.getStatus());
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public boolean validTimeZone(String timezone) {
        boolean avaliable = false;
        Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
        System.out.println(availableZoneIds);
        for (String availableZoneId : availableZoneIds) {
            if (availableZoneId.contains(timezone)) {
                avaliable = true;
                break;
            }
        }
        return avaliable;
    }

    @Override
    public void destroy() {
        jakarta.servlet.Filter.super.destroy();
    }
}

