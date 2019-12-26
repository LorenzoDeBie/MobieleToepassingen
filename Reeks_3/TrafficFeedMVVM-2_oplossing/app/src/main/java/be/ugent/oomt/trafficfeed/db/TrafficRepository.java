package be.ugent.oomt.trafficfeed.db;

import android.app.Application;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;
import be.ugent.oomt.trafficfeed.util.JsonUtils;

public class TrafficRepository {
    private TrafficNotificationDao trafficNotificationDao;
    private List<TrafficNotification> allTrafficNotifications;
    private static volatile TrafficRepository INSTANCE;
    private static final String FILE = "verkeersmeldingen.json";

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
        TrafficRoomDatabase db = TrafficRoomDatabase.getDatabase(application);
        trafficNotificationDao = db.getNotificationsDao();

        trafficNotificationDao.deleteAll();
        try {
            JSONObject json = JsonUtils.loadJSONFromAsset(application, FILE);
            List<TrafficNotification> notifications = JsonUtils.parseJSON(json);
            trafficNotificationDao.insertAll(notifications.toArray(new TrafficNotification[0]));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        allTrafficNotifications = trafficNotificationDao.getAllNotifications();
    }

    public List<TrafficNotification> getAllTrafficNotifications() {
        return allTrafficNotifications;
    }
}
