package com.ipn.mx.controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ipn.mx.modelo.dao.EventoDAO;
import com.ipn.mx.modelo.dto.Evento;
import com.ipn.mx.utilities.Utils;

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

    private void mostrarEvento(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        Evento e = new Evento();
        EventoDAO dao = new EventoDAO();
        try {
            e = dao.readById(new Evento(Integer.parseInt(request.getParameter("idEvento"))));
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Lista de eventos</title>");
                out.println(
                        "<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='container'>");
                out.println("<h1>Datos del evento</h1>");
                out.println("<div class=\"card\">\n" + "  <h5 class=\"card-header\">Datos del evento</h5>\n"
                        + "  <div class=\"card-body\">\n" + "    <h5 class=\"card-title\">" + e.getNombreEvento()
                        + "</h5>\n" + "    <p class=\"card-text\">Sede: " + e.getSede() + "</p>\n"
                        + "    <p class=\"card-text\">Fecha inicio: " + e.getFechaInicio() + "</p>\n"
                        + "    <p class=\"card-text\">Fecha término: " + e.getFechaTermino() + "</p>\n"
                        + "    <a href=\"#\" class=\"btn btn-primary\">Go somewhere</a>\n" + "  </div>\n" + "</div>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void almacenarEvento(HttpServletRequest request, HttpServletResponse response) {
        Evento e = new Evento();
        e.setNombreEvento(request.getParameter("nombreEvento"));
        e.setSede(request.getParameter("sede"));
        e.setFechaInicio(Utils.convertDate(request.getParameter("fechaInicio")));
        e.setFechaTermino(Utils.convertDate(request.getParameter("fechaTermino")));

        EventoDAO dao = new EventoDAO();

        if (request.getParameter("idEvento") == null) {
            try {
                dao.create(e);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            e.setIdEvento(Integer.parseInt(request.getParameter("idEvento")));
            try {
                dao.update(e);
                listaDeEventos(request, response);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(EventoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void actualizarEvento(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EventoDAO dao = new EventoDAO();
        Evento e = new Evento();
        try {
            e = dao.readById(new Evento(Integer.parseInt(request.getParameter("idEvento"))));
            try (PrintWriter out = response.getWriter()) {
                out.print("<!DOCTYPE html>\n" + "<html>\n" + "    <head>\n"
                        + "        <title>Actualizar evento</title>\n" + "        <meta charset=\"utf-8\">\n"
                        + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n"
                        + "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\"\n"
                        + "              integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n"
                        + "    </head>\n" + "    <body>\n" + "        <div class=\"container\">\n"
                        + "            <div class=\"row justify-content-center\">\n"
                        + "                <div class=\"col-md-8\">\n" + "                    <div class=\"card\">\n"
                        + "                        <div class=\"card-header\">Actualizar evento</div>\n"
                        + "                        <div class=\"card-body\">\n"
                        + "                            <form method=\"POST\" action=\"EventoServlet?accion=guardar\">\n"
                        + "                                <div class=\"form-group row\">\n"
                        + "                                    <label for=\"nombreEvento\" \n"
                        + "                                           class=\"col-md-4 col-form-label text-md-right\">\n"
                        + "                                        Nombre Evento\n"
                        + "                                    </label>\n"
                        + "                                    <div class=\"col-md-6\">\n"
                        + "                                        <input \n"
                        + "                                            id=\"nombreEvento\" \n"
                        + "                                            type=\"text\" \n"
                        + "                                            class=\"form-control \"\n"
                        + "                                            name=\"nombreEvento\" \n"
                        + "                                            value='" + e.getNombreEvento() + "'\n"
                        + "                                            required \n"
                        + "                                            autofocus />\n"
                        + "                                    </div>\n" + "                                </div>\n"
                        + "                                <div class=\"form-group row\">\n"
                        + "                                    <label for=\"sede\" \n"
                        + "                                           class=\"col-md-4 col-form-label text-md-right\">\n"
                        + "                                        Sede Evento\n"
                        + "                                    </label>\n"
                        + "                                    <div class=\"col-md-6\">\n"
                        + "                                        <input \n"
                        + "                                            id=\"sede\" \n"
                        + "                                            type=\"text\" \n"
                        + "                                            class=\"form-control \"\n"
                        + "                                            name=\"sede\" \n"
                        + "                                            value='" + e.getSede() + "' \n"
                        + "                                            required \n"
                        + "                                            autofocus />\n"
                        + "                                    </div>\n" + "                                </div>\n"
                        + "                                <div class=\"form-group row\">\n"
                        + "                                    <label for=\"fechaInicio\" \n"
                        + "                                           class=\"col-md-4 col-form-label text-md-right\">\n"
                        + "                                        Fecha Inicio\n"
                        + "                                    </label>\n"
                        + "                                    <div class=\"col-md-6\">\n"
                        + "                                        <input \n"
                        + "                                            class=\"form-control\" \n"
                        + "                                            type=\"datetime-local\" \n"
                        + "                                            value=\"" + e.getFechaInicio() + "T00:00:00"
                        + "\"\n" + "                                            name=\"fechaInicio\"\n"
                        + "                                            id=\"fechaInicio\">\n"
                        + "                                    </div>\n" + "                                </div>\n"
                        + "                                <div class=\"form-group row\">\n"
                        + "                                    <label for=\"fechaTermino\" \n"
                        + "                                           class=\"col-md-4 col-form-label text-md-right\">\n"
                        + "                                        Fecha T&eacute;rmino\n"
                        + "                                    </label>\n"
                        + "                                    <div class=\"col-md-6\">\n"
                        + "                                        <input \n"
                        + "                                            class=\"form-control\" \n"
                        + "                                            type=\"datetime-local\" \n"
                        + "                                            value=\"" + e.getFechaTermino() + "T00:00:00"
                        + "\"\n" + "                                            name=\"fechaTermino\"\n"
                        + "                                            id=\"fechaTermino\">\n"
                        + "                                    </div>\n" + "                                </div>\n"
                        + "                                <div class=\"form-group row mb-0\">\n"
                        + "                                    <div class=\"col-md-6 offset-md-4\">\n"
                        + "                                        <button type=\"submit\" class=\"btn btn-primary\">\n"
                        + "                                            Actualizar\n"
                        + "                                        </button>\n"
                        + "                                    </div>\n" + "                                </div>\n"
                        + "<input type=\"hidden\" value=\"" + e.getIdEvento()
                        + "\" name=\"idEvento\"                                            id=\"fechaTermino\">\n"
                        + "                            </form>\n" + "                        </div>\n"
                        + "                    </div>\n" + "                </div>\n" + "            </div>\n"
                        + "        </div>\n"
                        + "        <script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\"\n"
                        + "                integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\"\n"
                        + "        crossorigin=\"anonymous\"></script>\n"
                        + "        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\"\n"
                        + "                integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\"\n"
                        + "        crossorigin=\"anonymous\"></script>\n"
                        + "        <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\"\n"
                        + "                integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\"\n"
                        + "        crossorigin=\"anonymous\"></script>\n" + "    </body>\n" + "</html>\n" + "");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EventoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void eliminarEvento(HttpServletRequest request, HttpServletResponse response) {
        EventoDAO dao = new EventoDAO();
        Evento e = new Evento();
        try {
            e.setIdEvento(Integer.parseInt(request.getParameter("idEvento")));
            dao.delete(e);
            listaDeEventos(request, response);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(EventoServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void nuevoEvento(HttpServletRequest request, HttpServletResponse response) {
    }

    private void listaDeEventos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
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
                    out.println("<td><a href='EventoServlet?accion=eliminar&idEvento=" + e.getIdEvento()
                            + "'>Eliminar</a> | <a href='EventoServlet?accion=actualizar&idEvento=" + e.getIdEvento()
                            + "'>Actualizar</a></td>");
                    out.println("</tr>");
                }
            } catch (SQLException ex) {
                out.println("<h1>Houston, we have a problem.</h1>");
                Logger.getLogger(EventoServlet.class.getName()).log(Level.SEVERE, null, ex);
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
