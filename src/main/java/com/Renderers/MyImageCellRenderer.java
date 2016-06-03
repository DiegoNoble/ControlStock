/**
 * MyImageCellRenderer.java
 *
 * $Id$
 *
 */
package com.Renderers;

import java.awt.Color;
import java.awt.Component;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 * @author Dyego Souza do Carmo
 * @since 
 */
public class MyImageCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;
    private ImageIcon iconToSee;
    private Border borderToSee;

    /**
     * Construtor padrão
     *
     * @author Dyego Souza do Carmo
     * @version 1.0, 
     */
    public MyImageCellRenderer() {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setIcon(iconToSee);
        setBorder(borderToSee);
        return this;
    }

    @Override
    protected void setValue(Object value) {
        super.setValue(value);
        try {
            if ("JAVA".equalsIgnoreCase(value.toString())) {
                iconToSee = new ImageIcon(new URL("http://www.virtualvision.ca/images/icon_java.gif"));
                borderToSee = null;
            } else {
                iconToSee = new ImageIcon(new URL("http://www.moevenpick-wein.com/img/layout/icon_language.gif"));
                borderToSee = BorderFactory.createLineBorder(Color.RED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
