

package Utilidades;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;


public class DesktopPaneImagem extends JDesktopPane{

    private String caminhoImagem;

    public DesktopPaneImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

    @Override
    protected void paintComponent(Graphics g) {
        try{
        super.paintComponent(g);

        ImageIcon img = new ImageIcon(caminhoImagem);
        
        g.drawImage(img.getImage(), 0, 0, this);
    }catch(Exception e){
        JOptionPane.showMessageDialog(null, e);
    }
    }
}
