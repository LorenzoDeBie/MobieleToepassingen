package be.ugent.oomt.trafficfeed.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;

@Dao
public interface TrafficNotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TrafficNotification notification);

    @Insert
    void insertAll(TrafficNotification... notifications);

    @Query("DELETE FROM traffic_notifications")
    void deleteAll();

    @Query("SELECT * from traffic_notifications")
    LiveData<List<TrafficNotification>> getAllNotifications();
}
