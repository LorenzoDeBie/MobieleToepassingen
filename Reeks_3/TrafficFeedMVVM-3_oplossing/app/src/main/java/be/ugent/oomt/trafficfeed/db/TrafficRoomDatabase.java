package be.ugent.oomt.trafficfeed.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;

@Database(entities = {TrafficNotification.class}, version = 1)
public abstract class TrafficRoomDatabase extends RoomDatabase {
    public abstract TrafficNotificationDao getNotificationsDao();

    private static final String DB_NAME = "trafficfeed.db";
    private static volatile TrafficRoomDatabase INSTANCE;

    public static TrafficRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TrafficRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TrafficRoomDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
