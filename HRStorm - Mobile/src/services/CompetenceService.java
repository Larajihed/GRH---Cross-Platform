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
import entities.Competence;
import java.io.IOException;
import java.util.ArrayList;
import utils.Statics;
import java.util.List;
import java.util.Map;


/**
 *
 * @author conta
 */
public class CompetenceService {
    //var
    private static CompetenceService instance = null;
    private ConnectionRequest req;
    public boolean result;

    //util
    List<Competence> competences = new ArrayList<Competence>();
    
    
   

    //Singleton
    public static CompetenceService getInstance() {
        if (instance == null) {
            instance = new CompetenceService();
        }

        return instance;
    }
    
    //ACTIONS
     private CompetenceService()     {
        req = new ConnectionRequest();
    }
    //Add
    public void ajoutCompetence(Competence c) {
       // String url =Statics.BASE_URL+"/hremployee/competence/newcompetence?Nom="+c.getNom()+"&description="+c.getDescription();
        String url =Statics.BASE_URL+"/hremployee/competence/newcompetence?Nom="+ c.getNom() +"&description="+ c.getDescription();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String ( req.getResponseData());
            System.out.println("data == " + str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
    } 
    
    public List<Competence> fetchTasks() {
        
        req = new ConnectionRequest();
        
        //1
        String fetchURL = Statics.BASE_URL + "/hremployee/competence/get";
        
        //2
        req.setUrl(fetchURL);
        
        //3
        req.setPost(false);
        
        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                competences = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return competences;
    }
//Parse
    public List<Competence> parseTasks(String jsonText) {

        //var
        competences = new ArrayList<>();
        
        //DO
        //1
        JSONParser jp = new JSONParser();
        
        try {
            
            //2
            Map<String, Object> tasksListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        
            //3
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJSON.get("root");
        
            //4
            for (Map<String, Object> item : list) {
                
                Competence t = new Competence();
                t.setNom((String) item.get("Nom"));
                t.setDescription((String) item.get("Description"));
                //t.setId((Integer) item.get("Id"));
                float i = Float.parseFloat(item.get("Id").toString());
               // System.out.println((int) i);
                t.setId((int) i);
                competences.add(t);
            }
        
        } catch (IOException ex) {
        }
        
        
        
        //End
        return competences;
    }
 //Delete 
    public boolean deleteCompetence(int id) {
        String url = Statics.BASE_URL +"/hremployee/competence/delete/"+id;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseCodeListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  true;
    }
    
    public void modifierCompetence(Competence c) {
        String url = Statics.BASE_URL +"/hremployee/competence/UpdateCompetenceJSON/"+c.getId()+ "?Nom="+c.getNom()+"&Description="+c.getDescription();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println(req.getResponseCode() == 200) ;  // Code response Http 200 ok
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);//execution ta3 request sinon yet3ada chy dima nal9awha
        
    }
}

 

