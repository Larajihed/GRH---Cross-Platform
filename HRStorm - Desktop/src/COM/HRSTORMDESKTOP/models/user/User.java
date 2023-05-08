/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package COM.HRSTORMDESKTOP.models.user;

import java.sql.Date;

/**
 *
 * @author 21627
 */
public class User {
        private int id;
    private String prenom;
    private String roles;
    private String email;
    private String nom;
    private String password;
    private String nomsociete;
    private boolean is_verified;
    public User() {
    }

    public User(int id, String prenom, String roles, String email, String nom, String password, String nomsociete) {
        this.id = id;
        this.prenom = prenom;
        this.roles = roles;
        this.email = email;
        this.nom = nom;
        this.password = password;
        this.nomsociete = nomsociete;
    }

    public User(int id, String prenom, String roles, String email, String nom, String password) {
        this.id = id;
        this.prenom = prenom;
        this.roles = roles;
        this.email = email;
        this.nom = nom;
        this.password = password;
    }
    
    public User(String prenom, String roles, String email, String nom, String password, boolean is_verified) {
        this.prenom = prenom;
        this.roles = roles;
        this.email = email;
        this.nom = nom;
        this.password = password;
        this.is_verified = is_verified;
    }
    
    public User(String prenom, String roles, String email, String nom, String password, String nomsociete, boolean is_verified) {
        this.prenom = prenom;
        this.roles = roles;
        this.email = email;
        this.nom = nom;
        this.password = password;
        this.nomsociete = nomsociete;
        this.is_verified = is_verified;
    }
    
    public User(int id, String prenom, String roles, String email, String nom, String password, String nomsociete, boolean is_verified) {
        this.id = id;
        this.prenom = prenom;
        this.roles = roles;
        this.email = email;
        this.nom = nom;
        this.password = password;
        this.nomsociete = nomsociete;
        this.is_verified = is_verified;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNomsociete() {
        return nomsociete;
    }

    public void setNomsociete(String nomsociete) {
        this.nomsociete = nomsociete;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

   @Override
public String toString() {
    return String.format("Name: %s %s | Email: %s | Company: %s | Verified: %s",
        prenom, nom, email, nomsociete, is_verified ? "Yes" : "No");
}


 
}

