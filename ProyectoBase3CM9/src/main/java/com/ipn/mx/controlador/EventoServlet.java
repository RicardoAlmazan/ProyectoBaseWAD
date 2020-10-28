package com.ipn.mx.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipn.mx.modelo.dao.EventoDAO;
import com.ipn.mx.modelo.dto.Evento;

public class EventoServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion.equals("listaDeEventos")) {
            listaDeEventos(request, response);
        } else if (accion.equals("nuevo")) {
            nuevoEvento(request, response);
        } else if (accion.equals("eliminar")) {
            eliminarEvento(request, response);
        } else if (accion.equals("actualizar")) {
            actualizarEvento(request, response);
        } else if (accion.equals("guardar")) {
            almacenarEvento(request, response);
        } else if (accion.equals("ver")) {
            mostrarEvento(request, response);
        }
        // response.setContentType("text/html;charset=UTF-8");
        // try (PrintWriter out = response.getWriter()) {
        // /* TODO output your page here. You may use following sample code. */
        // out.println("<!DOCTYPE html>");
        // out.println("<html>");
        // out.println("<head>");
        // out.println("<title>Servlet EventoServlet</title>");
        // out.println("</head>");
        // out.println("<body>");
        // out.println("<h1>Servlet EventoServlet at " + request.getContextPath() +
        // "</h1>");
        // out.println("</body>");
        // out.println("</html>");
        // }
    }

    private void mostrarEvento(HttpServletRequest request, HttpServletResponse response) {
    }

    private void almacenarEvento(HttpServletRequest request, HttpServletResponse response) {
        Evento e = new Evento();
        e.setNombreEvento(request.getParameter("nombreEvento"));
        e.setSede(request.getParameter("sede"));
        e.setFechaInicio(Date.valueOf(request.getParameter("fechaInicio")));
        e.setFechaTermino(Date.valueOf(request.getParameter("fechaTermino")));

        EventoDAO dao = new EventoDAO();
        try {
            dao.create(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void actualizarEvento(HttpServletRequest request, HttpServletResponse response) {
    }

    private void eliminarEvento(HttpServletRequest request, HttpServletResponse response) {
    }

    private void nuevoEvento(HttpServletRequest request, HttpServletResponse response) {
    }

    private void listaDeEventos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Lista de eventos</title>");
            out.println(
                    "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h1>Lista de eventos</h1>");
            out.println("<table class=\"table table-dark\">\n" + "  <thead>\n" + "    <tr>\n"
                    + "      <th scope=\"col\">Clave de evento</th>\n"
                    + "      <th scope=\"col\">Nombre del evento</th>\n" + "      <th scope=\"col\">Sede</th>\n"
                    + "      <th scope=\"col\">Fecha de Inicio</th>\n"
                    + "      <th scope=\"col\">Fecha de Termino</th>\n" + "    </tr>\n" + "  </thead>\n"
                    + "  <tbody>\n");
            // Integer idEvento;
            // String nombreEvento;
            // String sede;
            // Date fechaInicio;
            // Date fechaTermino;

            EventoDAO dao = new EventoDAO();
            try {
                List lista = dao.readAll();
                for (int i = 0; i < lista.size(); i++) {
                    out.println("<tr>");
                    Evento e = (Evento) lista.get(i);
                    out.println("<th scope=\"row\">" + e.getIdEvento() + "</th>");
                    out.println("<td>" + e.getNombreEvento() + "</td>");
                    out.println("<td>" + e.getSede() + "</td>");
                    out.println("<td>" + e.getFechaInicio() + "</td>");
                    out.println("<td>" + e.getFechaTermino() + "</td>");
                    out.println("<td> Eliminar | Actualizar</td>");
                    out.println("</tr>");
                }
            } catch (Exception e) {
                // TODO: handle exception
            }

            out.println("</tbody>");
            out.println("</table>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
