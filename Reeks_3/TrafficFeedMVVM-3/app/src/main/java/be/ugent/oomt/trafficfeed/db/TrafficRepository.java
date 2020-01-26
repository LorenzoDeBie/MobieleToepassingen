package be.ugent.oomt.trafficfeed.db;


import android.app.Notification;
import android.content.Context;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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

public class TrafficRepository {
    private static TrafficRepository instance;
    private TrafficDAO dao;
    private Date lastRequest;
    private RequestQueue queue;
    private MutableLiveData<List<TrafficNotification>> notifications = new MutableLiveData<>();

    private static final String TAG = TrafficRepository.class.getCanonicalName();

    private TrafficRepository(final Context ctx) {
        TrafficDatabase db = TrafficDatabase.getDatabase(ctx);
        dao = db.trafficDAO();
        Log.d(TAG, "before deleteAll");
        dao.deleteAll();
        queue = Volley.newRequestQueue(ctx);
        getAllNotificationsFromWeb();
    }

    //singleton
    public static TrafficRepository getInstance(Context ctx) {
        if(instance == null) {
            instance = new TrafficRepository(ctx);
        }
        return instance;
    }

    public LiveData<List<TrafficNotification>> getAllNotifications() {
        if(new Date().getTime() - lastRequest.getTime() < 6000) {
            getAllNotificationsFromWeb();
        }
        return notifications;
    }

    private void getAllNotificationsFromWeb() {
        String url = "https://users.ugent.be/~ejodconi/verkeersmeldingen.json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: Loaded traffic notification from web");
                try {
                    dao.deleteAll();
                    notifications.setValue(JsonUtils.parseJSON(response));
                    dao.insertAll(notifications.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: Failed to load notifications from web", error);
            }
        });
        queue.add(jsonObjectRequest);
        lastRequest = new Date();
    }

}
