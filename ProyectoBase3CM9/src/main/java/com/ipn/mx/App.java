package com.ipn.mx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ipn.mx.modelo.dto.Evento;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        Evento evt = new Evento();
        evt.setIdEvento(1);
        evt.setNombreEvento("Jornada ISC");
        evt.setSede("Auditorio");
        System.out.println(evt.toString());
        System.out.println("Hello World!");

        String user = "root";
        String pwd = "n0m3l0s3";
        String url = "jdbc:mysql://localhost:3306/3CM9?serverTimezone=America/Mexico_City&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&useSSL=false";
        String driverMysql = "com.mysql.cj.jdbc.Driver";
        // String driverMysql = "com.mysql.jdbc.Driver";
        Connection conexion = null;
        try {
            Class.forName(driverMysql);
            conexion = DriverManager.getConnection(url, user, pwd);

            //
            //System.out.println(Evento.findByIdEvento(conexion, evt.getIdEvento()).toString());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Evento.class.getName()).log(Level.SEVERE, null, ex);
            // TODO: handle exception
        }
    }
}
