/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

public class Utilisateur {
    private int id;
    private String email;
    private String nom;
    private String prenom;
    private Boolean isVerified;
    private String password;
    private String nomsociete;
    private String[] roles;

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
    
    public void setNomsociete(String nomsociete) {
        this.nomsociete = nomsociete;
    }

    public String getNomsociete() {
        return nomsociete;
    }
    
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public String getPassword() {
        return password;
    }

    public String[] getRoles() {
        return roles;
    }
    

   






    public Utilisateur(String email,String nom, String prenom, String password,String isVerified,String nomsociete, String roles, String rue) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.nomsociete = nomsociete;
        
    }
    
    
     @Override
    public String toString() {
        return "Utilisateur{" + "id=" + id + ", email=" + email + ", nom=" + nom + ", prenom=" + prenom + ", password=" + password +'}';
    }
    
}
