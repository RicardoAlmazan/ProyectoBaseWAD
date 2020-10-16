package com.ipn.mx.modelo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ipn.mx.modelo.dto.Evento;

public class EventoDAO {
    private static String SQL_INSERT = "INSERT INTO Evento(nombreEvento, sede, fechaInicio, fechaTermino) VALUES (?, ?, ?, ?)";
    private static String SQL_UPDATE = "UPDATE Evento SET nombreEvento = ?, sede = ?, fechaInicio = ?, fechaTermino = ? WHERE idEvento = ?";
    private static String SQL_DELETE = "DELETE from Evento WHERE idEvento = ?";
    private static String SQL_SELECT = "SELECT * FROM Evento WHERE idEvento = ?";
    private static String SQL_SELECT_ALL = "SELECT * FROM Evento";

    private Connection con;

    private void getConnection() {
        String user = "root";
        String pwd = "n0m3l0s3";
        String url = "jdbc:mysql://localhost:3306/3CM9?serverTimezone=America/Mexico_City&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&useSSL=false";
        String driverMysql = "com.mysql.cj.jdbc.Driver";
        // String driverMysql = "com.mysql.jdbc.Driver";
        try {
            Class.forName(driverMysql);
            con = DriverManager.getConnection(url, user, pwd);

            //
            // System.out.println(Evento.findByIdEvento(conexion,
            // evt.getIdEvento()).toString());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EventoDAO.class.getName()).log(Level.SEVERE, null, ex);
            // TODO: handle exception
        }
    }

    public void create(Evento evt) throws SQLException {
        this.getConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(SQL_INSERT);
            ps.setString(1, evt.getNombreEvento());
            ps.setString(2, evt.getSede());
            ps.setDate(3, evt.getFechaInicio());
            ps.setDate(4, evt.getFechaTermino());

            ps.executeUpdate();
        } catch (Exception e) {

        } finally {
            if (ps != null)
                ps.close();
            if (con != null)
                con.close();
        }
    }

    public void update(Evento evt) throws SQLException {
        this.getConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(SQL_UPDATE);
            ps.setString(1, evt.getNombreEvento());
            ps.setString(2, evt.getSede());
            ps.setDate(3, evt.getFechaInicio());
            ps.setDate(4, evt.getFechaTermino());

            ps.setInt(5, evt.getIdEvento());

            ps.executeUpdate();
        } catch (Exception e) {

        } finally {
            if (ps != null)
                ps.close();
            if (con != null)
                con.close();
        }
    }

    public void delete(Evento evt) throws SQLException {
        this.getConnection();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(SQL_DELETE);
            ps.setInt(1, evt.getIdEvento());

            ps.executeUpdate();
        } catch (Exception e) {

        } finally {
            if (ps != null)
                ps.close();
            if (con != null)
                con.close();
        }
    }

    public List readAll() throws SQLException {
        this.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(SQL_SELECT_ALL);
            rs = ps.executeQuery();
            List resultados = this.obtenerResultados(rs);
            if (resultados.size() > 0)
                return resultados;
        } catch (Exception e) {

        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (con != null)
                con.close();
        }
        return null;
    }

    public Evento readById(Evento evt) throws SQLException {
        this.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement(SQL_SELECT);
            ps.setInt(1, evt.getIdEvento());
            rs = ps.executeQuery();
            List resultados = this.obtenerResultados(rs);
            if (resultados.size() > 0)
                return (Evento) resultados.get(0);
        } catch (Exception e) {

        } finally {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (con != null)
                con.close();
        }
        return null;
    }

    private List obtenerResultados(ResultSet rs) throws SQLException {
        List resultados = new ArrayList<>();
        while(rs.next()){
            Evento evt = new Evento();
            evt.setIdEvento(rs.getInt("idEvento"));
            evt.setNombreEvento(rs.getString("nombreEvento"));
            evt.setSede(rs.getString("sede"));
            evt.setFechaInicio(rs.getDate("fechaInicio"));
            evt.setFechaTermino(rs.getDate("fechaTermino"));
            resultados.add(evt);
        }
        return resultados;
    }

    public static void main(String[] args) {
        Evento e = new Evento();
        e.setNombreEvento("Torneo de Ajedrez");    
        e.setSede("Pasillo de culturales");
        e.setFechaInicio(Date.valueOf("2020-10-16"));
        e.setFechaTermino(Date.valueOf("2020-10-17"));

        EventoDAO dao = new EventoDAO();
        try {
            dao.create(e);
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }
}
