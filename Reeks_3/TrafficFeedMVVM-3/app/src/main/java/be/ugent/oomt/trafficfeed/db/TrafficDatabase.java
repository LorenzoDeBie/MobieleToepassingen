package be.ugent.oomt.trafficfeed.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;

@Database(entities = {TrafficNotification.class}, version = 1)
public abstract class TrafficDatabase extends RoomDatabase {

    private static TrafficDatabase INSTANCE;


    public static TrafficDatabase getDatabase( final Context ctx) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(ctx.getApplicationContext(), TrafficDatabase.class, "traffic_database").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public abstract TrafficDAO trafficDAO();

}
