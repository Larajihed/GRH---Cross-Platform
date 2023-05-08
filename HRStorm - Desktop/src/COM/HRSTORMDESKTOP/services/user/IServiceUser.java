/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.services.user;

import java.util.List;
import COM.HRSTORMDESKTOP.infrastructure.IService;
import COM.HRSTORMDESKTOP.models.user.User;

/**
 *
 * @author 21623
 */
public interface IServiceUser extends IService<User> {
     List<User> GetByRespSociete (User u);
     List<User> GetResponsables (User u);
     User GetByMail(String Email);
     User GetByCin(String cin);
     List<User> GetByName(String NomPrenom);
     List<User> SortByName();
     User Login(String email, String password);
     void AddFromCSV(String filepath);

     
    
}
