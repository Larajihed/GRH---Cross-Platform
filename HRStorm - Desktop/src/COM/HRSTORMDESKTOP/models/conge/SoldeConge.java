/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package COM.HRSTORMDESKTOP.models.Conge;

/**
 *
 * @author Marwen
 */
public class SoldeConge {
    
    private int id ; 
    private int id_user; 
    private int solde ; 

    public SoldeConge(int id, int id_user, int solde) {
        this.id = id;
        this.id_user = id_user;
        this.solde = solde;
    }

    public SoldeConge(int id_user, int solde) {
        this.id_user = id_user;
        this.solde = solde;
    }

    @Override
    public String toString() {
        return "SoldeConge{" + "id=" + id + ", id_user_=" + id_user + ", solde=" + solde + '}';
    }

    public SoldeConge() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return id_user;
    }

    public void setIduser(int id_user) {
        this.id_user = id_user;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }


    
}
