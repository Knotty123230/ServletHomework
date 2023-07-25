package org.example.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String format = getDate("UTC");
        if (req.getParameter("timezone") == null || req.getParameter("timezone").isEmpty()) {
            resp.getWriter().write(format);

        } else {
            String timezone = req.getParameter("timezone");
            String date = getDate(timezone);
            resp.getWriter().write(date);
        }
    }


    private String getDate(String param) {
        Date actualDate = new Date();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss z")
                .withZone(ZoneId.of(param));
        return dateFormat.format(actualDate.toInstant());
    }

}

