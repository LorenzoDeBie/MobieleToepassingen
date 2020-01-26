package be.ugent.oomt.trafficfeed.db;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;
import be.ugent.oomt.trafficfeed.util.JsonUtils;

public class TrafficRepository {

    private TrafficNotificationDao trafficNotificationDao;

    private static int REFRESH_TIMEOUT_IN_MINUTES = 3;
    private static volatile TrafficRepository INSTANCE;
    private static final String FILE = "verkeersmeldingen.json";

    private static final String FETCH_URL = "https://users.ugent.be/~ejodconi/verkeersmeldingen.json";
    private final RequestQueue requestQueue;
    private Date lastRefresh = new Date();

    public static TrafficRepository getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (TrafficRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TrafficRepository(application);
                }
            }
        }
        return INSTANCE;
    }

    private TrafficRepository(Application application) {
        requestQueue = Volley.newRequestQueue(application);

        TrafficRoomDatabase db = TrafficRoomDatabase.getDatabase(application);
        trafficNotificationDao = db.getNotificationsDao();
    }

    private void refresh() {
        // fetch data from url (this method is already executing in a thread)
        if (lastRefresh.after(getMaxRefreshTime(new Date()))) {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, FETCH_URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        // Mimick network latency
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        List<TrafficNotification> list = JsonUtils.parseJSON(response);
                        for (TrafficNotification item : list)
                            try {
                                trafficNotificationDao.insert(item);
                            } catch (SQLiteConstraintException e) {
                                Log.e("", e.getLocalizedMessage());
                            }
                    } catch (JSONException e) {
                        Log.e("", e.getLocalizedMessage());
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("", error.getLocalizedMessage());
                    error.printStackTrace();
                }
            });
            requestQueue.add(request);
            lastRefresh = new Date();
        }
    }

    public LiveData<List<TrafficNotification>> getAllTrafficNotifications() {
        refresh();
        return trafficNotificationDao.getAllNotifications();
    }

    private Date getMaxRefreshTime(Date currentDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MINUTE, -REFRESH_TIMEOUT_IN_MINUTES);
        return cal.getTime();
    }
}