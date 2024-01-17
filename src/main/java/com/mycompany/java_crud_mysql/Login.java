/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.java_crud_mysql;

import com.mysql.cj.protocol.Resultset;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author lalit
 */
public class Login extends javax.swing.JFrame {
    
    conexion objetoConexion = new conexion();

    public Login() {
        initComponents();
        this.setTitle("LOGIN");
        this.setSize(550,550);
        Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/com/mycompany/img/logo.png"));
        lbl_logo.setIcon(new ImageIcon(img.getScaledInstance(lbl_logo.getWidth(), lbl_logo.getHeight(), Image.SCALE_SMOOTH)));
        this.setIconImage(img);
        this.setLocationRelativeTo(null);
        objetoConexion = new conexion();
        objetoConexion.estableceConexion();
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_usuario = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btn_iniciar = new javax.swing.JButton();
        lbl_logo = new javax.swing.JLabel();
        txt_contrasena = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(txt_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 230, 40));

        jLabel1.setText("Usuario");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, -1, -1));

        jLabel2.setText("contraseña");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 330, -1, -1));

        btn_iniciar.setText("Iniciar Sesión ");
        btn_iniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_iniciarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_iniciar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 400, 154, 61));

        lbl_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/mycompany/img/logo.png"))); // NOI18N
        getContentPane().add(lbl_logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 30, 180, 160));
        getContentPane().add(txt_contrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 320, 230, 40));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_iniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_iniciarActionPerformed

        try {
            String user = txt_usuario.getText();
            String password = String.valueOf(txt_contrasena.getPassword());
            String query = "SELECT * FROM login WHERE user='" + user + "' and password='" + password + "'";
            Statement st = objetoConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                String nombreUsuario = rs.getString("user");
                JOptionPane.showMessageDialog(this, "Bienvenido, " + nombreUsuario);

                // Redirigir a otro JFrame
                form_alumnos otroFrame = new form_alumnos();
                otroFrame.setVisible(true);

                // Cerrar el JFrame actual
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "El usuario no está en la base de datos");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_iniciarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_iniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lbl_logo;
    private javax.swing.JPasswordField txt_contrasena;
    private javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables
}
