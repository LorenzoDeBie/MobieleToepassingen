package be.ugent.oomt.trafficfeed.db;


import android.content.Context;
import android.util.Log;
import be.ugent.oomt.trafficfeed.db.TrafficDatabase;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;
import be.ugent.oomt.trafficfeed.util.JsonUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class TrafficRepository {
    private static TrafficRepository instance;
    private TrafficDAO dao;
    private List<TrafficNotification> allNotifications;

    private static final String TAG = TrafficRepository.class.getCanonicalName();

    private TrafficRepository(final Context ctx) {
        TrafficDatabase db = TrafficDatabase.getDatabase(ctx);
        dao = db.trafficDAO();
        Log.d(TAG, "before deleteAll");
        dao.deleteAll();
        Log.d(TAG, "TrafficRepository: after deleteall");
        try {
            JSONObject json = JsonUtils.loadJSONFromAsset(ctx, "verkeersmeldingen.json");
            List<TrafficNotification> notifications = JsonUtils.parseJSON(json);
            Log.d(TAG, "TrafficRepository: before insertAll");
            dao.insertAll(notifications);
            Log.d(TAG, "TrafficRepository: after insertAll");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        allNotifications = dao.getAll();
    }

    //singleton
    public static TrafficRepository getInstance(Context ctx) {
        if(instance == null) {
            instance = new TrafficRepository(ctx);
        }
        return instance;
    }

    public List<TrafficNotification> getAllNotifications() {
        return allNotifications;
    }

}
