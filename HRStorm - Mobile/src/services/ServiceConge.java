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
import entities.Conge;
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
public class ServiceConge {
    public static Service instance = null;
    public static boolean resultOk = true; 
    private ConnectionRequest req;
      public static Service getInstance() {
        if(instance == null )
            instance = new Service();
        return instance ;
    }
      
    public ServiceConge() {
        req = new ConnectionRequest();
    }
    public ArrayList<Conge>affichageConge() {
        ArrayList<Conge> result = new ArrayList<>();
        String url = Statics.BASE_URL+"/affiche_conge_mobile";
        req.setUrl(url);
        req.addResponseListener((NetworkEvent evt) -> {
            JSONParser jsonp ;
            jsonp = new JSONParser();
            try {
                Map<String,Object>mapConges = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                List<Map<String,Object>> listOfMaps =  (List<Map<String,Object>>) mapConges.get("root");
                for(Map<String, Object> obj : listOfMaps) {
                    Conge re = new Conge();
                    float i = Float.parseFloat(obj.get("id").toString());
                    int id = (int)i;
                    String categorie = obj.get("categorie").toString();
                    //String Fin_m = obj.get("Fin_m").toString();
                    //String Debut_m = obj.get("Debut_m").toString();
                    String description = obj.get("description").toString();
                    re.setId(id);
                    re.setDescription(description);
                    re.setCategorie(categorie);
                    //re.setDebut_m(Debut_m);
                    //re.setFin_m(Fin_m);
                    //insert data into ArrayList result
                    result.add(re);
                }
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        });
      NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }
    
    
    public boolean modifierConge(Conge Conge) {
            float idf = Conge.getId();
            int id = (int) idf;

        String url = Statics.BASE_URL +"/updateconges_mobile/"+id+"&description="+Conge.getDescription()+"&categorie="+Conge.getCategorie()+"&fin_m="+Conge.getFin_m()+"&debut_m="+Conge.getDebut_m();
        req.setUrl(url);
        System.out.println(url);
        req.setPost(true);

        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                  req.setUrl(url);
                req.setPost(true);
                resultOk = req.getResponseCode() == 200 ;  
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOk;
        
    }
    
        public boolean deleteConge(int id ) {
            
        String url = Statics.BASE_URL +"/deleteconges_mobile/"+id;
        
          req.setUrl(url);
                req.setPost(true);
                
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
                            NetworkManager.getInstance().addToQueueAndWait(req);

            }
        });
            
        req.setUrl(url);
                req.setPost(true);
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }
         public void ajoutConge(Conge r) {
        
        String url;
        url = Statics.BASE_URL + "/addconges_mobile/new?description="+r.getDescription()+"&debut_m="+r.getDebut_m()+"&categorie="+r.getCategorie()+"&fin_m="+r.getFin_m();
        
                
        req.setUrl(url);
        req.setPost(true);
        req.addResponseListener((e) -> {
            
            String str = new String(req.getResponseData());
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        
    
         
         
        req.setUrl(url);
                req.setPost(true);


        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ;  
                req.removeResponseListener(this);
            }
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);
        
    }
    


        
}
