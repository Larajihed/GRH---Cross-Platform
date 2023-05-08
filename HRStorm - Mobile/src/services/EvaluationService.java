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
import entities.Evaluation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.Statics;

/**
 *
 * @author conta
 */
public class EvaluationService {
    
    private static EvaluationService instance = null;
    private ConnectionRequest req;
    public boolean result;

    //util
    List<Evaluation> Evaluations = new ArrayList<Evaluation>();
    
    
   

    //Singleton
    public static EvaluationService getInstance() {
        if (instance == null) {
            instance = new EvaluationService();
        }

        return instance;
    }
    
    //ACTIONS
     private EvaluationService()     {
        req = new ConnectionRequest();
    }
    //Add
    public void ajoutEvaluation(Evaluation c) {
        String url =Statics.BASE_URL+"/hremployee/Evaluation/newEvaluation?commentaire="+ c.getCommentaire() +"&experience="+ c.getExperience()+"&level="+c.getLevel()+"&poste="+c.getPoste();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String ( req.getResponseData());
            System.out.println("data == " + str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
    } 
    
    public List<Evaluation> fetchEvaluations() {
        
        req = new ConnectionRequest();
        
        //1
        String fetchURL = Statics.BASE_URL + "/hremployee/evaluation/get";
        
        //2
        req.setUrl(fetchURL);
        
        //3
        req.setPost(false);
        
        //4
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Evaluations = parseEvaluations(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return Evaluations;
    }
//Parse
    public List<Evaluation> parseEvaluations(String jsonText) {

        //var
        Evaluations = new ArrayList<>();
        
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
                
                Evaluation t = new Evaluation();
                t.setCommentaire((String) item.get("commentaire"));
                t.setExperience((Double) item.get("experience"));
                t.setLevel((String) item.get("level"));
                //t.setId((Integer) item.get("Id"));
                float i = Float.parseFloat(item.get("id").toString());
              //  System.out.println((int) i);
                t.setId((int) i);
                Evaluations.add(t);
                System.out.println(Evaluations);
            }
        
        } catch (IOException ex) {
        }
        
        
        
        //End
        return Evaluations;
    }
 //Delete 
    public boolean deleteEvaluation(int id) {
        String url = Statics.BASE_URL +"/hremployee/Evaluation/delete/"+id;
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
    
    public void modifierEvaluation(Evaluation c) {
        String url = Statics.BASE_URL +"/hremployee/Evaluation/UpdateEvaluationJSON/"+"commentaire="+ c.getCommentaire() +"&experience="+ c.getExperience()+"&level="+c.getLevel()+"&poste="+c.getPoste();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println(req.getResponseCode() == 200) ;  
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);
    }
}
