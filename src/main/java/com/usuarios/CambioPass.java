package com.usuarios;

import com.Beans.Usuario;
import com.DAO.DAOGenerico;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class CambioPass extends javax.swing.JFrame {

    private static Usuario usuario;

    public CambioPass() {
        initComponents();
        setSize(400, 300);
        setLocationRelativeTo(null);
        lblErrorNombreContrasena.setVisible(false);
        lblErrorNombreContrasena1.setVisible(false);
        lblErrorNuevaContrasena.setVisible(false);
        lblErrorNuevaContrasena1.setVisible(false);
    }

    public static Usuario getInstance() {

        if (usuario == null) {
            usuario = new Usuario();
        }

        return usuario;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            JOptionPane.showMessageDialog(null, "Você pressionou Enter");
        } else {
            JOptionPane.showMessageDialog(null, "Você não pressionou Enter");
        }

    }

    private static void setLogs() throws IOException {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy hh'h'mm'm'ss");
        String agora = format.format(date);

        File out = new File("logs/System.out");
        File err = new File("logs/System.err");
        if (!out.exists()) {
            out.mkdirs();
        }
        if (!err.exists()) {
            err.mkdirs();
        }

        System.setOut(
                new PrintStream(
                new FileOutputStream("logs/System.out/" + agora + ".txt", true)));

        System.setErr(
                new PrintStream(
                new FileOutputStream("logs/System.err/" + agora + ".txt", true)));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        ptxtPass = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        ptxtNuevoPass2 = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        ptxtNuevoPass = new javax.swing.JPasswordField();
        lblErrorNombreContrasena = new javax.swing.JLabel();
        lblErrorNombreContrasena1 = new javax.swing.JLabel();
        lblErrorNuevaContrasena = new javax.swing.JLabel();
        lblErrorNuevaContrasena1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnCambiarContrasena = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Login.."); // NOI18N
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel2.setText("Nombre"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel2, gridBagConstraints);

        jLabel4.setText("Nueva contraseña"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtNombre, gridBagConstraints);

        ptxtPass.setText("jPasswordField1");
        ptxtPass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ptxtPassFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(ptxtPass, gridBagConstraints);

        jLabel5.setText("Nueva contraseña"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel5, gridBagConstraints);

        ptxtNuevoPass2.setText("jPasswordField1");
        ptxtNuevoPass2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ptxtNuevoPass2FocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(ptxtNuevoPass2, gridBagConstraints);

        jLabel6.setText("Contraseña actual"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel6, gridBagConstraints);

        ptxtNuevoPass.setText("jPasswordField1");
        ptxtNuevoPass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ptxtNuevoPassFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(ptxtNuevoPass, gridBagConstraints);

        lblErrorNombreContrasena.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorNombreContrasena.setText("* Error  ");
        jPanel3.add(lblErrorNombreContrasena, new java.awt.GridBagConstraints());

        lblErrorNombreContrasena1.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorNombreContrasena1.setText("* Error  ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel3.add(lblErrorNombreContrasena1, gridBagConstraints);

        lblErrorNuevaContrasena.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorNuevaContrasena.setText("* Error  ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        jPanel3.add(lblErrorNuevaContrasena, gridBagConstraints);

        lblErrorNuevaContrasena1.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorNuevaContrasena1.setText("* Error  ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        jPanel3.add(lblErrorNuevaContrasena1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        btnCambiarContrasena.setText("Cambiar contraseña"); // NOI18N
        btnCambiarContrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarContrasenaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(98, Short.MAX_VALUE)
                .addComponent(btnCambiarContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(87, 87, 87))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCambiarContrasena)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCambiarContrasenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarContrasenaActionPerformed

        try {

            String pass = new String(ptxtPass.getPassword()).trim();
            String nombre = txtNombre.getText();
            DAOGenerico dao = new DAOGenerico();

            List<Usuario> listaUsuarios = dao.Login(nombre, pass);

            if (listaUsuarios.isEmpty()) {

                lblErrorNombreContrasena.setVisible(true);
                lblErrorNombreContrasena1.setVisible(true);
                lblErrorNuevaContrasena.setVisible(false);
                lblErrorNuevaContrasena1.setVisible(false);

            } else {
                String nuevoPass = new String(ptxtNuevoPass.getPassword()).trim();
                String nuevoPass2 = new String(ptxtNuevoPass2.getPassword()).trim();

                if (nuevoPass.equals(nuevoPass2)) {



                    Usuario actualizaPass = new Usuario();
                    actualizaPass.setId(listaUsuarios.get(0).getId());
                    actualizaPass.setNombre(listaUsuarios.get(0).getNombre());
                    actualizaPass.setPass(nuevoPass);
                    actualizaPass.setPerfil(listaUsuarios.get(0).getPerfil());
                    new DAOGenerico<>(actualizaPass).actualiza();

                    JOptionPane.showMessageDialog(null, "Contraseña editada correctamente!");
                    this.dispose();

                } else {
                    lblErrorNombreContrasena.setVisible(false);
                    lblErrorNombreContrasena1.setVisible(false);
                    lblErrorNuevaContrasena.setVisible(true);
                    lblErrorNuevaContrasena1.setVisible(true);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }


    }//GEN-LAST:event_btnCambiarContrasenaActionPerformed

    private void ptxtPassFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ptxtPassFocusGained

        ptxtPass.setText("");
    }//GEN-LAST:event_ptxtPassFocusGained

    private void ptxtNuevoPassFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ptxtNuevoPassFocusGained

        ptxtNuevoPass.setText("");
    }//GEN-LAST:event_ptxtNuevoPassFocusGained

    private void ptxtNuevoPass2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ptxtNuevoPass2FocusGained

        ptxtNuevoPass2.setText("");
    }//GEN-LAST:event_ptxtNuevoPass2FocusGained
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiarContrasena;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblErrorNombreContrasena;
    private javax.swing.JLabel lblErrorNombreContrasena1;
    private javax.swing.JLabel lblErrorNuevaContrasena;
    private javax.swing.JLabel lblErrorNuevaContrasena1;
    private javax.swing.JPasswordField ptxtNuevoPass;
    private javax.swing.JPasswordField ptxtNuevoPass2;
    private javax.swing.JPasswordField ptxtPass;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
