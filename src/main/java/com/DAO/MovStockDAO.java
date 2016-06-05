/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import com.Beans.MovStock;

/**
 *
 * @author Diego
 */
public class MovStockDAO extends DAOGenerico<Object> {

    public MovStockDAO() {
    }

    public MovStockDAO(MovStock objeto) {
        super.objeto = objeto;
    }

}
