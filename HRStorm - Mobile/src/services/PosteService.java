package services;

import entities.Competence;
import entities.Poste;
import utils.Statics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;

public class PosteService {
    private static PosteService instance = null;
    private ConnectionRequest req;
    private List<Poste> postes = new ArrayList<Poste>();

    private PosteService() {
        req = new ConnectionRequest();
    }

    public static PosteService getInstance() {
        if (instance == null) {
            instance = new PosteService();
        }

        return instance;
    }

    public void addPoste(Poste p) {
        String url = Statics.BASE_URL + "/hremployee/poste/newposte?Nom=" + p.getNom() + "&Missions=" + p.getMissions()
                + "&Description=" + p.getDescription() + "&SALAIRE_MAX=" + p.getSalaireMax() + "&SALAIRE_MIN="
                + p.getSalaireMin();
        for (Competence c : p.getCompetences()) {
            url += "&Competences[]=" + c.getId();
        }
        req.setUrl(url);
        req.addResponseListener((e) -> {
            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    public List<Poste> fetchPostes() {
        req = new ConnectionRequest();

        String fetchURL = Statics.BASE_URL + "/hremployee/poste/get";

        req.setUrl(fetchURL);

        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                postes = parsePostes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
        return postes;
    }

    public List<Poste> parsePostes(String jsonText) {
        postes = new ArrayList<>();

        JSONParser jp = new JSONParser();

        try {
            Map<String, Object> postesListJSON = jp.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) postesListJSON.get("root");

            for (Map<String, Object> item : list) {
                Poste p = new Poste();
               // p.setId((Integer) item.get("Id"));
                float i = Float.parseFloat(item.get("Id").toString());
              //  System.out.println((int) i);
                p.setId((int) i);
                p.setNom((String) item.get("Nom"));
                p.setMissions((String) item.get("Missions"));
                p.setDescription((String) item.get("Description"));
                p.setSalaireMax((Float) item.get("SALAIRE_MAX"));
                p.setSalaireMin((Float) item.get("SALAIRE_MIN"));
                List<Map<String, Object>> compList = (List<Map<String, Object>>) item.get("Competences");
                for (Map<String, Object> compItem : compList) {
                    Competence c = new Competence();
                   // c.setId((Integer) compItem.get("Id"));
                    float idC = Float.parseFloat(item.get("Id").toString());
                c.setId((int) idC);
                    c.setNom((String) compItem.get("Nom"));
                    //c.setDescription((String) compItem.get("Description"));
                    //p.addCompetence(c);
                }

                postes.add(p);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    return postes;
}

public boolean deletePoste(int id) {
    String url = Statics.BASE_URL + "/hremployee/poste/delete/" + id;
    req.setUrl(url);
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            req.removeResponseCodeListener(this);
        }
    });
    NetworkManager.getInstance().addToQueueAndWait(req);
    return true;
}

public void updatePoste(Poste p) {
    String url = Statics.BASE_URL + "/hremployee/poste/UpdatePosteJSON/" + p.getId() + "?Nom=" + p.getNom()
            + "&Missions=" + p.getMissions() + "&Description=" + p.getDescription() + "&SALAIRE_MAX="
            + p.getSalaireMax() + "&SALAIRE_MIN=" + p.getSalaireMin();
    for (Competence c : p.getCompetences()) {
        url += "&Competences[]=" + c.getId();
    }
    req.setUrl(url);

    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            System.out.println(req.getResponseCode() == 200);
            req.removeResponseListener(this);
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);
}
}