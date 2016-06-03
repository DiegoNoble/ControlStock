
package com.DAO;

import java.util.List;

public interface IDAO {
    
    public void registra ();
    public void actualiza();
    public void elimina();
    public List BuscaTodos(Class classe);
    public Object buscarPorID(Class classe, int id);
    
}
