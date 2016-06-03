/**
 * MyTimeCellRenderer.java
 *
 * $Id$
 *
 */
package com.Renderers;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 * @author Dyego Souza do Carmo
 * @since 
 */
public class MyTimeCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    
    /**
     * Construtor padrão
     *
     * @author Dyego Souza do Carmo
     * @version 1.0, 
     */
    public MyTimeCellRenderer(){
        super();
    }

    @Override
    protected void setValue(Object value) {
        if (value != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            Date valuefOfdate = (Date) value;
            super.setValue(sdf.format(valuefOfdate));
        } else {
        super.setValue(value);
        }
    }



}
