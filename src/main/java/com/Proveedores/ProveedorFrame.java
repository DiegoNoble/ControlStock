
package com.Proveedores;


import com.Beans.Proveedor;
import Utilidades.HibernateUtil;
import com.Compras.RegistraCompraFrame;
import javax.swing.JOptionPane;
import org.hibernate.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;


public class ProveedorFrame extends javax.swing.JInternalFrame {
    String Filtro = "from Proveedor";
    private RegistraCompraFrame registraCompraFrame;
    
    
    public ProveedorFrame() {
        initComponents();

        btnExcluir.setVisible(false);
        muestraContenidoTabla();
        btnSeleccionaProveedor.setVisible(false);
        
    }
     public ProveedorFrame(RegistraCompraFrame seteaProveedor) {
        initComponents();

        btnExcluir.setVisible(false);
        muestraContenidoTabla();
        this.registraCompraFrame = seteaProveedor;
        
        
    }

    class CenterRenderer extends DefaultTableCellRenderer { //----> Classe utilizada para centralizar el contenido de las columnas de las tablas

        public CenterRenderer() {
            setHorizontalAlignment(CENTER);
        }
    }
    
      private void muestraContenidoTabla() {


        TableCellRenderer centerRenderer = new CenterRenderer();
        ((DefaultTableCellRenderer) tblProveedor.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        
        TableColumn column = tblProveedor.getColumnModel().getColumn(0); //----> LLamada de la funcion que centraliza el contenido en las columnas del Jtable
        TableColumn column1 = tblProveedor.getColumnModel().getColumn(1);
        TableColumn column2 = tblProveedor.getColumnModel().getColumn(2);
        TableColumn column3 = tblProveedor.getColumnModel().getColumn(3);
       
        
        column.setCellRenderer(centerRenderer);
        column1.setCellRenderer(centerRenderer);
        column2.setCellRenderer(centerRenderer);
        column3.setCellRenderer(centerRenderer);
        

        DefaultTableModel modelo = (DefaultTableModel) tblProveedor.getModel();
        modelo.setNumRows(0);

        tblProveedor.getColumn("Código").setPreferredWidth(10); //------> Ajusta el tamaño de las columnas
        tblProveedor.getColumn("Nombre").setPreferredWidth(100);
        tblProveedor.getColumn("Razón Social").setPreferredWidth(100);
        tblProveedor.getColumn("R.U.T.").setPreferredWidth(20);
      
        try{
            
            
         Session seccion = HibernateUtil.getSeccion();   
         List<Proveedor> lista_proveedor = new ArrayList();
         lista_proveedor = seccion.createQuery(Filtro).list();
         
         int tamano_lista = lista_proveedor.size();
         
         for (int i=0; i<tamano_lista; i++){
             
             Proveedor proveedor = lista_proveedor.get(i);
             
             Object[] linea = new Object[10];
             linea[0] = proveedor.getId();
             linea[1] = proveedor.getNombre();
             linea[2] = proveedor.getRazon_social();
             linea[3] = proveedor.getRut();
             linea[4] = proveedor.getDireccion();
             linea[5] = proveedor.getCiudad();
             linea[6] = proveedor.getPais();
             linea[7] = proveedor.getCel();
             linea[8] = proveedor.getTel();
             linea[9] = proveedor.getEmail();
            
             
             modelo.addRow(linea);
         }
         
        }catch(Exception error){
        JOptionPane.showMessageDialog(null, "Error"+error);
        }
       
    }
       
  
    

    private void NuevoCliente() {
        
        if (txtNombre.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe el nombre del Proveedor!", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
        } else {
            
            Session seccion = HibernateUtil.getSeccion();
            Proveedor proveedor = new Proveedor();
            proveedor.setNombre(txtNombre.getText());
            proveedor.setCel(txtCel.getText());
            proveedor.setCiudad(txtCiudad.getText());
            proveedor.setDireccion(txtDireccion.getText());
            proveedor.setEmail(txtEmail.getText());
            proveedor.setPais(txtPaís.getText());
            proveedor.setRazon_social(txtRazonSocial.getText());
            proveedor.setRut(txtRut.getText());
            proveedor.setTel(txtTelefono.getText());
            
            Transaction transacion = seccion.beginTransaction();
            seccion.save(proveedor);
            transacion.commit();
            seccion.close();
            
            JOptionPane.showMessageDialog(null, "Proveedor registrado correctamente!");
            
    
        
        }
    }

   private void EditarCliente() {
        
       if (txtNombre.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe el nombre del Proveedor!", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
        
       } else {
           
            Session seccion = HibernateUtil.getSeccion();   
            Proveedor proveedor = new Proveedor();
            proveedor.setId(Integer.parseInt(jlbCodigo.getText()));
            proveedor.setNombre(txtNombre.getText());
            proveedor.setCel(txtCel.getText());
            proveedor.setCiudad(txtCiudad.getText());
            proveedor.setDireccion(txtDireccion.getText());
            proveedor.setEmail(txtEmail.getText());
            proveedor.setPais(txtPaís.getText());
            proveedor.setRazon_social(txtRazonSocial.getText());
            proveedor.setRut(txtRut.getText());
            proveedor.setTel(txtTelefono.getText());
            
            Transaction transacion = seccion.beginTransaction();
            seccion.update(proveedor);
            transacion.commit();
            seccion.close();
            
            JOptionPane.showMessageDialog(null, "Cliente editado correctamente!");
           
            
        }
     
    }
   
       private void eliminaCliente(){
           
           Session seccion = HibernateUtil.getSeccion();   
           Proveedor proveedor = new Proveedor();
           proveedor.setId(Integer.parseInt(jlbCodigo.getText())); 
           Transaction transacion = seccion.beginTransaction();
           seccion.delete(proveedor);
           transacion.commit();
           seccion.close();
            
            JOptionPane.showMessageDialog(null, "Proveedor eliminado correctamente!");
           
       }

    private void habilitaCampos() {
        txtNombre.setEditable(true);
        txtDireccion.setEditable(true);
        txtCiudad.setEditable(true);
        txtPaís.setEditable(true);
        txtTelefono.setEditable(true);
        txtEmail.setEditable(true);
        txtCel.setEditable(true);
        txtRazonSocial.setEditable(true);
        txtRut.setEditable(true);
        tblProveedor.setEnabled(false);
        tblProveedor.setVisible(false);
    }

    private void desabilitaCampos() {
        txtNombre.setEditable(false);
        txtDireccion.setEditable(false);
        txtCiudad.setEditable(false);
        txtPaís.setEditable(false);
        txtTelefono.setEditable(false);
        txtEmail.setEditable(false);
        txtCel.setEditable(false);
        txtRazonSocial.setEditable(false);
        txtRut.setEditable(false);
        tblProveedor.setEnabled(true);
        tblProveedor.setVisible(true);
    }

    private void desabilitaBotoes() {
        btnSalvar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnNovo.setEnabled(true);
        btnAlterar.setEnabled(true);
        btnExcluir.setEnabled(true);
    }

    private void habilitaBotoes() {
        btnSalvar.setEnabled(true);
        btnCancelar.setEnabled(true);
        btnNovo.setEnabled(false);
        btnAlterar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }
    
    private void limpiaCampos(){
    
        jlbCodigo.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtCiudad.setText("");
        txtPaís.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtCel.setText("");
        txtRazonSocial.setText("");
        txtRut.setText("");
    }
    

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCiudad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtPaís = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtRazonSocial = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtRut = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCel = new javax.swing.JTextField();
        jlbCodigo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtFiltroNombre = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProveedor = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btnSeleccionaProveedor = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();
        btnAlterar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        jTextField2.setText("jTextField2");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Sistema de control comercial - D.N.Soft .-");
        setPreferredSize(new java.awt.Dimension(700, 600));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Proveedores");
        jPanel1.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel3.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel3, gridBagConstraints);

        txtNombre.setEditable(false);
        txtNombre.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtNombre, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel4.setText("Dirección");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel4, gridBagConstraints);

        txtDireccion.setEditable(false);
        txtDireccion.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtDireccion, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel5.setText("Ciudad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel5, gridBagConstraints);

        txtCiudad.setEditable(false);
        txtCiudad.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtCiudad, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel6.setText("País");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel6, gridBagConstraints);

        txtPaís.setEditable(false);
        txtPaís.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtPaís, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel9.setText("Teléfono");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel9, gridBagConstraints);

        txtTelefono.setEditable(false);
        txtTelefono.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtTelefono, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel10.setText("Email");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jLabel10, gridBagConstraints);

        txtEmail.setEditable(false);
        txtEmail.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtEmail, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel7.setText("Razón Social");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        jPanel2.add(jLabel7, gridBagConstraints);

        txtRazonSocial.setEditable(false);
        txtRazonSocial.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtRazonSocial, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel12.setText("Rut");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        jPanel2.add(jLabel12, gridBagConstraints);

        txtRut.setEditable(false);
        txtRut.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtRut, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel13.setText("Cel");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        jPanel2.add(jLabel13, gridBagConstraints);

        txtCel.setEditable(false);
        txtCel.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(txtCel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jlbCodigo, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel2, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel2.setText("Filtro por Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel2, gridBagConstraints);

        txtFiltroNombre.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtFiltroNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFiltroNombreActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(txtFiltroNombre, gridBagConstraints);

        tblProveedor.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        tblProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Nombre", "Razón Social", "R.U.T.", "Dirección", "ciudad", "pais", "cel", "telefono", "email"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblProveedor.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProveedorMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProveedor);
        tblProveedor.getColumnModel().getColumn(4).setMinWidth(0);
        tblProveedor.getColumnModel().getColumn(4).setPreferredWidth(0);
        tblProveedor.getColumnModel().getColumn(4).setMaxWidth(0);
        tblProveedor.getColumnModel().getColumn(5).setMinWidth(0);
        tblProveedor.getColumnModel().getColumn(5).setPreferredWidth(0);
        tblProveedor.getColumnModel().getColumn(5).setMaxWidth(0);
        tblProveedor.getColumnModel().getColumn(6).setMinWidth(0);
        tblProveedor.getColumnModel().getColumn(6).setPreferredWidth(0);
        tblProveedor.getColumnModel().getColumn(6).setMaxWidth(0);
        tblProveedor.getColumnModel().getColumn(7).setMinWidth(0);
        tblProveedor.getColumnModel().getColumn(7).setPreferredWidth(0);
        tblProveedor.getColumnModel().getColumn(7).setMaxWidth(0);
        tblProveedor.getColumnModel().getColumn(8).setMinWidth(0);
        tblProveedor.getColumnModel().getColumn(8).setPreferredWidth(0);
        tblProveedor.getColumnModel().getColumn(8).setMaxWidth(0);
        tblProveedor.getColumnModel().getColumn(9).setMinWidth(0);
        tblProveedor.getColumnModel().getColumn(9).setPreferredWidth(0);
        tblProveedor.getColumnModel().getColumn(9).setMaxWidth(0);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel3.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel3, gridBagConstraints);

        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnSeleccionaProveedor.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSeleccionaProveedor.setMnemonic('P');
        btnSeleccionaProveedor.setText("Seleccionar Proveedor");
        btnSeleccionaProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionaProveedorActionPerformed(evt);
            }
        });
        jPanel4.add(btnSeleccionaProveedor);

        btnNovo.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnNovo.setMnemonic('N');
        btnNovo.setText("Nuevo");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });
        jPanel4.add(btnNovo);

        btnAlterar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnAlterar.setMnemonic('E');
        btnAlterar.setText("Editar");
        btnAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarActionPerformed(evt);
            }
        });
        jPanel4.add(btnAlterar);

        btnExcluir.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        jPanel4.add(btnExcluir);

        btnSalvar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnSalvar.setMnemonic('S');
        btnSalvar.setText("Salvar");
        btnSalvar.setEnabled(false);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        jPanel4.add(btnSalvar);

        btnCancelar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel4.add(btnCancelar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(jPanel4, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        
        limpiaCampos();
        habilitaCampos();
        habilitaBotoes();
       
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        if (jlbCodigo.getText().equals("")){
        NuevoCliente();
        }else {
            EditarCliente();
        }
        
        muestraContenidoTabla();
        desabilitaBotoes();
        desabilitaCampos();
        
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        desabilitaBotoes();
        desabilitaCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarActionPerformed

        
        if (tblProveedor.getSelectedRow() != -1) {
            habilitaBotoes();
            habilitaCampos();

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor en la tabla", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnAlterarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        if (tblProveedor.getSelectedRow() != -1) {
            String Nombre = txtNombre.getText();
            int resposta = JOptionPane.showConfirmDialog(this, "Confirma la eliminación del proveedor "+Nombre+"?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                eliminaCliente();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un proveedor de la lista!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        muestraContenidoTabla();
        limpiaCampos();
        
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void tblProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProveedorMouseClicked

       
            limpiaCampos();
        
            try {
            int filaSeleccionada = tblProveedor.getSelectedRow();
            
            jlbCodigo.setText(tblProveedor.getValueAt(filaSeleccionada, 0).toString());

            txtNombre.setText(tblProveedor.getValueAt(filaSeleccionada, 1).toString());

                       
            Object objRS = tblProveedor.getValueAt(filaSeleccionada, 2);
            if (objRS == null) {
            } else {
                txtRazonSocial.setText(tblProveedor.getValueAt(filaSeleccionada, 2).toString());
            }
           

            Object objRUT = tblProveedor.getValueAt(filaSeleccionada, 3);
            if (objRUT == null) /* || ((objCI instanceof String)&& ((String)objCI).length() == 0))*/ {
                //NO HACE NADA, EVITAMOS EL NULLPOINEXCEPTION
            } else {
                txtRut.setText(tblProveedor.getValueAt(filaSeleccionada, 3).toString());
            }
            
            Object objDir = tblProveedor.getValueAt(filaSeleccionada, 4);
            if (objDir == null) {
            } else {
                txtDireccion.setText(tblProveedor.getValueAt(filaSeleccionada, 4).toString());
            }
            
            Object objCiu = tblProveedor.getValueAt(filaSeleccionada, 5);
            if (objCiu == null) {
            } else {
                txtCiudad.setText(tblProveedor.getValueAt(filaSeleccionada, 5).toString());
            }
            
            Object objPai = tblProveedor.getValueAt(filaSeleccionada, 6);
            if (objPai == null) {
            } else {
                txtPaís.setText(tblProveedor.getValueAt(filaSeleccionada, 6).toString());
            }
            
            Object objCel = tblProveedor.getValueAt(filaSeleccionada, 7);
            if (objCel == null) {
            } else {
                txtCel.setText(tblProveedor.getValueAt(filaSeleccionada, 7).toString());
            }
            
            Object objTel = tblProveedor.getValueAt(filaSeleccionada, 8);
            if (objTel == null) {
            } else {
                txtTelefono.setText(tblProveedor.getValueAt(filaSeleccionada, 8).toString());
            }
            
            Object objEmail = tblProveedor.getValueAt(filaSeleccionada, 9);
            if (objEmail == null) {
            } else {
                txtEmail.setText(tblProveedor.getValueAt(filaSeleccionada, 9).toString());
            }
            
           
        } catch (Exception error) {
        }
           
    }//GEN-LAST:event_tblProveedorMouseClicked

    private void txtFiltroNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFiltroNombreActionPerformed

        Filtro = "from Proveedor where nombre like '%"+txtFiltroNombre.getText()+"%'";
        muestraContenidoTabla();
    }//GEN-LAST:event_txtFiltroNombreActionPerformed

    private void btnSeleccionaProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionaProveedorActionPerformed

        int filaSeleccionada = tblProveedor.getSelectedRow();
        if(filaSeleccionada == -1 ){
            JOptionPane.showMessageDialog(null, "Seleccione un proveedor en la tabla!","Error",JOptionPane.ERROR_MESSAGE);
        }else{
            
        Proveedor proveedor = new Proveedor();
        proveedor.setId(Integer.parseInt(tblProveedor.getValueAt(filaSeleccionada, 0).toString()));
        proveedor.setNombre(tblProveedor.getValueAt(filaSeleccionada, 1).toString());
        registraCompraFrame.setProveedor(proveedor);
        this.dispose();
        registraCompraFrame.toFront();
        
        }
        
        
    }//GEN-LAST:event_btnSeleccionaProveedorActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlterar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnSeleccionaProveedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel jlbCodigo;
    private javax.swing.JTable tblProveedor;
    private javax.swing.JTextField txtCel;
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFiltroNombre;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPaís;
    private javax.swing.JTextField txtRazonSocial;
    private javax.swing.JTextField txtRut;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
