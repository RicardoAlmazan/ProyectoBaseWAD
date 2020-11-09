package com.ipn.mx.controlador;

import com.ipn.mx.modelo.dao.EventoDAO;
import com.ipn.mx.modelo.dto.Evento;
import com.ipn.mx.utilities.Utils;
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

/**
 *
 * @author Richie
 */
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
                out.println("<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">\n"
                        + "            <nav class=\"navbar navbar-dark bg-dark\">\n"
                        + "                <a class=\"navbar-brand\" href=\"/3cm9\">\n"
                        + "                    <img src=\"assets/img/bootstrap-solid.svg\" width=\"30\" height=\"30\" class=\"d-inline-block align-top\" alt=\"\" >\n"
                        + "                    Eventos\n"
                        + "                </a>\n"
                        + "            </nav>\n"
                        + "\n"
                        + "            <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n"
                        + "                <span class=\"navbar-toggler-icon\"></span>\n"
                        + "            </button>\n"
                        + "\n"
                        + "            <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n"
                        + "                <ul class=\"navbar-nav mr-auto\">\n"
                        + "                    <li class=\"nav-item active\">\n"
                        + "                        <a class=\"nav-link\" href=\"EventoServlet?accion=listaDeEventos\">Lista de eventos <span class=\"sr-only\">(current)</span></a>\n"
                        + "                    </li>\n"
                        + "                </ul>\n"
                        + "            </div>\n"
                        + "        </nav>");
                out.println("<div class='container'>");
                out.println("<h1>Datos del evento</h1>");
                out.println("<div class=\"card\">\n"
                        + "  <h5 class=\"card-header\">Datos del evento</h5>\n"
                        + "  <div class=\"card-body\">\n"
                        + "    <h5 class=\"card-title\">" + e.getNombreEvento() + "</h5>\n"
                        + "    <p class=\"card-text\">Sede: " + e.getSede() + "</p>\n"
                        + "    <p class=\"card-text\">Fecha inicio: " + e.getFechaInicio() + "</p>\n"
                        + "    <p class=\"card-text\">Fecha término: " + e.getFechaTermino() + "</p>\n"
                        + "    <a href=\"/EventoServlet?accion=listaDeEventos\" class=\"btn btn-primary\">Regresar</a>\n"
                        + "    <a href='EventoServlet?accion=actualizar&idEvento=" + e.getIdEvento() + "' class=\"btn btn-warning\">Actualizar</a>\n"
                        + "    <a href='EventoServlet?accion=eliminar&idEvento=" + e.getIdEvento() + "' class=\"btn btn-danger\">Eliminar</a>\n"
                        + "  </div>\n"
                        + "</div>");
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
                try (PrintWriter out = response.getWriter()) {
                    out.print("<!DOCTYPE html>\n"
                            + "<html>\n"
                            + "    <head>\n"
                            + "        <title>Actualizar evento</title>\n"
                            + "        <meta charset=\"utf-8\">\n"
                            + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n"
                            + "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\"\n"
                            + "              integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n"
                            + "    </head>\n"
                            + "    <body>\n");
                    out.println("<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">\n"
                            + "            <nav class=\"navbar navbar-dark bg-dark\">\n"
                            + "                <a class=\"navbar-brand\" href=\"/3cm9\">\n"
                            + "                    <img src=\"assets/img/bootstrap-solid.svg\" width=\"30\" height=\"30\" class=\"d-inline-block align-top\" alt=\"\" >\n"
                            + "                    Eventos\n"
                            + "                </a>\n"
                            + "            </nav>\n"
                            + "\n"
                            + "            <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n"
                            + "                <span class=\"navbar-toggler-icon\"></span>\n"
                            + "            </button>\n"
                            + "\n"
                            + "            <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n"
                            + "                <ul class=\"navbar-nav mr-auto\">\n"
                            + "                    <li class=\"nav-item active\">\n"
                            + "                        <a class=\"nav-link\" href=\"EventoServlet?accion=listaDeEventos\">Lista de eventos <span class=\"sr-only\">(current)</span></a>\n"
                            + "                    </li>\n"
                            + "                </ul>\n"
                            + "            </div>\n"
                            + "        </nav>");
                    out.println("<div class=\"container\">\n"
                            + "<h2>¡Se ha registrado correctamente el evento " + e.getNombreEvento() + "!</h2>"
                            + "<a class=\"btn btn-success\" href='EventoServlet?accion=listaDeEventos'>Ver eventos</a>"
                            + "</div>");
                }
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
                out.print("<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "    <head>\n"
                        + "        <title>Actualizar evento</title>\n"
                        + "        <meta charset=\"utf-8\">\n"
                        + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n"
                        + "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\"\n"
                        + "              integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n"
                        + "    </head>\n"
                        + "    <body>\n");
                out.println("<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">\n"
                        + "            <nav class=\"navbar navbar-dark bg-dark\">\n"
                        + "                <a class=\"navbar-brand\" href=\"/3cm9\">\n"
                        + "                    <img src=\"assets/img/bootstrap-solid.svg\" width=\"30\" height=\"30\" class=\"d-inline-block align-top\" alt=\"\" >\n"
                        + "                    Eventos\n"
                        + "                </a>\n"
                        + "            </nav>\n"
                        + "\n"
                        + "            <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n"
                        + "                <span class=\"navbar-toggler-icon\"></span>\n"
                        + "            </button>\n"
                        + "\n"
                        + "            <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n"
                        + "                <ul class=\"navbar-nav mr-auto\">\n"
                        + "                    <li class=\"nav-item active\">\n"
                        + "                        <a class=\"nav-link\" href=\"EventoServlet?accion=listaDeEventos\">Lista de eventos <span class=\"sr-only\">(current)</span></a>\n"
                        + "                    </li>\n"
                        + "                </ul>\n"
                        + "            </div>\n"
                        + "        </nav>");
                out.println("<div class=\"container\">\n"
                        + "            <div class=\"row justify-content-center\">\n"
                        + "                <div class=\"col-md-8\">\n"
                        + "                    <div class=\"card\">\n"
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
                        + "                                    </div>\n"
                        + "                                </div>\n"
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
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <div class=\"form-group row\">\n"
                        + "                                    <label for=\"fechaInicio\" \n"
                        + "                                           class=\"col-md-4 col-form-label text-md-right\">\n"
                        + "                                        Fecha Inicio\n"
                        + "                                    </label>\n"
                        + "                                    <div class=\"col-md-6\">\n"
                        + "                                        <input \n"
                        + "                                            class=\"form-control\" \n"
                        + "                                            type=\"datetime-local\" \n"
                        + "                                            value=\"" + e.getFechaInicio() + "T00:00:00" + "\"\n"
                        + "                                            name=\"fechaInicio\"\n"
                        + "                                            id=\"fechaInicio\">\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <div class=\"form-group row\">\n"
                        + "                                    <label for=\"fechaTermino\" \n"
                        + "                                           class=\"col-md-4 col-form-label text-md-right\">\n"
                        + "                                        Fecha T&eacute;rmino\n"
                        + "                                    </label>\n"
                        + "                                    <div class=\"col-md-6\">\n"
                        + "                                        <input \n"
                        + "                                            class=\"form-control\" \n"
                        + "                                            type=\"datetime-local\" \n"
                        + "                                            value=\"" + e.getFechaTermino() + "T00:00:00" + "\"\n"
                        + "                                            name=\"fechaTermino\"\n"
                        + "                                            id=\"fechaTermino\">\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "                                <div class=\"form-group row mb-0\">\n"
                        + "                                    <div class=\"col-md-6 offset-md-4\">\n"
                        + "                                        <button type=\"submit\" class=\"btn btn-primary\">\n"
                        + "                                            Actualizar\n"
                        + "                                        </button>\n"
                        + "                                    </div>\n"
                        + "                                </div>\n"
                        + "<input type=\"hidden\" value=\"" + e.getIdEvento() + "\" name=\"idEvento\"                                            id=\"fechaTermino\">\n"
                        + "                            </form>\n"
                        + "                        </div>\n"
                        + "                    </div>\n"
                        + "                </div>\n"
                        + "            </div>\n"
                        + "        </div>\n"
                        + "        <script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\"\n"
                        + "                integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\"\n"
                        + "        crossorigin=\"anonymous\"></script>\n"
                        + "        <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\"\n"
                        + "                integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\"\n"
                        + "        crossorigin=\"anonymous\"></script>\n"
                        + "        <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\"\n"
                        + "                integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\"\n"
                        + "        crossorigin=\"anonymous\"></script>\n"
                        + "    </body>\n"
                        + "</html>\n"
                        + ""
                );
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
            Logger.getLogger(EventoServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void nuevoEvento(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("eventosForm.html");
    }

    private void listaDeEventos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Lista de eventos</title>");
            out.println("<link rel=\"stylesheet\" href=\"https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css\" integrity=\"sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2\" crossorigin=\"anonymous\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">\n"
                    + "            <nav class=\"navbar navbar-dark bg-dark\">\n"
                    + "                <a class=\"navbar-brand\" href=\"/3cm9\">\n"
                    + "                    <img src=\"assets/img/bootstrap-solid.svg\" width=\"30\" height=\"30\" class=\"d-inline-block align-top\" alt=\"\" >\n"
                    + "                    Eventos\n"
                    + "                </a>\n"
                    + "            </nav>\n"
                    + "\n"
                    + "            <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n"
                    + "                <span class=\"navbar-toggler-icon\"></span>\n"
                    + "            </button>\n"
                    + "\n"
                    + "            <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n"
                    + "                <ul class=\"navbar-nav mr-auto\">\n"
                    + "                    <li class=\"nav-item active\">\n"
                    + "                        <a class=\"nav-link\" href=\"EventoServlet?accion=listaDeEventos\">Lista de eventos <span class=\"sr-only\">(current)</span></a>\n"
                    + "                    </li>\n"
                    + "                </ul>\n"
                    + "            </div>\n"
                    + "        </nav>");
            out.println("<div class='container'>");
            out.println("<h1>Lista de eventos</h1>");
            out.println("<a href='EventoServlet?accion=nuevo' class='btn btn-danger'>Nuevo</a>");
            out.println("<table class=\"table table-dark\">\n"
                    + "  <thead class='thead-light'>\n"
                    + "    <tr>\n"
                    + "      <th scope=\"col\">Clave de evento</th>\n"
                    + "      <th scope=\"col\">Nombre del evento</th>\n"
                    + "      <th scope=\"col\">Sede</th>\n"
                    + "      <th scope=\"col\">Fecha de Inicio</th>\n"
                    + "      <th scope=\"col\">Fecha de Termino</th>\n"
                    + "      <th scope=\"col\">Acción</th>\n"
                    + "    </tr>\n"
                    + "  </thead>\n"
                    + "  <tbody>\n");

            EventoDAO dao = new EventoDAO();
            try {
                List lista = dao.readAll();
                for (int i = 0; i < lista.size(); i++) {
                    out.println("<tr>");
                    Evento e = (Evento) lista.get(i);
                    out.println("<th scope=\"row\">" + e.getIdEvento() + "</th>\n");
                    out.println("<td>" + e.getNombreEvento() + "</td>");
                    out.println("<td>" + e.getSede() + "</td>");
                    out.println("<td>" + e.getFechaInicio() + "</td>");
                    out.println("<td>" + e.getFechaTermino() + "</td>");
                    out.println("<td><a class=\"btn btn-danger\" href='EventoServlet?accion=eliminar&idEvento=" + e.getIdEvento() + "'>Eliminar</a>");
                    out.println("<a class=\"btn btn-warning\" href='EventoServlet?accion=actualizar&idEvento=" + e.getIdEvento() + "'>Actualizar</a>");
                    out.println("<a href='EventoServlet?accion=ver&idEvento=" + e.getIdEvento() + "' class=\"btn btn-info\">Ver</a>");
                    out.println("</td>");
                    out.println("</tr>");
                }
            } catch (SQLException ex) {
                out.println("<h1>Houston, we have a problem.</h1>");
                Logger.getLogger(EventoServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            out.println("  </tbody>\n"
                    + "</table>");

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}