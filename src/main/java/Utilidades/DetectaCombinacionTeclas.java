/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class DetectaCombinacionTeclas {

    boolean swcontrol = false;
    boolean swshift = false;

    public void init(Component component) {
        component.addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    swcontrol = true;
                } else {
                    if (swcontrol && e.getKeyCode() == KeyEvent.VK_P) {
                        Actualiza actualiza = new Actualiza();
                        actualiza.actualizaArticulosPedido();
                        swcontrol = false;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    swshift = true;
                } else {
                    if (swshift && e.getKeyCode() == KeyEvent.VK_C) {
                        Actualiza actualiza = new Actualiza();
                        actualiza.actualizaArticulosCompra();
                        swshift = false;
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    swcontrol = true;
                } else {
                    if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                        swshift = true;
                    } else {
                        if (swcontrol && swshift && e.getKeyCode() == KeyEvent.VK_D) {
                            System.out.println("D");
                            swcontrol = false;
                            swshift = false;
                        }
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

        });

    }
}
