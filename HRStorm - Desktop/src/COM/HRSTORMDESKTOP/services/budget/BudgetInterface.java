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
public interface BudgetInterface <T> {
   public void ajouter(T t);
    public List<T> getAll();
     void supprimer_budget(T t);
      public void modifier_budget(float budget,LocalDate  d,float prime,float budget_materiel,float budget_Service,float budget_salaire,T t);
      public List<Float> getBudget(); 
}
