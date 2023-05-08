/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.services.budget;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author ihebt
 */
public interface depenseInterface <T,B> {
    public void ajouter(T t,B b);
    public List<T> getAll();
     void supprimer_depense(T t);
      public void modifier_depense(B budget,String nom,float montant,LocalDate D,String justificatif,String categorie,T t);
      
}
