/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.services.Planning;

import java.util.List;

/**
 *
 * @author farouk
 */
public interface IService<R> {
    
    public void ajout(R r);
    public void modifier(R r);
    public void supprimer(int id);
    public List<R> afficher();
    
}
