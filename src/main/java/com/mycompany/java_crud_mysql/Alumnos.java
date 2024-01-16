/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.java_crud_mysql;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author lalit
 * 
 * jaja
 */
public class Alumnos {

    int codigo;
    String nombreAlumnos;
    String apellidoAlumnos;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombreAlumnos() {
        return nombreAlumnos;
    }

    public void setNombreAlumnos(String nombreAlumnos) {
        this.nombreAlumnos = nombreAlumnos;
    }

    public String getApellidoAlumnos() {
        return apellidoAlumnos;
    }

    public void setApellidoAlumnos(String apellidoAlumnos) {
        this.apellidoAlumnos = apellidoAlumnos;
    }

    public void InsertarAlumno(JTextField paramNombres, JTextField paramApellidos) {
        // Obtén el texto de los JTextField
        String nombres = paramNombres.getText();
        String apellidos = paramApellidos.getText();

        // Verificamos si alguno de los campos está vacío
        if (nombres.isEmpty() || apellidos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;  // Sale del método si hay campos vacíos
        }

        // Verifica si los campos contienen solo letras y espacios
        if (!validarTextoAlfabetico(nombres) || !validarTextoAlfabetico(apellidos)) {
            JOptionPane.showMessageDialog(null, "Los nombres y apellidos deben contener solo letras y espacios.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return;  // Sale del método si hay caracteres no permitidos
        }

        // Continúa con el proceso de inserción si no hay problemas de validación
        setNombreAlumnos(nombres);
        setApellidoAlumnos(apellidos);

        conexion objetoConexion = new conexion();

        String consulta = "INSERT INTO Alumnos (nombres, apellidos) VALUES (?, ?);";

        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, getNombreAlumnos());
            cs.setString(2, getApellidoAlumnos());

            cs.execute();

            JOptionPane.showMessageDialog(null, "Se insertó correctamente el alumno");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se insertó correctamente el alumno, error: " + e.toString());
        }
    }

    // Función para validar que un texto contenga solo letras y espacios
    private boolean validarTextoAlfabetico(String texto) {
        // Patrón que permite solo letras y espacios
        String patron = "^[a-zA-Z ]+$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(texto);
        return matcher.matches();
    }

    public void MostrarAlumnos(JTable paramTablaTotalAlumnos) {

        conexion objetoConexion = new conexion();

        DefaultTableModel modelo = new DefaultTableModel();

        TableRowSorter<TableModel> ordenarTabla = new TableRowSorter<TableModel>(modelo);

        paramTablaTotalAlumnos.setRowSorter(ordenarTabla);

        String sql = "";

        modelo.addColumn("id");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");

        paramTablaTotalAlumnos.setModel(modelo);

        sql = "select * from Alumnos;";

        String[] datos = new String[3];
        Statement st;

        try {

            st = objetoConexion.estableceConexion().createStatement();

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);

                modelo.addRow(datos);

            }

            paramTablaTotalAlumnos.setModel(modelo);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "no se pudieron mostrar los registros, error:" + e.toString());
        }

    }

    public void seleccionarAlumno(JTable paramTablaAlumnos, JTextField paramId, JTextField paramNombres, JTextField paramApellidos) {

        try {

            int fila = paramTablaAlumnos.getSelectedRow();

            if (fila >= 0) {

                paramId.setText(paramTablaAlumnos.getValueAt(fila, 0).toString());
                paramNombres.setText(paramTablaAlumnos.getValueAt(fila, 1).toString());
                paramApellidos.setText(paramTablaAlumnos.getValueAt(fila, 2).toString());

            } else {

                JOptionPane.showMessageDialog(null, "Fila no seleccionada");

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error de seleccion, error:" + e.toString());

        }

    }

    public void modificarAlumnos(JTextField paramCodigo, JTextField paramNombres, JTextField paramApellidos) {
        // Verificamos si se ha seleccionado un código de alumno
        if (paramCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un alumno para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        setCodigo(Integer.parseInt(paramCodigo.getText()));
        setNombreAlumnos(paramNombres.getText());
        setApellidoAlumnos(paramApellidos.getText());

        conexion objetoConexion = new conexion();

        String consulta = "update Alumnos SET alumnos.nombres =?, alumnos.apellidos =? where alumnos.id =?;";

        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);

            cs.setString(1, getNombreAlumnos());
            cs.setString(2, getApellidoAlumnos());
            cs.setInt(3, getCodigo());

            cs.execute();
            JOptionPane.showMessageDialog(null, "Modificación exitosa");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se modificó el registro: " + e.toString());
        }
    }

    public void EliminarAlumnos(JTextField paramCodigo) {
        // Verifica si se ha seleccionado un código de alumno
        if (paramCodigo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un alumno para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        setCodigo(Integer.parseInt(paramCodigo.getText()));

        conexion objetoConexion = new conexion();

        String consulta = "DELETE from alumnos where alumnos.id=?;";

        try {
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, getCodigo());
            cs.execute();

            JOptionPane.showMessageDialog(null, "Se eliminó correctamente el alumno");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar, error: " + e.toString());
        }
    }

}
