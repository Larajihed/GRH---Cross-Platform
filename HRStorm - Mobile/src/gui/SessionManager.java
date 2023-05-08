/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.codename1.io.Storage;
import entities.User;

public class SessionManager {
    private static User user;
    private static final String ID_KEY = "id";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final String NOM_KEY = "nom";
    private static final String PRENOM_KEY = "prenom";
    private static final String NOM_SOCIETE_KEY = "nomsociete";
    
    
    
    public static User getUser() {
    return user;
}
    public static void setId(long id) {
    Storage.getInstance().writeObject(ID_KEY, id);
}

public static long getId() {
    return (long) Storage.getInstance().readObject(ID_KEY);
}
    
    public static void setEmail(String email) {
        System.out.println(email);
        Storage.getInstance().writeObject(EMAIL_KEY, email);
    }
    
    public static String getEmail() {
        return (String) Storage.getInstance().readObject(EMAIL_KEY);
    }
    
    public static void setPassword(String password) {
        Storage.getInstance().writeObject(PASSWORD_KEY, password);
    }
    
    public static String getPassword() {
        return (String) Storage.getInstance().readObject(PASSWORD_KEY);
    }
    
    public static void setNom(String nom) {
        Storage.getInstance().writeObject(NOM_KEY, nom);
    }
    
    public static String getNom() {
        return (String) Storage.getInstance().readObject(NOM_KEY);
    }
    
    public static void setPrenom(String prenom) {
        Storage.getInstance().writeObject(PRENOM_KEY, prenom);
    }
    
    public static String getPrenom() {
        return (String) Storage.getInstance().readObject(PRENOM_KEY);
    }
    
    public static void setNomsociete(String nomsociete) {
        Storage.getInstance().writeObject(NOM_SOCIETE_KEY, nomsociete);
    }
    
    public static String getNomsociete() {
        return (String) Storage.getInstance().readObject(NOM_SOCIETE_KEY);
    }
    
    public static void clear() {
Storage.getInstance().deleteStorageFile("myFile.txt");
    }
}
