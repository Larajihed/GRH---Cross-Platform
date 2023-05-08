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
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import entities.Planning;
import utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ilyes
 */
public class PlanningService {

    private ConnectionRequest req;
    private boolean resultOK;
    private static PlanningService instance = null;
    private ArrayList<Planning> plans;

    private PlanningService() {
        req = new ConnectionRequest();
    }

    public static PlanningService getInstance() {
        if (instance == null) {
            instance = new PlanningService();
        }
        return instance;
    }

    public ArrayList<Planning> parsePlans(String jsonText) throws ParseException {
            plans = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> planListJson = null;
        try {
            planListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
        } catch (IOException ex) {
           // Logger.getLogger(PlanningService.class.getName()).log(Level.SEVERE, null, ex);
        }

            List<Map<String, Object>> list = (List<Map<String, Object>>) planListJson.get("root");
            for (Map<String, Object> obj : list) {
                Planning p = new Planning();
                float id = Float.parseFloat(obj.get("id").toString());
                String name = obj.get("nom").toString();
                String description = obj.get("description").toString();
                Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("dateDebut").toString());
                Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(obj.get("dateFin").toString());
                p.setId((int) id);
                p.setName(name);
                p.setDescription(description);
                p.setStartDate(startDate);
                p.setEndDate(endDate);

                plans.add(p);
            }

        

        return plans;
    }

    public ArrayList<Planning> getAllPlans() {
        ArrayList<Planning> listPlan = new ArrayList<>();
        String url = Statics.BASE_URL + "/planning/JSON/getAll";
        req.setUrl(url);
        req.setPost(false);
        System.out.println(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    plans = parsePlans(new String(req.getResponseData()));
                } catch (Exception e) {
                   // ex.printStackTrace();
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return plans;
    }

    public boolean addPlan(Planning p) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String url = Statics.BASE_URL + "/planning/addplanning?nom=" + p.getName() + "&description=" + p.getDescription() + "&start_date=" + format.format(p.getStartDate()) + "&end_date=" + format.format(p.getEndDate());
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

   public boolean editPlan(Planning p) {
    String url = Statics.BASE_URL + "/planning/editplanning?id=" + p.getId() +
            "&nom=" + p.getName() +
            "&description=" + p.getDescription() +
            "&startDate=" + p.getStartDate() +
            "&endDate=" + p.getEndDate();
    req.setUrl(url);
    NetworkManager.getInstance().addToQueueAndWait(req);
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            resultOK = req.getResponseCode() == 200;
            req.removeResponseListener(this);
        }
    });
    return resultOK;
}
   
   public boolean deletePlan(int id) {
    String url = Statics.BASE_URL + "/planning/delplanning/" + id;
    req.setUrl(url);
    req.setPost(false);
    NetworkManager.getInstance().addToQueueAndWait(req);
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            resultOK = req.getResponseCode() == 200;
            req.removeResponseListener(this);
        }
    });
    return resultOK;
}

}
