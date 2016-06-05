/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Diego Noble
 */
public class ControlarEntradaTexto extends PlainDocument {

    Set<Character> validos;
    private final int tamanoMaximo;

    public ControlarEntradaTexto(int tamanoMaximo, Character[] caracteres) {
        super();
        this.tamanoMaximo = tamanoMaximo;
        this.validos = new HashSet<>(Arrays.asList(caracteres));
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null) {
            return;
        }

        String stringAntiga = getText(0, getLength());
        int tamanhoNovo = stringAntiga.length() + str.length();

        if (tamanhoNovo <= tamanoMaximo) {
            char[] chars = str.toCharArray();
            StringBuilder inserted = new StringBuilder();
            for (char ch : chars) {
                if (validos.contains(ch)) {
                    inserted.append(ch); //Filtramos só o que é válido  
                }
            }
            super.insertString(offs, inserted.toString(), a);
        } else {
            super.insertString(offs, "", a);
        }

    }

}
