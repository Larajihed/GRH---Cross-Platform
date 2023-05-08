/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.services.user;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import COM.HRSTORMDESKTOP.Config;

import COM.HRSTORMDESKTOP.infrastructure.DBConnector;
import COM.HRSTORMDESKTOP.models.user.User;
//import masterhrdesktopv2.services.feuilletemps.ImpServiceFeuilleTemps;
import COM.HRSTORMDESKTOP.utils.PasswordEncryption;

/**
 *
 * @author Achref
 */
public class ImpServiceUser implements IServiceUser {

    private Connection dbcon;
    private PreparedStatement pst;
    private static ImpServiceUser instance;

    public ImpServiceUser() {
        dbcon = DBConnector.getInstance().getCnx();
    }

    public static ImpServiceUser getInstance() {
        if (instance == null) {
            instance = new ImpServiceUser();
        }
        return instance;
    }

    @Override
    public User Add(User entity) {
        String req = "INSERT INTO `user` (`email`, `roles` ,"
                + "`password` , `is_verified` , `nom` , "
                + "` `prenom` , `nomsociete`, )"
                + "VALUES ( ? , ? , ? , 1 , ? , ? , ? ) ";
        try {
            pst = dbcon.prepareStatement(req);
            pst.setString(6, entity.getNom());
            pst.setString(5,  entity.getPrenom());
            pst.setString(3, entity.getPassword());
            pst.setString(7, entity.getNomsociete());
            pst.setString(2, entity.getRoles());
            pst.setString(1, entity.getEmail());

            pst.executeUpdate();
            System.out.println("Utilisateur ajouté");
            return entity;

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }

    }

    @Override
    public void Delete(User entity) {
        String req = "DELETE FROM `user` WHERE `IdUser` = ?";
        try {

            pst = dbcon.prepareStatement(req);
            pst.setInt(1, entity.getId());
            pst.executeUpdate();
            System.out.println("Utilisateur supprimé");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());

        }
    }

    @Override
    public void Update(User entity) {
        String req = "UPDATE `user` SET `email` = ? , `roles` = ?"
                + " , `password` = ? ,  `is_verified` = 1 , `nom` = ? "
                + " , `prenom` = ? , `nomsociete` = ? "
                + " WHERE `IdUser` = ?";
        try {
            pst = dbcon.prepareStatement(req);
            pst.setString(5, entity.getNom());
            pst.setString(6,  entity.getPrenom());
            pst.setString(3, entity.getPassword());
            pst.setString(7, entity.getNomsociete());
            pst.setString(2, entity.getRoles());
            pst.setString(1, entity.getEmail());
            pst.executeUpdate();
            System.out.println("Utilisateur modifié");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());

        }

    }

    @Override
    public List<User> Getall() {
        List<User> users = new ArrayList<>();

        String req = "SELECT * from `user`";

        try {
            pst = dbcon.prepareStatement(req);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt(1));
                u.setEmail(rs.getString(2));
                u.setRoles(rs.getString(3));
                u.setPassword(rs.getString(4));
                u.setIs_verified(rs.getBoolean(5));
                u.setPrenom(rs.getString(6));
                u.setNom(rs.getString(7));
                u.setNomsociete(rs.getString(8));
                users.add(u);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return users;
    }

    @Override
    public User GetById(int ID) {
        String req = "SELECT * from `user` WHERE `IdUser` = ?";
        try {
            pst = dbcon.prepareStatement(req);
            pst.setInt(1, ID);
            ResultSet rs = pst.executeQuery();

            User u = new User();
            rs.next();
                u.setId(rs.getInt(1));
                u.setEmail(rs.getString(2));
                u.setRoles(rs.getString(3));
                u.setPassword(rs.getString(4));
                u.setIs_verified(rs.getBoolean(5));
                u.setPrenom(rs.getString(6));
                u.setNom(rs.getString(7));
                u.setNomsociete(rs.getString(8));

            return u;

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }
    

    public List<User> GetByNomSociete(User u) {
        List<User> users = new ArrayList<>();
        String req = "SELECT * from `user` WHERE `nomsociete` = ?";
        try {
            pst = dbcon.prepareStatement(req);
            pst.setString(8,req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                User us = new User();
                u.setId(rs.getInt(1));
                u.setEmail(rs.getString(2));
                u.setRoles(rs.getString(3));
                u.setPassword(rs.getString(4));
                u.setIs_verified(rs.getBoolean(5));
                u.setPrenom(rs.getString(6));
                u.setNom(rs.getString(7));
                u.setNomsociete(rs.getString(8));
                users.add(us);
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return users;
    }

    @Override
    public List<User> GetResponsables(User u) {
        List<User> users = new ArrayList<>();
        String req = "SELECT * from `user` WHERE `Roles` = HRRESPONSABLE";
        try {
            pst = dbcon.prepareStatement(req);
            pst.setString(3, req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                User us = new User();
                u.setId(rs.getInt(1));
                u.setEmail(rs.getString(2));
                u.setPassword(rs.getString(4));
                u.setIs_verified(rs.getBoolean(5));
                u.setPrenom(rs.getString(6));
                u.setNom(rs.getString(7));
                u.setNomsociete(rs.getString(8));
                users.add(us);
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return users;
    }

    public int countemploye(int idsociete) {
        User u = new User();
        String req = "SELECT count(*) as it from User where  `nomsociete` = ?";
        int count = 0;

        try {
            Statement st = dbcon.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {

                count = rs.getInt("it");

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(u.getId() + "id");
        return count;

    }

    @Override
    public List<User> GetByName(String nom) {
        List<User> users = new ArrayList<>();
        String req = "SELECT * from `user` WHERE `nom` = ? ";
        try {
            pst = dbcon.prepareStatement(req);
            pst.setString(7, nom);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                User us = new User();
                us.setId(rs.getInt(1));
                us.setEmail(rs.getString(2));
                us.setRoles(rs.getString(3));
                us.setPassword(rs.getString(4));
                us.setIs_verified(rs.getBoolean(5));
                us.setPrenom(rs.getString(6));
                us.setNomsociete(rs.getString(8));
                users.add(us);
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return users;
    }

    @Override
    public List<User> SortByName() {
        List<User> users = new ArrayList<>();

        String req = "SELECT * from `user` ORDER BY `nom`";

        try {
            pst = dbcon.prepareStatement(req);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt(1));
                u.setEmail(rs.getString(2));
                u.setRoles(rs.getString(3));
                u.setPassword(rs.getString(4));
                u.setIs_verified(rs.getBoolean(5));
                u.setPrenom(rs.getString(6));
                u.setNomsociete(rs.getString(8));
                users.add(u);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return users;
    }

   
    @Override
    public User Login(String email, String password) {
        String req = "SELECT * from `User` WHERE `email` = ? ";
        
        PasswordEncryption pe = new PasswordEncryption();
        User u = new User();
        try {
            pst = dbcon.prepareStatement(req);
            System.out.println(pst);
            pst.setString(1, email);
            System.out.println(pst);

            ResultSet rs = pst.executeQuery();


            rs.next();
            System.out.println(rs);

            if (rs.getRow() != 0) {
                System.out.println("waa");
                u.setEmail(rs.getString("email"));

                if (pe.Verify(password, rs.getString("password") )) {
                u.setId(rs.getInt("id"));
                u.setRoles(rs.getString("roles"));
                u.setPassword(rs.getString("password"));
                u.setIs_verified(rs.getBoolean("is_verified"));
                u.setPrenom(rs.getString("prenom"));
                u.setNom(rs.getString("nom"));
                //u.setNomsociete(rs.getString("nomsociete"));
                }

            }
            return u;

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }

    }

//        return "User{" + "id=" + id + ", prenom=" + prenom + ", roles=" + roles + ", email=" + email + ", nom=" + nom + ", password=" + password + ", nomsociete=" + nomsociete + ", is_verified=" + is_verified + '}';

    

/*    @Override
    public void AddFromCSV(String filepath) {
        String req = "INSERT INTO `user` (`NomPrenom`, `Adresse` ,"
                + "`Cin` , `Role` , `DocumentLegit` , "
                + "`DateNaissance` , `Email` , `PasswordHash`, "
                + "`PasswordSalt` , `IsResponsable` , "
                + "`FeuilleDeTempsPriv` , `CongePriv` , "
                + "`RecrutementPriv` , `Face`, `IdSociete`)"
                + "VALUES (? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?) ";
        int batchSize = 20;

        try {
            pst = dbcon.prepareStatement(req);
            BufferedReader lineReader = new BufferedReader(new FileReader(filepath));

            String lineText = null;
            int count = 0;

            // To JUMP COLUMNS NAMES
            //lineReader.readLine();
            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");

                pst = dbcon.prepareStatement(req);
                pst.setString(1, data[0]);
                pst.setString(2, data[1]);
                pst.setString(3, data[2]);
                pst.setString(4, data[3]);
                pst.setString(5, data[4]);
                pst.setDate(6, Date.valueOf(data[5]));
                pst.setString(7, data[6]);
                pst.setString(8, data[7]);
                pst.setString(9, data[8]);
                pst.setBoolean(10, Boolean.parseBoolean(data[9]));
                pst.setBoolean(11, Boolean.parseBoolean(data[10]));
                pst.setBoolean(12, Boolean.parseBoolean(data[11]));
                pst.setBoolean(13, Boolean.parseBoolean(data[12]));
                pst.setString(14, data[13]);
                pst.setInt(15, Config.UserStatic.getSociete().getIdSociete());
                pst.addBatch();
                if (count % batchSize == 0) {
                    pst.executeBatch();
                }
            }
            lineReader.close();
            pst.executeBatch();

            System.out.println("Data has been inserted successfully.");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

    } */

    @Override
    public User GetByMail(String email) {
        String req = "SELECT * from `user` WHERE `email` =?";
        try {
            pst = dbcon.prepareStatement(req);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            User u = new User();
            rs.next();
            if (rs.getRow() != 0) {
            }
            return u;

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    public User GetByprenom(String prenom)   {
        String req = "SELECT * from `user` WHERE `prenom` = ";
        try {
            pst = dbcon.prepareStatement(req);
            pst.setString(6, prenom);
            ResultSet rs = pst.executeQuery();

            User u = new User();
            rs.next();
            if (rs.getRow() != 0) {
                u.setId(rs.getInt(1));
                u.setEmail(rs.getString(2));
                u.setRoles(rs.getString(3));
                u.setPassword(rs.getString(4));
                u.setIs_verified(rs.getBoolean(5));
                u.setNom(rs.getString(7));
                u.setNomsociete(rs.getString(8));
            }
            return u;

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<User> GetByRespSociete(User u) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User GetByCin(String cin) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void AddFromCSV(String filepath) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
