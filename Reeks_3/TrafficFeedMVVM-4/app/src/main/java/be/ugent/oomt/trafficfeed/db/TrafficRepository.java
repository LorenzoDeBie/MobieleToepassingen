package be.ugent.oomt.trafficfeed.db;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;
import be.ugent.oomt.trafficfeed.util.JsonUtils;

public class TrafficRepository {

    private final LiveData<List<TrafficNotification>> notifficationList;
    private TrafficNotificationDao trafficNotificationDao;

    private static int REFRESH_TIMEOUT_IN_MINUTES = 5;
    private static volatile TrafficRepository INSTANCE;

    private final Application applicationContext;

    private static final String FETCH_URL = "https://users.ugent.be/~ejodconi/verkeersmeldingen.json";
    private final RequestQueue requestQueue;
    private static Date LAST_REFRESH = getMaxRefreshTime(new Date()); // make sure we refresh first time

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
        this.applicationContext = application;
        requestQueue = Volley.newRequestQueue(application);

        TrafficRoomDatabase db = TrafficRoomDatabase.getDatabase(application);
        trafficNotificationDao = db.trafficNotificationDAO();
        this.notifficationList = trafficNotificationDao.getAllNotifications();
    }

    private void refresh() {
        // fetch data from url (this method is already executing in a thread)
        if (LAST_REFRESH.before(getMaxRefreshTime(new Date()))) {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, FETCH_URL, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(final JSONObject response) {
                    AsyncTask<Void, Void, Integer> insertTask = new AsyncTask<Void, Void, Integer>(){

                        @Override
                        protected Integer doInBackground(Void... voids) {
                            int newItems = 0;
                            try {
                                List<TrafficNotification> list = JsonUtils.parseJSON(response);
                                for (TrafficNotification item : list)
                                    try {
                                        trafficNotificationDao.insert(item);
                                        newItems++;
                                    } catch (SQLiteConstraintException e) {
                                        Log.w(TrafficRepository.class.getCanonicalName(), e.getMessage());
                                    }
                            } catch (JSONException e) {
                                Log.e(TrafficRepository.class.getCanonicalName(), e.getMessage());
                                e.printStackTrace();
                            }
                            return newItems;
                        }

                        @Override
                        protected void onPostExecute(Integer result) {
                            Toast.makeText(applicationContext,
                                    "new items: " + result,
                                    Toast.LENGTH_SHORT).show();
                        }
                    };
                    insertTask.execute();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            requestQueue.add(request);
            LAST_REFRESH = new Date();
        }
    }

    public LiveData<List<TrafficNotification>> getAllTrafficNotifications() {
        refresh();
        return this.notifficationList;
    }

    private static Date getMaxRefreshTime(Date currentDate){
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MINUTE, -REFRESH_TIMEOUT_IN_MINUTES);
        return cal.getTime();
    }
}
