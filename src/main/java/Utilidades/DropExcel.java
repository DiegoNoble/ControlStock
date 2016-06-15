/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
/**
 *
 * @author Diego Noble
 */
public class DropExcel implements DropTargetListener{
    private DropTarget dt;
    private JTable jtable;
    private DefaultTableModel TableModel = new DefaultTableModel();

    public DropExcel( JTable jtable ){
        this.jtable = jtable;
        dt = new DropTarget( jtable , this );  
    }

  @Override
  public void dragEnter(DropTargetDragEvent dtde) {}

  @Override
  public void dragExit(DropTargetEvent dte) {}

  @Override
  public void dragOver(DropTargetDragEvent dtde) {}

  @Override
  public void dropActionChanged(DropTargetDragEvent dtde) {}

  @Override
  public void drop(DropTargetDropEvent dtde) {
     try {
            /* proporciona datos para operaciones de transferencia en swing */
            Transferable tr = dtde.getTransferable();
            /* Devuelve una array de objetos DataFlavor */
            DataFlavor[] flavors = tr.getTransferDataFlavors();                        
            if( flavors.length > 0 ){                    
                /* Si existe una lista de objetos de archivo */
                if ( flavors[0].isFlavorJavaFileListType() ) {                     
                    dtde.acceptDrop( DnDConstants.ACTION_COPY );                    
                    /* obtiene un List con los archivos arrastrados al componente */
                    java.util.List list = (java.util.List) tr.getTransferData( flavors[0] );                    
                    if( !list.isEmpty() ){ /* abre el primer archivo */                        
                        File file = new File( list.get(0).toString() );
                        if ( file.exists() ){     
                            /* SI el archivo corresponde a un archivo excel */
                            if( file.getName().endsWith("xls") )
                            {
                                readXLS( file );
                            }else{
                                JOptionPane.showMessageDialog(null,"No es un archivo *.xls valido","Error", JOptionPane.ERROR_MESSAGE );                                
                            }                            
                        }else{ System.err.println( "error archivo no existe " ); }
                    }                    
                    dtde.dropComplete(true);
                    return;
                }
            }
            System.err.println("Drop failed: " + dtde );
            dtde.rejectDrop();
        } catch (Exception ex) {
            System.err.println( ex.getMessage() );
            dtde.rejectDrop();
        }
  }

  /**
 * Metodo para leer solo la primera hoja de un archivo excel y colocar los datos
 * en un DefaultTableModel
 * @param File xls archivo excel
 */
  private void readXLS( File xls ){               
        try { 
            Workbook workbook = Workbook.getWorkbook( xls );            
            /* Si existen hojas */
            if( workbook.getNumberOfSheets() > 0 ){
                Sheet hoja = workbook.getSheet( 0 ); /* obtiene solo la primera hoja */                
                /* forma el array para los nombres de las columnas del JTable */
                String[] columNames = new String[ hoja.getColumns() ];
                /* Forma la matriz para los datos */
                Object[][] data = new String[ hoja.getRows() ][ hoja.getColumns() ];  
                /* Recorre todas las celdas*/
                for ( int fila = 0; fila < hoja.getRows(); fila++ )
                {   
                    for ( int columna = 0; columna  < hoja.getColumns() ; columna++ )
                    {
                        /* Asigna nombre de columna */
                        columNames[columna]="Columna " + ( columna + 1 );
                        /* Lee celda y coloca en el array */
                        data[ fila ][ columna ] = hoja.getCell(columna, fila).getContents();
                    }                                        
                }
                /* Crea el TableModel y asigna a tabla */
                TableModel = new DefaultTableModel( data, columNames );
                jtable.setModel(TableModel);
            }

        } catch (IOException ex) {
            System.err.println( ex.getMessage() );
        } catch (BiffException ex) {
            System.err.println( ex.getMessage() );
        }
  }

  public DefaultTableModel getTableModel()
  {
      return this.TableModel;
  }

  public DropTarget getDropTarget()
  {
      return this.dt;
  }

}