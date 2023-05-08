/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import entities.Candidat;
import entities.Recrutment;
import utils.Statics;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author usoum
 */
public class Service {
    public static Service instance = null;
    public static boolean resultOk = true; 
    private ConnectionRequest req;
      public static Service getInstance() {
        if(instance == null )
            instance = new Service();
        return instance ;
    }
    
    
    
    public Service() {
        req = new ConnectionRequest();
        
    }
    public ArrayList<Recrutment>affichageReclamations() {
        ArrayList<Recrutment> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/employee/recrutement/affiche_offre_mobile";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                
                try {
                    
                    Map<String,Object>mapReclamations = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapReclamations.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        
                        Recrutment re = new Recrutment();
                        //dima id fi codename one float 5outhouha
                        float i = Float.parseFloat(obj.get("id").toString());
                        int id = (int)i;
                        String titre = obj.get("titre").toString();
                        float n = Float.parseFloat(obj.get("nbrposte").toString());
                        int nbrposte = (int) n;
                        float salaire = Float.parseFloat(obj.get("salaire").toString());
                        String type = obj.get("type").toString();
                        String description = obj.get("description").toString();
                        
                        re.setId(id);
                        re.setDescription(description);
                        re.setTitre(titre);
                        re.setNbrposte(nbrposte);
                        re.setSalaire(salaire);
                        re.setType(type);
                   
                       
  
                        
                        //insert data into ArrayList result
                        result.add(re);
                       
                    
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return result;
        
        
    }
    
    
    public boolean modifierReclamation(Recrutment recrutment) {
        String url = Statics.BASE_URL +"/employee/recrutement/modifier_offre_mobile?id="+recrutment.getId()+"&description="+recrutment.getDescription()+"&nbreposte="+recrutment.getNbrposte()+"&titre="+recrutment.getTitre()+"&salaire="+recrutment.getSalaire()+"&type="+recrutment.getType();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }
    
        public boolean deleteRecrutment(int id ) {
        String url = Statics.BASE_URL +"/employee/recrutement/supprimer_offre_mobile?id="+id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }
         public void ajoutReclamation(Recrutment r) {
        
        String url =Statics.BASE_URL+"/employee/recrutement/ajouter_offre_mobile?description="+r.getDescription()+"&nbreposte="+r.getNbrposte()+"&titre="+r.getTitre()+"&salaire="+r.getSalaire()+"&type="+r.getType();
        
        req.setUrl(url);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());//Reponse json hethi lyrinaha fi navigateur 9bila
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
         
         public ArrayList<Candidat>affichageCandidat() {
        ArrayList<Candidat> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/employee/recrutement/candidt_mobile";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                JSONParser jsonp ;
                jsonp = new JSONParser();
                
                
                try {
                    
                    Map<String,Object>mapReclamations = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapReclamations.get("root");
                    
                    for(Map<String, Object> obj : listOfMaps) {
                        
                        Candidat re = new Candidat();
                        //dima id fi codename one float 5outhouha
                        float i = Float.parseFloat(obj.get("id").toString());
                        int id = (int)i;
                        String nom = obj.get("nom").toString();
                        String prenom = obj.get("prenom").toString();
                        String email = obj.get("email").toString();
                        String lettre = obj.get("lettremotivation").toString();
                        System.out.println(id);
                        System.out.println(nom);
                        System.out.println(prenom);
                        System.out.println(email);
                        System.out.println(lettre);
                        float t = Float.parseFloat(obj.get("tel").toString());
                        int tel = (int)t;
                        float e = Float.parseFloat(obj.get("etat").toString());
                        int etat = (int)e;
                        System.out.println(tel);
                        System.out.println(etat);
                        
                        
                        re.setId(id);
                        re.setEmail(email);
                        re.setNom(nom);
                        re.setPrenom(prenom);
                        re.setLettre(lettre);
                        re.setTel(tel);
                        re.setEtat(etat);

                        //insert data into ArrayList result
                        result.add(re);
                       
                    
                    }
                    
                }catch(Exception ex) {
                    
                    ex.printStackTrace();
                }
            
            }
        });
        
      NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha

        return result;   
    }
        public boolean AccepterCandidat(int id) {
        String url = Statics.BASE_URL +"/employee/recrutement/modifier_candidat_mobile?id="+id;
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }
    public boolean RefuserCandidat(int id) {
        String url = Statics.BASE_URL +"/employee/recrutement/refuser_candidat_mobile?id="+id;
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
    return resultOk;
        
    }
    public boolean deleteCandidat(int id ) {
        String url = Statics.BASE_URL +"/employee/recrutement/supprimer_candiat_mobile?id="+id;
        
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }
        
}
