package com.web;
import com.clientlog.IWriter;
import com.clientlog.Infrastructure;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientLogger extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException{

        response.sendRedirect("/example.html");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        IWriter writer = Infrastructure.getWriter();
        writer.setDataToWrite(request.getReader());
        Infrastructure.threadPool().execute(writer);
    }

}