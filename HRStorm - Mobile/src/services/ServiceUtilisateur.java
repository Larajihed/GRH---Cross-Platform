/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.codename1.io.ConnectionRequest;
import com.codename1.ui.util.Resources;



import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.util.Resources;
import utils.Statics;
import com.codename1.io.JSONParser;
import gui.SessionManager;
import gui.LoginForm;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Map;
import gui.HomeForm;


/**
 *
 * @author user
 */
public class ServiceUtilisateur {
     //singleton 
    public static ServiceUtilisateur instance = null ;
    
    public static boolean resultOk = true;
    String json;

    //initilisation connection request 
    private final ConnectionRequest req;
    
    
    public static ServiceUtilisateur getInstance() {
        if(instance == null )
            instance = new ServiceUtilisateur();
        return instance ;
    }
    
    
    
    public ServiceUtilisateur() {
        req = new ConnectionRequest();
        
    }
    

    
    
    
   public void signin(String email, String password) {
    try {
        req.setPost(false);
        req.setUrl("http://127.0.0.1:8000/Codenameone/service/login/");
        req.addArgument("email", email);
        req.addArgument("password", password);

    } catch (Exception e) {
        System.out.println(e.getMessage());
        System.out.println("A");
    }
    req.addResponseListener((e) -> {
        JSONParser j = new JSONParser();
        try {
            Map<String, Object> Response = j.parseJSON(new InputStreamReader(new ByteArrayInputStream(req.getResponseData())));
            String ErrorMessage = Response.get("Error").toString();
            if (!ErrorMessage.equals("")) {
                Dialog.show("Authentication failed", ErrorMessage, "OK", null);
                
            } else {
                /*
                float id = Float.parseFloat(Response.get("id").toString());
                
                SessionManager.setId((int)id);
                SessionManager.setPassword(Response.get("password").toString());
                SessionManager.setNom(Response.get("nom").toString());
                SessionManager.setPrenom(Response.get("prenom").toString());
                System.out.println(Response.get("userIdentifier").toString());
                SessionManager.setEmail(Response.get("userIdentifier").toString());
                //SessionManager.setNomsociete(Response.get("nomsociete").toString());
                */
                new HomeForm().show();
                
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    });
    NetworkManager.getInstance().addToQueueAndWait(req);
}

    
    
public void register(String email, String password, String nom, String prenom, String nomsociete) {
    try {
        req.setPost(false);
        req.setUrl("http://127.0.0.1:8000/Codenameone/service/RHresponsable/");
        req.addArgument("email", email);
        req.addArgument("password", password);
        req.addArgument("nom", nom);
        req.addArgument("prenom", prenom);
        req.addArgument("nomsociete", nomsociete);

    } catch (Exception e) {
        System.out.println(e.getMessage());
        System.out.println("A");
    }
    req.addResponseListener((e) -> {
        JSONParser j = new JSONParser();

        try {
            Map<String, Object> Response = j.parseJSON(
                new InputStreamReader(new ByteArrayInputStream(req.getResponseData()))
            );

            String errorMessage = Response.get("Error").toString();
            if (!errorMessage.equals("")) {
                Dialog.show("Registration failed.", errorMessage, "OK", null);
            } else {
                Dialog.show("Success", "Account created successfully.", "OK", null);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    });
    NetworkManager.getInstance().addToQueueAndWait(req);
}

 
    }

