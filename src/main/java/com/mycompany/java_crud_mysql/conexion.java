/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_crud_mysql;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class conexion {

    private Connection conectar = null;
    private String usuario;
    private String contrasenia;
    private String bd;
    private String ip;
    private String puerto;

    public conexion() {
        cargarConfiguracion();
    }

        private void cargarConfiguracion() {
        try (InputStream input = new FileInputStream("src/main/java/com/mycompany/java_crud_mysql/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            usuario = prop.getProperty("usuario");
            contrasenia = prop.getProperty("contrasenia");
            bd = prop.getProperty("bd");
            ip = prop.getProperty("ip");
            puerto = prop.getProperty("puerto");

        } catch (Exception e) {
            throw new RuntimeException("Error al cargar la configuración: " + e.getMessage(), e);
        }
    }

    public Connection estableceConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conectar = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/" + bd, usuario, contrasenia);
            System.out.println("Conexión establecida correctamente");
            return conectar;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Error al establecer la conexión: " + e.getMessage(), e);
        }
    }

    public void cerrarConexion() {
        if (conectar != null) {
            try {
                conectar.close();
                System.out.println("Conexión cerrada correctamente");
            } catch (SQLException e) {
                throw new RuntimeException("Error al cerrar la conexión: " + e.getMessage(), e);
            }
        }
    }
}


