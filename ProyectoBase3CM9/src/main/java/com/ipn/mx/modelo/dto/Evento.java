package com.ipn.mx.modelo.dto;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Evento implements Serializable {

    private Integer idEvento;
    private String nombreEvento;
    private String sede;
    private Date fechaInicio;
    private Date fechaTermino;

    public Evento() {

    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaTermino() {
        return fechaTermino;
    }

    public void setFechaTermino(Date fechaTermino) {
        this.fechaTermino = fechaTermino;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("idEvento=").append(getIdEvento()).append("\n");
        sb.append(", nombreEvento=").append(this.nombreEvento).append("\n");
        sb.append(", sede=").append(this.sede).append("\n");
        sb.append("fechaInicio=").append(this.fechaInicio).append("\n");
        sb.append("fechaTermino=").append(this.fechaTermino).append("\n");

        return sb.toString();
    }

    public static void insertar(Connection conexion, Evento evt) {
        try {
            PreparedStatement ps = null;
            String SQL_INSERT = "INSERT INTO Evento(nombreEvento, sede, fechaInicio, fechaTermino) VALUES (?, ?, ?, ?)";

            ps = conexion.prepareStatement(SQL_INSERT);
            ps.setString(1, evt.getNombreEvento());
            ps.setString(2, evt.getSede());
            ps.setDate(3, evt.getFechaInicio());
            ps.setDate(4, evt.getFechaTermino());

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
            // TODO: handle exception
        }
    }

    public static void actualizar(Connection conexion, Evento evt) {
        try {
            PreparedStatement ps = null;
            String SQL_UPDATE = "UPDATE Evento SET nombreEvento = ?, sede = ?, fechaInicio = ?, fechaTermino = ? WHERE idEvento = ?";

            ps = conexion.prepareStatement(SQL_UPDATE);
            ps.setString(1, evt.getNombreEvento());
            ps.setString(2, evt.getSede());
            ps.setDate(3, evt.getFechaInicio());
            ps.setDate(4, evt.getFechaTermino());

            ps.setInt(5, evt.getIdEvento());

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
            // TODO: handle exception
        }
    }

    public static void borrar(Connection conexion, Evento evt) {
        try {
            PreparedStatement ps = null;
            String SQL_DELETE = "DELETE from Evento WHERE idEvento = ?";

            ps = conexion.prepareStatement(SQL_DELETE);
            ps.setInt(1, evt.getIdEvento());

            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
            // TODO: handle exception
        }
    }

    public static Evento findByIdEvento(Connection conexion, Integer idEvento) {
        Evento evt = new Evento();
        try {
            PreparedStatement ps = null;
            String SQL_FIND_BY_ID = "SELECT * FROM Evento WHERE idEvento = ?";

            ResultSet rs = null;

            ps = conexion.prepareStatement(SQL_FIND_BY_ID);
            ps.setInt(1, idEvento);

            rs = ps.executeQuery();

            while (rs.next()) {
                evt.setIdEvento(idEvento);
                evt.setNombreEvento(rs.getString("nombreEvento"));
                evt.setSede(rs.getString("sede"));
                evt.setFechaInicio(rs.getDate("fechaInicio"));
                evt.setFechaTermino(rs.getDate("fechaTermino"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
            // TODO: handle exception
        }
        return evt;
    }
}