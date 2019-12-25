package be.ugent.oomt.trafficfeed.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;

import java.util.Collection;
import java.util.List;

@Dao
public interface TrafficDAO {
    @Insert
    void insertAll(Collection<TrafficNotification> trafficNotifications);

    @Query("DELETE FROM trafficnotifications")
    void deleteAll();

    //TODO: maybe return livedata?
    @Query("SELECT * FROM trafficnotifications")
    List<TrafficNotification> getAll();
}
