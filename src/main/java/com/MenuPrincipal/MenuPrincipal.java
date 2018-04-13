package com.MenuPrincipal;

import Utilidades.DesktopPaneImagem;
import Utilidades.DetectaCombinacionTeclas;
import Utilidades.LeeProperties;
import com.Articulos.AlertaStocks;
import com.Articulos.ArticulosFrame;
import com.Articulos.ConsultaMovStock;
import com.CategoriaArticulos.CategoriasDialog;
import com.Ciudades.CiudadesDialog;
import com.Clientes.ClienteFrame;
import com.Compras.ConsultaCompras;
import com.Compras.RegistraCompra;
import com.Informes.InformeArticulosPorCiudad;
import com.Informes.InformeArticulosPorCliente;
import com.Informes.InformeArticulosPorVendedorEntreFechas;
import com.Informes.InformeClientesPorCiudad;
import com.Informes.InformeRemitosAgrupados;
import com.Pedidos.ConsultaPedidos;
import com.Pedidos.RegistraPedido;
import com.Proveedores.ProveedoresFrame;
import com.Remito.ConsultaRemitos;
import com.Repartos.ConsultaRepartos;
import com.Repartos.RegistraRepartos;
import com.Transportista.TransportistaFrame;
import com.Unidades.UnidadFrame;
import com.usuarios.frameLogin;
import com.usuarios.registroUsuarios;
import com.vendedor.VendedorFrame;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public final class MenuPrincipal extends javax.swing.JFrame {

    DesktopPaneImagem desktopPane = new DesktopPaneImagem("/com/imagenes/fondo3.jpg");
    public String perfil;

    public MenuPrincipal() {
        initComponents();

        setSize(1152, 864);
        add(desktopPane);
        //setLocationRelativeTo(null);
        this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
        this.perfil = frameLogin.getInstance().getPerfil();
        txtNombre.setText(frameLogin.getInstance().getNombre());
        txtPerfil.setText(perfil);

        switch (perfil) {
            case "Operador":
                mnuItemUsuarios.setEnabled(false);

                break;
            case "Gerente":
                mnuItemUsuarios.setEnabled(false);

                break;
            case "Administrador":

        }
        /* Actualiza actualiza = new Actualiza();
         actualiza.actualizaArticulosPedido();
         */
        DetectaCombinacionTeclas combinacionTeclas = new DetectaCombinacionTeclas();
        combinacionTeclas.init(this);

        AlertaStocks AlertaStocks = new AlertaStocks();
        desktopPane.add(AlertaStocks);
        centralizaJanela(AlertaStocks);
        AlertaStocks.setVisible(true);
        AlertaStocks.toFront();
    }

    private void centralizaJanela(JInternalFrame internalFrame) {
        internalFrame.setLocation((this.getWidth() - internalFrame.getWidth()) / 2,
                1);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jToolBar2 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPerfil = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuSistema = new javax.swing.JMenu();
        mnuItemUsuarios = new javax.swing.JMenuItem();
        mnuItemSesion = new javax.swing.JMenuItem();
        mnuItemSalir = new javax.swing.JMenuItem();
        mnuRegistros = new javax.swing.JMenu();
        mnuItemClientes = new javax.swing.JMenuItem();
        mnuItemProveedores = new javax.swing.JMenuItem();
        mnuItemClientes1 = new javax.swing.JMenuItem();
        mnuItemClientes2 = new javax.swing.JMenuItem();
        mnuItemProductos = new javax.swing.JMenuItem();
        mnuItemClientes3 = new javax.swing.JMenuItem();
        mnuItemCategorias = new javax.swing.JMenuItem();
        mnuItemUnidades = new javax.swing.JMenuItem();
        mnuItemUnidades1 = new javax.swing.JMenuItem();
        mnuVentas = new javax.swing.JMenu();
        mnuItemRegistrarVentas2 = new javax.swing.JMenuItem();
        mnuItemRegistrarVentas3 = new javax.swing.JMenuItem();
        mnuVentas1 = new javax.swing.JMenu();
        mnuItemRegistrarVentas5 = new javax.swing.JMenuItem();
        mnuAyuda2 = new javax.swing.JMenu();
        mnuItemConsultaCuentasProveedores3 = new javax.swing.JMenuItem();
        mnuItemConsultaCuentasProveedores4 = new javax.swing.JMenuItem();
        mnuCompras = new javax.swing.JMenu();
        mnuItemRegistrarVentas1 = new javax.swing.JMenuItem();
        mnuItemConsultarVendas1 = new javax.swing.JMenuItem();
        mnuAyuda1 = new javax.swing.JMenu();
        mnuItemConsultaCuentasProveedores2 = new javax.swing.JMenuItem();
        mnuAyuda3 = new javax.swing.JMenu();
        mnuItemConsultaCuentasProveedores5 = new javax.swing.JMenuItem();
        mnuItemConsultaCuentasProveedores6 = new javax.swing.JMenuItem();
        mnuItemConsultaCuentasProveedores7 = new javax.swing.JMenuItem();
        mnuItemConsultaCuentasProveedores10 = new javax.swing.JMenuItem();
        mnuItemConsultaCuentasProveedores8 = new javax.swing.JMenuItem();
        mnuItemConsultaCuentasProveedores9 = new javax.swing.JMenuItem();
        mnuItemConsultaCuentasProveedores11 = new javax.swing.JMenuItem();
        mnuAyuda = new javax.swing.JMenu();
        mnuItemConsultaCuentasProveedores1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de control comercial - D.N.Soft .-");

        jToolBar2.setRollover(true);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel1.setText("Usuario:");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel1, gridBagConstraints);

        txtNombre.setEditable(false);
        txtNombre.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtNombre.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtNombre, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
        jLabel2.setText("Perfil:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel1.add(jLabel2, gridBagConstraints);

        txtPerfil.setEditable(false);
        txtPerfil.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
        txtPerfil.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtPerfil, gridBagConstraints);

        jToolBar2.add(jPanel1);

        getContentPane().add(jToolBar2, java.awt.BorderLayout.SOUTH);

        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);
        getContentPane().add(jToolBar1, java.awt.BorderLayout.WEST);

        jMenuBar1.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N

        mnuSistema.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuSistema.setText("  Sistema  ");
        mnuSistema.setBorderPainted(true);
        mnuSistema.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        mnuItemUsuarios.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemUsuarios.setText(" Usuarios ");
        mnuItemUsuarios.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemUsuarios.setBorderPainted(true);
        mnuItemUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemUsuariosActionPerformed(evt);
            }
        });
        mnuSistema.add(mnuItemUsuarios);

        mnuItemSesion.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemSesion.setText(" Cerrar sesión ");
        mnuItemSesion.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemSesion.setBorderPainted(true);
        mnuItemSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemSesionActionPerformed(evt);
            }
        });
        mnuSistema.add(mnuItemSesion);

        mnuItemSalir.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemSalir.setText(" Salir ");
        mnuItemSalir.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemSalir.setBorderPainted(true);
        mnuItemSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemSalirActionPerformed(evt);
            }
        });
        mnuSistema.add(mnuItemSalir);

        jMenuBar1.add(mnuSistema);

        mnuRegistros.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuRegistros.setText("  Registros  ");
        mnuRegistros.setBorderPainted(true);
        mnuRegistros.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        mnuItemClientes.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemClientes.setText(" Clientes ");
        mnuItemClientes.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemClientes.setBorderPainted(true);
        mnuItemClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemClientesActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemClientes);

        mnuItemProveedores.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemProveedores.setText(" Proveedores ");
        mnuItemProveedores.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemProveedores.setBorderPainted(true);
        mnuItemProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemProveedoresActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemProveedores);

        mnuItemClientes1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemClientes1.setText(" Vendedores");
        mnuItemClientes1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemClientes1.setBorderPainted(true);
        mnuItemClientes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemClientes1ActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemClientes1);

        mnuItemClientes2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemClientes2.setText(" Transportistas ");
        mnuItemClientes2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemClientes2.setBorderPainted(true);
        mnuItemClientes2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemClientes2ActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemClientes2);

        mnuItemProductos.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemProductos.setText(" Articulos ");
        mnuItemProductos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemProductos.setBorderPainted(true);
        mnuItemProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemProductosActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemProductos);

        mnuItemClientes3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemClientes3.setText(" Articulos agotados");
        mnuItemClientes3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemClientes3.setBorderPainted(true);
        mnuItemClientes3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemClientes3ActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemClientes3);

        mnuItemCategorias.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemCategorias.setText(" Categorías  ");
        mnuItemCategorias.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemCategorias.setBorderPainted(true);
        mnuItemCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemCategoriasActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemCategorias);

        mnuItemUnidades.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemUnidades.setText(" Unidades");
        mnuItemUnidades.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemUnidades.setBorderPainted(true);
        mnuItemUnidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemUnidadesActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemUnidades);

        mnuItemUnidades1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemUnidades1.setText(" Ciudades ");
        mnuItemUnidades1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemUnidades1.setBorderPainted(true);
        mnuItemUnidades1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemUnidades1ActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemUnidades1);

        jMenuBar1.add(mnuRegistros);

        mnuVentas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuVentas.setText(" Pedidos ");
        mnuVentas.setBorderPainted(true);
        mnuVentas.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        mnuItemRegistrarVentas2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarVentas2.setText(" Registrar");
        mnuItemRegistrarVentas2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarVentas2.setBorderPainted(true);
        mnuItemRegistrarVentas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarVentas2ActionPerformed(evt);
            }
        });
        mnuVentas.add(mnuItemRegistrarVentas2);

        mnuItemRegistrarVentas3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarVentas3.setText(" Consultar/Atender");
        mnuItemRegistrarVentas3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarVentas3.setBorderPainted(true);
        mnuItemRegistrarVentas3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarVentas3ActionPerformed(evt);
            }
        });
        mnuVentas.add(mnuItemRegistrarVentas3);

        jMenuBar1.add(mnuVentas);

        mnuVentas1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuVentas1.setText("Remitos");
        mnuVentas1.setBorderPainted(true);
        mnuVentas1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        mnuItemRegistrarVentas5.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarVentas5.setText(" Consultar");
        mnuItemRegistrarVentas5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarVentas5.setBorderPainted(true);
        mnuItemRegistrarVentas5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarVentas5ActionPerformed(evt);
            }
        });
        mnuVentas1.add(mnuItemRegistrarVentas5);

        jMenuBar1.add(mnuVentas1);

        mnuAyuda2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuAyuda2.setText(" Repartos ");
        mnuAyuda2.setBorderPainted(true);
        mnuAyuda2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mnuAyuda2.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        mnuItemConsultaCuentasProveedores3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultaCuentasProveedores3.setText(" Registrar ");
        mnuItemConsultaCuentasProveedores3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuItemConsultaCuentasProveedores3.setBorderPainted(true);
        mnuItemConsultaCuentasProveedores3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaCuentasProveedores3ActionPerformed(evt);
            }
        });
        mnuAyuda2.add(mnuItemConsultaCuentasProveedores3);

        mnuItemConsultaCuentasProveedores4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultaCuentasProveedores4.setText(" Consultas ");
        mnuItemConsultaCuentasProveedores4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuItemConsultaCuentasProveedores4.setBorderPainted(true);
        mnuItemConsultaCuentasProveedores4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaCuentasProveedores4ActionPerformed(evt);
            }
        });
        mnuAyuda2.add(mnuItemConsultaCuentasProveedores4);

        jMenuBar1.add(mnuAyuda2);

        mnuCompras.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuCompras.setText("  Compras  ");
        mnuCompras.setBorderPainted(true);
        mnuCompras.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mnuCompras.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        mnuItemRegistrarVentas1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarVentas1.setText(" Registrar compra ");
        mnuItemRegistrarVentas1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarVentas1.setBorderPainted(true);
        mnuItemRegistrarVentas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarVentas1ActionPerformed(evt);
            }
        });
        mnuCompras.add(mnuItemRegistrarVentas1);

        mnuItemConsultarVendas1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultarVendas1.setText(" Consultar compras ");
        mnuItemConsultarVendas1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemConsultarVendas1.setBorderPainted(true);
        mnuItemConsultarVendas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultarVendas1ActionPerformed(evt);
            }
        });
        mnuCompras.add(mnuItemConsultarVendas1);

        jMenuBar1.add(mnuCompras);

        mnuAyuda1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuAyuda1.setText(" Stock ");
        mnuAyuda1.setBorderPainted(true);
        mnuAyuda1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mnuAyuda1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        mnuItemConsultaCuentasProveedores2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultaCuentasProveedores2.setText(" Consulta movimientos");
        mnuItemConsultaCuentasProveedores2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuItemConsultaCuentasProveedores2.setBorderPainted(true);
        mnuItemConsultaCuentasProveedores2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaCuentasProveedores2ActionPerformed(evt);
            }
        });
        mnuAyuda1.add(mnuItemConsultaCuentasProveedores2);

        jMenuBar1.add(mnuAyuda1);

        mnuAyuda3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuAyuda3.setText(" Informes ");
        mnuAyuda3.setBorderPainted(true);
        mnuAyuda3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mnuAyuda3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        mnuItemConsultaCuentasProveedores5.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultaCuentasProveedores5.setText(" Articulos por preventista ");
        mnuItemConsultaCuentasProveedores5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuItemConsultaCuentasProveedores5.setBorderPainted(true);
        mnuItemConsultaCuentasProveedores5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaCuentasProveedores5ActionPerformed(evt);
            }
        });
        mnuAyuda3.add(mnuItemConsultaCuentasProveedores5);

        mnuItemConsultaCuentasProveedores6.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultaCuentasProveedores6.setText(" Articulos por ciudad");
        mnuItemConsultaCuentasProveedores6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuItemConsultaCuentasProveedores6.setBorderPainted(true);
        mnuItemConsultaCuentasProveedores6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaCuentasProveedores6ActionPerformed(evt);
            }
        });
        mnuAyuda3.add(mnuItemConsultaCuentasProveedores6);

        mnuItemConsultaCuentasProveedores7.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultaCuentasProveedores7.setText(" Articulos por clientes");
        mnuItemConsultaCuentasProveedores7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuItemConsultaCuentasProveedores7.setBorderPainted(true);
        mnuItemConsultaCuentasProveedores7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaCuentasProveedores7ActionPerformed(evt);
            }
        });
        mnuAyuda3.add(mnuItemConsultaCuentasProveedores7);

        mnuItemConsultaCuentasProveedores10.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultaCuentasProveedores10.setText(" Clientes por ciudad");
        mnuItemConsultaCuentasProveedores10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuItemConsultaCuentasProveedores10.setBorderPainted(true);
        mnuItemConsultaCuentasProveedores10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaCuentasProveedores10ActionPerformed(evt);
            }
        });
        mnuAyuda3.add(mnuItemConsultaCuentasProveedores10);

        mnuItemConsultaCuentasProveedores8.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultaCuentasProveedores8.setText(" Remitos agrupados");
        mnuItemConsultaCuentasProveedores8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuItemConsultaCuentasProveedores8.setBorderPainted(true);
        mnuItemConsultaCuentasProveedores8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaCuentasProveedores8ActionPerformed(evt);
            }
        });
        mnuAyuda3.add(mnuItemConsultaCuentasProveedores8);

        mnuItemConsultaCuentasProveedores9.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultaCuentasProveedores9.setText(" Inventario ");
        mnuItemConsultaCuentasProveedores9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuItemConsultaCuentasProveedores9.setBorderPainted(true);
        mnuItemConsultaCuentasProveedores9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaCuentasProveedores9ActionPerformed(evt);
            }
        });
        mnuAyuda3.add(mnuItemConsultaCuentasProveedores9);

        mnuItemConsultaCuentasProveedores11.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultaCuentasProveedores11.setText(" Últimos pedidos por Cliente");
        mnuItemConsultaCuentasProveedores11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuItemConsultaCuentasProveedores11.setBorderPainted(true);
        mnuItemConsultaCuentasProveedores11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaCuentasProveedores11ActionPerformed(evt);
            }
        });
        mnuAyuda3.add(mnuItemConsultaCuentasProveedores11);

        jMenuBar1.add(mnuAyuda3);

        mnuAyuda.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuAyuda.setText("  Ayuda  ");
        mnuAyuda.setBorderPainted(true);
        mnuAyuda.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mnuAyuda.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        mnuItemConsultaCuentasProveedores1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultaCuentasProveedores1.setText("  Sobre  ");
        mnuItemConsultaCuentasProveedores1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuItemConsultaCuentasProveedores1.setBorderPainted(true);
        mnuItemConsultaCuentasProveedores1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaCuentasProveedores1ActionPerformed(evt);
            }
        });
        mnuAyuda.add(mnuItemConsultaCuentasProveedores1);

        jMenuBar1.add(mnuAyuda);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuItemSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_mnuItemSalirActionPerformed

    private void mnuItemClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemClientesActionPerformed

        ClienteFrame clientes = new ClienteFrame();
        desktopPane.add(clientes);
        centralizaJanela(clientes);
        clientes.setVisible(true);
        clientes.toFront();

    }//GEN-LAST:event_mnuItemClientesActionPerformed

    private void mnuItemProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemProveedoresActionPerformed

        ProveedoresFrame proveedorFrame = new ProveedoresFrame();
        desktopPane.add(proveedorFrame);
        centralizaJanela(proveedorFrame);
        proveedorFrame.setVisible(true);
        proveedorFrame.toFront();

    }//GEN-LAST:event_mnuItemProveedoresActionPerformed

    private void mnuItemProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemProductosActionPerformed

        ArticulosFrame articulos = new ArticulosFrame(perfil);
        desktopPane.add(articulos);
        centralizaJanela(articulos);
        articulos.setVisible(true);
        articulos.setSize(900, desktopPane.getHeight());
        articulos.toFront();

    }//GEN-LAST:event_mnuItemProductosActionPerformed

    private void mnuItemRegistrarVentas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarVentas1ActionPerformed

        RegistraCompra registraCompra = new RegistraCompra();
        desktopPane.add(registraCompra);
        centralizaJanela(registraCompra);
        registraCompra.setVisible(true);
        registraCompra.toFront();

    }//GEN-LAST:event_mnuItemRegistrarVentas1ActionPerformed

    private void mnuItemConsultarVendas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultarVendas1ActionPerformed

        ConsultaCompras consultaCompra = new ConsultaCompras();
        desktopPane.add(consultaCompra);
        centralizaJanela(consultaCompra);
        consultaCompra.setVisible(true);
        consultaCompra.toFront();

    }//GEN-LAST:event_mnuItemConsultarVendas1ActionPerformed

    private void mnuItemUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemUsuariosActionPerformed

        registroUsuarios usuarios = new registroUsuarios();
        desktopPane.add(usuarios);
        centralizaJanela(usuarios);
        usuarios.setVisible(true);
        usuarios.toFront();

    }//GEN-LAST:event_mnuItemUsuariosActionPerformed

    private void mnuItemSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemSesionActionPerformed

        this.dispose();
        frameLogin login = new frameLogin();
        login.setVisible(true);
        //this.dispose();
    }//GEN-LAST:event_mnuItemSesionActionPerformed

    private void mnuItemConsultaCuentasProveedores1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaCuentasProveedores1ActionPerformed

        JOptionPane.showMessageDialog(null, "<html><font size=6 face=Verdana color=black><center>Sistema de Control Comercial<br><br>"
                + "Desarrollado por D.N.Soft</center><br><br>"
                + "<font size=5 face=Verdana color=black>Contactos: Diego Noble<br><br>"
                + "cel: 099493567<br><br>"
                + "E-mail: noblediego@hotmail.com<br><br></html>");

    }//GEN-LAST:event_mnuItemConsultaCuentasProveedores1ActionPerformed

    private void mnuItemCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemCategoriasActionPerformed

        CategoriasDialog categorias = new CategoriasDialog(null, false, this);
        categorias.setVisible(true);
        categorias.toFront();

    }//GEN-LAST:event_mnuItemCategoriasActionPerformed

    private void mnuItemUnidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemUnidadesActionPerformed

        UnidadFrame unidad = new UnidadFrame();
        centralizaJanela(unidad);
        desktopPane.add(unidad);
        unidad.setVisible(true);
        unidad.toFront();


    }//GEN-LAST:event_mnuItemUnidadesActionPerformed

    private void mnuItemRegistrarVentas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarVentas2ActionPerformed
        RegistraPedido registraPedido = new RegistraPedido();

        if (!registraPedido.isVisible()) {
            centralizaJanela(registraPedido);
            desktopPane.add(registraPedido);
            registraPedido.setVisible(true);
        } else {

            registraPedido.toFront();
        }
    }//GEN-LAST:event_mnuItemRegistrarVentas2ActionPerformed

    private void mnuItemRegistrarVentas3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarVentas3ActionPerformed

        ConsultaPedidos atencionDePedidos = new ConsultaPedidos();
        centralizaJanela(atencionDePedidos);
        desktopPane.add(atencionDePedidos);
        atencionDePedidos.setVisible(true);
        atencionDePedidos.toFront();

    }//GEN-LAST:event_mnuItemRegistrarVentas3ActionPerformed

    private void mnuItemClientes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemClientes1ActionPerformed

        VendedorFrame vedeFrame = new VendedorFrame();
        centralizaJanela(vedeFrame);
        desktopPane.add(vedeFrame);
        vedeFrame.setVisible(true);
        vedeFrame.toFront();

    }//GEN-LAST:event_mnuItemClientes1ActionPerformed

    private void mnuItemRegistrarVentas5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarVentas5ActionPerformed
        ConsultaRemitos consultaRemitos = new ConsultaRemitos();
        centralizaJanela(consultaRemitos);
        desktopPane.add(consultaRemitos);
        consultaRemitos.setVisible(true);
        consultaRemitos.toFront();
    }//GEN-LAST:event_mnuItemRegistrarVentas5ActionPerformed

    private void mnuItemConsultaCuentasProveedores2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaCuentasProveedores2ActionPerformed
        ConsultaMovStock consultaMovStock = new ConsultaMovStock();
        centralizaJanela(consultaMovStock);
        desktopPane.add(consultaMovStock);
        consultaMovStock.setVisible(true);
        consultaMovStock.toFront();
    }//GEN-LAST:event_mnuItemConsultaCuentasProveedores2ActionPerformed

    private void mnuItemUnidades1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemUnidades1ActionPerformed
        CiudadesDialog ciudadesDialog = new CiudadesDialog(this, false, this);
        ciudadesDialog.setVisible(true);
        ciudadesDialog.toFront();
    }//GEN-LAST:event_mnuItemUnidades1ActionPerformed

    private void mnuItemClientes2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemClientes2ActionPerformed
        TransportistaFrame transportistaFrame = new TransportistaFrame();
        centralizaJanela(transportistaFrame);
        desktopPane.add(transportistaFrame);
        transportistaFrame.setVisible(true);
        transportistaFrame.toFront();
    }//GEN-LAST:event_mnuItemClientes2ActionPerformed

    private void mnuItemConsultaCuentasProveedores3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaCuentasProveedores3ActionPerformed

        RegistraRepartos registraRepartos = new RegistraRepartos();
        centralizaJanela(registraRepartos);
        desktopPane.add(registraRepartos);
        registraRepartos.setVisible(true);
        registraRepartos.toFront();
    }//GEN-LAST:event_mnuItemConsultaCuentasProveedores3ActionPerformed

    private void mnuItemConsultaCuentasProveedores4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaCuentasProveedores4ActionPerformed
        ConsultaRepartos consultaRepartos = new ConsultaRepartos();
        centralizaJanela(consultaRepartos);
        desktopPane.add(consultaRepartos);
        consultaRepartos.setVisible(true);
        consultaRepartos.toFront();
    }//GEN-LAST:event_mnuItemConsultaCuentasProveedores4ActionPerformed

    private void mnuItemConsultaCuentasProveedores5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaCuentasProveedores5ActionPerformed

        InformeArticulosPorVendedorEntreFechas informe = new InformeArticulosPorVendedorEntreFechas(null, false);
        informe.setVisible(true);
        informe.toFront();
    }//GEN-LAST:event_mnuItemConsultaCuentasProveedores5ActionPerformed

    private void mnuItemConsultaCuentasProveedores6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaCuentasProveedores6ActionPerformed
        InformeArticulosPorCiudad informe = new InformeArticulosPorCiudad(null, false);
        informe.setVisible(true);
        informe.toFront();
    }//GEN-LAST:event_mnuItemConsultaCuentasProveedores6ActionPerformed

    private void mnuItemConsultaCuentasProveedores7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaCuentasProveedores7ActionPerformed
        InformeArticulosPorCliente informe = new InformeArticulosPorCliente(null, false);
        informe.setVisible(true);
        informe.toFront();
    }//GEN-LAST:event_mnuItemConsultaCuentasProveedores7ActionPerformed

    private void mnuItemConsultaCuentasProveedores8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaCuentasProveedores8ActionPerformed
        InformeRemitosAgrupados informe = new InformeRemitosAgrupados(null, false);
        informe.setVisible(true);
        informe.toFront();
    }//GEN-LAST:event_mnuItemConsultaCuentasProveedores8ActionPerformed

    private void mnuItemConsultaCuentasProveedores9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaCuentasProveedores9ActionPerformed
        try {
            LeeProperties props = new LeeProperties();
            Connection conexion = DriverManager.getConnection(props.getUrl(), props.getUsr(), props.getPsw());

            InputStream resource = getClass().getClassLoader().getResourceAsStream("informesJasperServer/inventario.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, null, conexion);
            JasperViewer reporte = new JasperViewer(jasperPrint, false);
            reporte.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mnuItemConsultaCuentasProveedores9ActionPerformed

    private void mnuItemClientes3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemClientes3ActionPerformed
        AlertaStocks AlertaStocks = new AlertaStocks();
        desktopPane.add(AlertaStocks);
        centralizaJanela(AlertaStocks);
        AlertaStocks.setVisible(true);
        AlertaStocks.toFront();
    }//GEN-LAST:event_mnuItemClientes3ActionPerformed

    private void mnuItemConsultaCuentasProveedores10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaCuentasProveedores10ActionPerformed
        InformeClientesPorCiudad informe = new InformeClientesPorCiudad(null, false);
        informe.setVisible(true);
        informe.toFront();
    }//GEN-LAST:event_mnuItemConsultaCuentasProveedores10ActionPerformed

    private void mnuItemConsultaCuentasProveedores11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaCuentasProveedores11ActionPerformed

        try {
            LeeProperties props = new LeeProperties();
            Connection conexion = DriverManager.getConnection(props.getUrl(), props.getUsr(), props.getPsw());

            InputStream resource = getClass().getClassLoader().getResourceAsStream("informesJasperServer/ultimo_pedido_cliente.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(resource, null, conexion);
            JasperViewer reporte = new JasperViewer(jasperPrint, false);
            reporte.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_mnuItemConsultaCuentasProveedores11ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JMenu mnuAyuda;
    private javax.swing.JMenu mnuAyuda1;
    private javax.swing.JMenu mnuAyuda2;
    private javax.swing.JMenu mnuAyuda3;
    private javax.swing.JMenu mnuCompras;
    private javax.swing.JMenuItem mnuItemCategorias;
    private javax.swing.JMenuItem mnuItemClientes;
    private javax.swing.JMenuItem mnuItemClientes1;
    private javax.swing.JMenuItem mnuItemClientes2;
    private javax.swing.JMenuItem mnuItemClientes3;
    private javax.swing.JMenuItem mnuItemConsultaCuentasProveedores1;
    private javax.swing.JMenuItem mnuItemConsultaCuentasProveedores10;
    private javax.swing.JMenuItem mnuItemConsultaCuentasProveedores11;
    private javax.swing.JMenuItem mnuItemConsultaCuentasProveedores2;
    private javax.swing.JMenuItem mnuItemConsultaCuentasProveedores3;
    private javax.swing.JMenuItem mnuItemConsultaCuentasProveedores4;
    private javax.swing.JMenuItem mnuItemConsultaCuentasProveedores5;
    private javax.swing.JMenuItem mnuItemConsultaCuentasProveedores6;
    private javax.swing.JMenuItem mnuItemConsultaCuentasProveedores7;
    private javax.swing.JMenuItem mnuItemConsultaCuentasProveedores8;
    private javax.swing.JMenuItem mnuItemConsultaCuentasProveedores9;
    private javax.swing.JMenuItem mnuItemConsultarVendas1;
    private javax.swing.JMenuItem mnuItemProductos;
    private javax.swing.JMenuItem mnuItemProveedores;
    private javax.swing.JMenuItem mnuItemRegistrarVentas1;
    private javax.swing.JMenuItem mnuItemRegistrarVentas2;
    private javax.swing.JMenuItem mnuItemRegistrarVentas3;
    private javax.swing.JMenuItem mnuItemRegistrarVentas5;
    private javax.swing.JMenuItem mnuItemSalir;
    private javax.swing.JMenuItem mnuItemSesion;
    private javax.swing.JMenuItem mnuItemUnidades;
    private javax.swing.JMenuItem mnuItemUnidades1;
    private javax.swing.JMenuItem mnuItemUsuarios;
    private javax.swing.JMenu mnuRegistros;
    private javax.swing.JMenu mnuSistema;
    private javax.swing.JMenu mnuVentas;
    private javax.swing.JMenu mnuVentas1;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPerfil;
    // End of variables declaration//GEN-END:variables
}
