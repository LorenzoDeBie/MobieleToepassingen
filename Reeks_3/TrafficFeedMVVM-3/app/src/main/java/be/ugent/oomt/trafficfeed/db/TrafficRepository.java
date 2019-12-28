package be.ugent.oomt.trafficfeed.db;


import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import be.ugent.oomt.trafficfeed.db.TrafficDatabase;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;
import be.ugent.oomt.trafficfeed.util.JsonUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Timer;

public class TrafficRepository {
    private static TrafficRepository instance;
    private TrafficDAO dao;
    private List<TrafficNotification> allNotifications;

    private static final String TAG = TrafficRepository.class.getCanonicalName();
    private final RequestQueue queue;
    public static final String URL = "http://users.ugent.be/~ejodconi/verkeersmeldingen.json";
    private Date lastRefresh;

    private TrafficRepository(final Context ctx) {
        TrafficDatabase db = TrafficDatabase.getDatabase(ctx);
        dao = db.trafficDAO();
        updateNotifications();
        queue = Volley.newRequestQueue(ctx);
    }

    //singleton
    public static TrafficRepository getInstance(Context ctx) {
        if(instance == null) {
            instance = new TrafficRepository(ctx);
        }
        return instance;
    }

    public LiveData<List<TrafficNotification>> getAllNotifications() {
        long diff = new Date().getTime() - lastRefresh.getTime();
        // only update if longer than 1 minute passed
        if(diff / (1000*60) > 0)
            updateNotifications();
        return dao.getAll();
    }

    private void updateNotifications() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    allNotifications = JsonUtils.parseJSON(response);
                    dao.deleteAll();
                    dao.insertAll(allNotifications);
                    lastRefresh = new Date();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }

}
