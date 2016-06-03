package com.MenuPrincipal;

import com.DAO.DAOGenerico;
import Utilidades.DesktopPaneImagem;
import com.Articulos.ArticulosFrame;
import com.Beans.FacturaCompra;
import com.CategoriaArticulos.CategoriaFrame;
import com.Clientes.ClienteFrame;
import com.Compras.ConsultaCompraFrame;
import com.Compras.RegistraCompraFrame;
import com.CuentasPagar.RegistraPagos;
import com.CuentasRecibir.ConsultaCuentasRecibir;
import com.CuentasPagar.ConsultaPagosCompras;
import com.CuentasPagar.FrameRecordatorioCuentasPagar;
import com.CuentasPagar.RegistroGastosFijos;
import com.DAO.FacturaCompraDAO;
import com.Pedidos.RegistraPedido;
import com.Proveedores.ProveedorFrame;
import com.Unidades.UnidadFrame;
import com.Ventas.ConsultaVentaFrame;
import com.Ventas.RegistraVentaFrame;
import com.caja.cajaFrame;
import com.caja.consultaCajaFrame;
import com.usuarios.frameLogin;
import com.usuarios.registroUsuarios;
import com.view.FrameRegistraCotizacion;
import java.awt.Image;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public final class MenuPrincipal extends javax.swing.JFrame {

    DesktopPaneImagem desktopPane = new DesktopPaneImagem("/com/imagenes/fondo3.jpg");
    public String perfil;
    private List<FacturaCompra> listFactVencimientos;
    private FacturaCompraDAO facturaCompraDAO;

    public MenuPrincipal() {
        initComponents();
        setSize(1152, 864);
        add(desktopPane);
        setLocationRelativeTo(null);
        this.perfil = frameLogin.getInstance().getPerfil();
        txtNombre.setText(frameLogin.getInstance().getNombre());
        txtPerfil.setText(perfil);

        switch (perfil) {
            case "Operador":
                mnuItemUsuarios.setEnabled(false);
                mnuCuentasClientes.setEnabled(false);
                mnuCuentasProveedores.setEnabled(false);
                break;
            case "Gerente":
                mnuItemUsuarios.setEnabled(false);
                mnuCuentasClientes.setEnabled(false);
                mnuCuentasProveedores.setEnabled(false);
                buscaVencimientosCuentasPagar();

                break;
            case "Administrador":
                buscaVencimientosCuentasPagar();

        }
//        String foto = "/com/imagenes/Zoom.png";
        btnConsultaArticulos.setSize(70, 60);
        ImageIcon fot = new ImageIcon(getClass().getResource("/com/imagenes/Zoom.png"));
        Icon icono = new ImageIcon(fot.getImage().getScaledInstance(btnConsultaArticulos.getWidth(),
                btnConsultaArticulos.getHeight(), Image.SCALE_DEFAULT));
        btnConsultaArticulos.setIcon(icono);
        this.repaint();

        String foto1 = "/com/imagenes/Facturar.png";
        btnVenta.setSize(70, 60);
        ImageIcon fot1 = new ImageIcon(getClass().getResource("/com/imagenes/Facturar.png"));
        Icon icono1 = new ImageIcon(fot1.getImage().getScaledInstance(btnVenta.getWidth(),
                btnVenta.getHeight(), Image.SCALE_DEFAULT));
        btnVenta.setIcon(icono1);
        this.repaint();

        String foto2 = "/com/imagenes/caja.png";
        btnCaja.setSize(70, 60);
        ImageIcon fot2 = new ImageIcon(getClass().getResource(foto2));
        Icon icono2 = new ImageIcon(fot2.getImage().getScaledInstance(btnCaja.getWidth(),
                btnCaja.getHeight(), Image.SCALE_DEFAULT));
        btnCaja.setIcon(icono2);
        this.repaint();

        String foto3 = "/com/imagenes/calculadora.png";
        btnCalculadora.setSize(70, 60);
        ImageIcon fot3 = new ImageIcon(getClass().getResource(foto3));
        Icon icono3 = new ImageIcon(fot3.getImage().getScaledInstance(btnCalculadora.getWidth(),
                btnCalculadora.getHeight(), Image.SCALE_DEFAULT));
        btnCalculadora.setIcon(icono3);
        this.repaint();

    }

    private void centralizaJanela(JInternalFrame internalFrame) {
        internalFrame.setLocation((this.getWidth() - internalFrame.getWidth()) / 2,
                (this.getHeight() - internalFrame.getHeight()) / 4);
    }

    public void buscaVencimientosCuentasPagar() {

        facturaCompraDAO = new FacturaCompraDAO();
        try {
            listFactVencimientos = facturaCompraDAO.buscaProximosVencimientos();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar vencimientos" + ex);
            ex.printStackTrace();
        }
        if (listFactVencimientos.isEmpty()) {
        } else {

            FrameRecordatorioCuentasPagar recordatorio = new FrameRecordatorioCuentasPagar(listFactVencimientos);
            desktopPane.add(recordatorio);
            recordatorio.setLocation(1, 1);
            recordatorio.setVisible(true);
            recordatorio.toFront();
        }

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
        jPanel3 = new javax.swing.JPanel();
        btnConsultaArticulos = new javax.swing.JButton();
        btnVenta = new javax.swing.JButton();
        btnCaja = new javax.swing.JButton();
        btnCalculadora = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuSistema = new javax.swing.JMenu();
        mnuItemUsuarios = new javax.swing.JMenuItem();
        mnuItemCotización = new javax.swing.JMenuItem();
        mnuItemSesion = new javax.swing.JMenuItem();
        mnuItemSalir = new javax.swing.JMenuItem();
        mnuCaja = new javax.swing.JMenu();
        mnuItemMoviminetos = new javax.swing.JMenuItem();
        mnuItemConsultaMov = new javax.swing.JMenuItem();
        mnuRegistros = new javax.swing.JMenu();
        mnuItemClientes = new javax.swing.JMenuItem();
        mnuItemProveedores = new javax.swing.JMenuItem();
        mnuItemProductos = new javax.swing.JMenuItem();
        mnuItemCategorias = new javax.swing.JMenuItem();
        mnuItemUnidades = new javax.swing.JMenuItem();
        mnuVentas = new javax.swing.JMenu();
        mnuItemRegistrarVentas = new javax.swing.JMenuItem();
        mnuItemConsultarVendas = new javax.swing.JMenuItem();
        mnuItemRegistrarVentas2 = new javax.swing.JMenuItem();
        mnuCompras = new javax.swing.JMenu();
        mnuItemRegistrarVentas1 = new javax.swing.JMenuItem();
        mnuItemConsultarVendas1 = new javax.swing.JMenuItem();
        mnuCuentasClientes = new javax.swing.JMenu();
        mnuItemConsultaCuentas = new javax.swing.JMenuItem();
        mnuCuentasProveedores = new javax.swing.JMenu();
        mnuItemConsultaCuentasProveedores = new javax.swing.JMenuItem();
        mnuItemConsultaPagosProveedores = new javax.swing.JMenuItem();
        mnuItemRegistraCuentaFija = new javax.swing.JMenuItem();
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

        jPanel3.setLayout(new java.awt.GridBagLayout());

        btnConsultaArticulos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/imagenes/Zoom.png"))); // NOI18N
        btnConsultaArticulos.setToolTipText("Consultar Articulos.");
        btnConsultaArticulos.setFocusable(false);
        btnConsultaArticulos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConsultaArticulos.setPreferredSize(new java.awt.Dimension(90, 80));
        btnConsultaArticulos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnConsultaArticulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultaArticulosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(btnConsultaArticulos, gridBagConstraints);

        btnVenta.setToolTipText("Realizar venta de Articulos");
        btnVenta.setFocusable(false);
        btnVenta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVenta.setPreferredSize(new java.awt.Dimension(90, 80));
        btnVenta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(btnVenta, gridBagConstraints);

        btnCaja.setToolTipText("Realizar y consultar movimientos de caja");
        btnCaja.setFocusable(false);
        btnCaja.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCaja.setPreferredSize(new java.awt.Dimension(90, 80));
        btnCaja.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCajaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(btnCaja, gridBagConstraints);

        btnCalculadora.setToolTipText("Utilizar calculadora");
        btnCalculadora.setFocusable(false);
        btnCalculadora.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCalculadora.setPreferredSize(new java.awt.Dimension(90, 80));
        btnCalculadora.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCalculadora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalculadoraActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(btnCalculadora, gridBagConstraints);

        jToolBar1.add(jPanel3);

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

        mnuItemCotización.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemCotización.setText(" Cotización  ");
        mnuItemCotización.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemCotización.setBorderPainted(true);
        mnuItemCotización.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemCotizaciónActionPerformed(evt);
            }
        });
        mnuSistema.add(mnuItemCotización);

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

        mnuCaja.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuCaja.setText("  Control de caja  ");
        mnuCaja.setBorderPainted(true);
        mnuCaja.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        mnuItemMoviminetos.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemMoviminetos.setText(" Movimientos diarios ");
        mnuItemMoviminetos.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemMoviminetos.setBorderPainted(true);
        mnuItemMoviminetos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemMoviminetosActionPerformed(evt);
            }
        });
        mnuCaja.add(mnuItemMoviminetos);

        mnuItemConsultaMov.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultaMov.setText(" Consultar movimientos ");
        mnuItemConsultaMov.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemConsultaMov.setBorderPainted(true);
        mnuItemConsultaMov.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaMovActionPerformed(evt);
            }
        });
        mnuCaja.add(mnuItemConsultaMov);

        jMenuBar1.add(mnuCaja);

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
        mnuItemUnidades.setText("  Unidades");
        mnuItemUnidades.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemUnidades.setBorderPainted(true);
        mnuItemUnidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemUnidadesActionPerformed(evt);
            }
        });
        mnuRegistros.add(mnuItemUnidades);

        jMenuBar1.add(mnuRegistros);

        mnuVentas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuVentas.setText("  Ventas  ");
        mnuVentas.setBorderPainted(true);
        mnuVentas.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        mnuItemRegistrarVentas.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarVentas.setText(" Registrar Venta ");
        mnuItemRegistrarVentas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarVentas.setBorderPainted(true);
        mnuItemRegistrarVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarVentasActionPerformed(evt);
            }
        });
        mnuVentas.add(mnuItemRegistrarVentas);

        mnuItemConsultarVendas.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultarVendas.setText(" Consultar Ventas ");
        mnuItemConsultarVendas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemConsultarVendas.setBorderPainted(true);
        mnuItemConsultarVendas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultarVendasActionPerformed(evt);
            }
        });
        mnuVentas.add(mnuItemConsultarVendas);

        mnuItemRegistrarVentas2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistrarVentas2.setText(" Registrar Pedido ");
        mnuItemRegistrarVentas2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistrarVentas2.setBorderPainted(true);
        mnuItemRegistrarVentas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistrarVentas2ActionPerformed(evt);
            }
        });
        mnuVentas.add(mnuItemRegistrarVentas2);

        jMenuBar1.add(mnuVentas);

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

        mnuCuentasClientes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuCuentasClientes.setText("  Cuentas a Recibir   ");
        mnuCuentasClientes.setBorderPainted(true);
        mnuCuentasClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mnuCuentasClientes.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        mnuItemConsultaCuentas.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultaCuentas.setText("  Cuentas de Clientes  ");
        mnuItemConsultaCuentas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemConsultaCuentas.setBorderPainted(true);
        mnuItemConsultaCuentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaCuentasActionPerformed(evt);
            }
        });
        mnuCuentasClientes.add(mnuItemConsultaCuentas);

        jMenuBar1.add(mnuCuentasClientes);

        mnuCuentasProveedores.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        mnuCuentasProveedores.setText("  Cuentas a Pagar  ");
        mnuCuentasProveedores.setBorderPainted(true);
        mnuCuentasProveedores.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        mnuCuentasProveedores.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N

        mnuItemConsultaCuentasProveedores.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultaCuentasProveedores.setText("  Registrar pagos proveedores   ");
        mnuItemConsultaCuentasProveedores.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemConsultaCuentasProveedores.setBorderPainted(true);
        mnuItemConsultaCuentasProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaCuentasProveedoresActionPerformed(evt);
            }
        });
        mnuCuentasProveedores.add(mnuItemConsultaCuentasProveedores);

        mnuItemConsultaPagosProveedores.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemConsultaPagosProveedores.setText("  Consulta pagos proveedores   ");
        mnuItemConsultaPagosProveedores.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemConsultaPagosProveedores.setBorderPainted(true);
        mnuItemConsultaPagosProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemConsultaPagosProveedoresActionPerformed(evt);
            }
        });
        mnuCuentasProveedores.add(mnuItemConsultaPagosProveedores);

        mnuItemRegistraCuentaFija.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        mnuItemRegistraCuentaFija.setText("  Registro de gastos fijos");
        mnuItemRegistraCuentaFija.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        mnuItemRegistraCuentaFija.setBorderPainted(true);
        mnuItemRegistraCuentaFija.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuItemRegistraCuentaFijaActionPerformed(evt);
            }
        });
        mnuCuentasProveedores.add(mnuItemRegistraCuentaFija);

        jMenuBar1.add(mnuCuentasProveedores);

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

        ProveedorFrame proveedorFrame = new ProveedorFrame();
        desktopPane.add(proveedorFrame);
        centralizaJanela(proveedorFrame);
        proveedorFrame.setVisible(true);
        proveedorFrame.toFront();

    }//GEN-LAST:event_mnuItemProveedoresActionPerformed

    private void mnuItemProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemProductosActionPerformed

        ArticulosFrame articulos = new ArticulosFrame();
        desktopPane.add(articulos);
        articulos.setLocation(1, 1);
        articulos.setVisible(true);
        articulos.setSize(900, desktopPane.getHeight());
        articulos.toFront();

    }//GEN-LAST:event_mnuItemProductosActionPerformed

    private void mnuItemRegistrarVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarVentasActionPerformed

        RegistraVentaFrame registraVentaFrame = new RegistraVentaFrame();
        desktopPane.add(registraVentaFrame);
        centralizaJanela(registraVentaFrame);
        registraVentaFrame.setVisible(true);
        registraVentaFrame.toFront();
    }//GEN-LAST:event_mnuItemRegistrarVentasActionPerformed

    private void mnuItemConsultarVendasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultarVendasActionPerformed

        ConsultaVentaFrame consultaVenta = new ConsultaVentaFrame();
        desktopPane.add(consultaVenta);
        centralizaJanela(consultaVenta);
        consultaVenta.setVisible(true);
        consultaVenta.toFront();
    }//GEN-LAST:event_mnuItemConsultarVendasActionPerformed

    private void mnuItemRegistrarVentas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarVentas1ActionPerformed

        RegistraCompraFrame registraCompra = new RegistraCompraFrame();
        desktopPane.add(registraCompra);
        centralizaJanela(registraCompra);
        registraCompra.setVisible(true);
        registraCompra.toFront();

    }//GEN-LAST:event_mnuItemRegistrarVentas1ActionPerformed

    private void mnuItemConsultarVendas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultarVendas1ActionPerformed

        ConsultaCompraFrame consultaCompra = new ConsultaCompraFrame();
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

    private void mnuItemMoviminetosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemMoviminetosActionPerformed

        cajaFrame caja = new cajaFrame();
        desktopPane.add(caja);
        centralizaJanela(caja);
        caja.setVisible(true);
        caja.toFront();

    }//GEN-LAST:event_mnuItemMoviminetosActionPerformed

    private void mnuItemConsultaMovActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaMovActionPerformed

        consultaCajaFrame consultaCaja = new consultaCajaFrame();
        desktopPane.add(consultaCaja);
        consultaCaja.setLocation(1, 1);
        consultaCaja.setVisible(true);
        consultaCaja.toFront();

    }//GEN-LAST:event_mnuItemConsultaMovActionPerformed

    private void btnConsultaArticulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultaArticulosActionPerformed

        ArticulosFrame articulos = new ArticulosFrame();
        desktopPane.add(articulos);
        articulos.setLocation(1, 1);
        articulos.setVisible(true);
        articulos.setSize(900, desktopPane.getHeight());
        articulos.toFront();

    }//GEN-LAST:event_btnConsultaArticulosActionPerformed

    private void btnVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentaActionPerformed

        RegistraVentaFrame registraVentaFrame = new RegistraVentaFrame();
        desktopPane.add(registraVentaFrame);
        centralizaJanela(registraVentaFrame);
        registraVentaFrame.setVisible(true);
        registraVentaFrame.toFront();

    }//GEN-LAST:event_btnVentaActionPerformed

    private void btnCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCajaActionPerformed

        cajaFrame caja = new cajaFrame();
        desktopPane.add(caja);
        caja.setLocation(1, 1);
        caja.setVisible(true);
        caja.toFront();

    }//GEN-LAST:event_btnCajaActionPerformed

    private void mnuItemCotizaciónActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemCotizaciónActionPerformed

        FrameRegistraCotizacion frame = new FrameRegistraCotizacion();
        centralizaJanela(frame);
        desktopPane.add(frame);
        frame.setVisible(true);
        frame.toFront();
    }//GEN-LAST:event_mnuItemCotizaciónActionPerformed

    private void btnCalculadoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalculadoraActionPerformed

        try {
            Runtime.getRuntime().exec("cmd.exe /C start calc.exe");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No fue posible abrir la calculadora, error: " + ex);
        }

    }//GEN-LAST:event_btnCalculadoraActionPerformed

    private void mnuItemConsultaCuentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaCuentasActionPerformed

        ConsultaCuentasRecibir consultaCreditos = new ConsultaCuentasRecibir();
        desktopPane.add(consultaCreditos);
        consultaCreditos.setLocation(1, 1);
        consultaCreditos.setVisible(true);
        consultaCreditos.toFront();
    }//GEN-LAST:event_mnuItemConsultaCuentasActionPerformed

    private void mnuItemConsultaCuentasProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaCuentasProveedoresActionPerformed

        RegistraPagos pagos = new RegistraPagos();
        desktopPane.add(pagos);
        pagos.setLocation(1, 1);
        pagos.setVisible(true);
        pagos.toFront();

    }//GEN-LAST:event_mnuItemConsultaCuentasProveedoresActionPerformed

    private void mnuItemConsultaCuentasProveedores1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaCuentasProveedores1ActionPerformed

        JOptionPane.showMessageDialog(null, "<html><font size=6 face=Verdana color=black><center>Sistema de Control Comercial<br><br>"
                + "Desarrollado por D.N.Soft</center><br><br>"
                + "<font size=5 face=Verdana color=black>Contactos: Diego Noble<br><br>"
                + "cel: 099493567<br><br>"
                + "E-mail: noblediego@hotmail.com<br><br></html>");

    }//GEN-LAST:event_mnuItemConsultaCuentasProveedores1ActionPerformed

    private void mnuItemCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemCategoriasActionPerformed

        CategoriaFrame categoria = new CategoriaFrame();
        desktopPane.add(categoria);
        categoria.setLocation(1, 1);
        categoria.setVisible(true);
        categoria.toFront();

    }//GEN-LAST:event_mnuItemCategoriasActionPerformed

    private void mnuItemRegistraCuentaFijaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistraCuentaFijaActionPerformed

        RegistroGastosFijos gastos = new RegistroGastosFijos();
        desktopPane.add(gastos);
        gastos.setLocation(1, 1);
        gastos.setVisible(true);
        gastos.toFront();

    }//GEN-LAST:event_mnuItemRegistraCuentaFijaActionPerformed

    private void mnuItemUnidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemUnidadesActionPerformed

        UnidadFrame unidad = new UnidadFrame();
        centralizaJanela(unidad);
        desktopPane.add(unidad);
        unidad.setVisible(true);
        unidad.toFront();


    }//GEN-LAST:event_mnuItemUnidadesActionPerformed

    private void mnuItemConsultaPagosProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemConsultaPagosProveedoresActionPerformed

        ConsultaPagosCompras pagosProveedores = new ConsultaPagosCompras();
        centralizaJanela(pagosProveedores);
        desktopPane.add(pagosProveedores);
        pagosProveedores.setVisible(true);
        pagosProveedores.toFront();

    }//GEN-LAST:event_mnuItemConsultaPagosProveedoresActionPerformed

    private void mnuItemRegistrarVentas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuItemRegistrarVentas2ActionPerformed
        RegistraPedido registraPedido = new RegistraPedido();
        centralizaJanela(registraPedido);
        desktopPane.add(registraPedido);
        registraPedido.setVisible(true);
        registraPedido.toFront();
    }//GEN-LAST:event_mnuItemRegistrarVentas2ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    new MenuPrincipal().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCaja;
    private javax.swing.JButton btnCalculadora;
    private javax.swing.JButton btnConsultaArticulos;
    private javax.swing.JButton btnVenta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JMenu mnuAyuda;
    private javax.swing.JMenu mnuCaja;
    private javax.swing.JMenu mnuCompras;
    private javax.swing.JMenu mnuCuentasClientes;
    private javax.swing.JMenu mnuCuentasProveedores;
    private javax.swing.JMenuItem mnuItemCategorias;
    private javax.swing.JMenuItem mnuItemClientes;
    private javax.swing.JMenuItem mnuItemConsultaCuentas;
    private javax.swing.JMenuItem mnuItemConsultaCuentasProveedores;
    private javax.swing.JMenuItem mnuItemConsultaCuentasProveedores1;
    private javax.swing.JMenuItem mnuItemConsultaMov;
    private javax.swing.JMenuItem mnuItemConsultaPagosProveedores;
    private javax.swing.JMenuItem mnuItemConsultarVendas;
    private javax.swing.JMenuItem mnuItemConsultarVendas1;
    private javax.swing.JMenuItem mnuItemCotización;
    private javax.swing.JMenuItem mnuItemMoviminetos;
    private javax.swing.JMenuItem mnuItemProductos;
    private javax.swing.JMenuItem mnuItemProveedores;
    private javax.swing.JMenuItem mnuItemRegistraCuentaFija;
    private javax.swing.JMenuItem mnuItemRegistrarVentas;
    private javax.swing.JMenuItem mnuItemRegistrarVentas1;
    private javax.swing.JMenuItem mnuItemRegistrarVentas2;
    private javax.swing.JMenuItem mnuItemSalir;
    private javax.swing.JMenuItem mnuItemSesion;
    private javax.swing.JMenuItem mnuItemUnidades;
    private javax.swing.JMenuItem mnuItemUsuarios;
    private javax.swing.JMenu mnuRegistros;
    private javax.swing.JMenu mnuSistema;
    private javax.swing.JMenu mnuVentas;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPerfil;
    // End of variables declaration//GEN-END:variables
}
