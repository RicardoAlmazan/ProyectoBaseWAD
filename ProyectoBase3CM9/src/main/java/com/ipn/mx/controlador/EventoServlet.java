package com.ipn.mx.controlador;

import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EventoServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html; charset=UTF-8");    
        try (PrintWriter out = response.getWriter()){
            out.println("");
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
}
