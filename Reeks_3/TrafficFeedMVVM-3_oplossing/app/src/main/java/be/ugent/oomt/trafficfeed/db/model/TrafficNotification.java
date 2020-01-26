package be.ugent.oomt.trafficfeed.db.model;

import androidx.annotation.InspectableProperty;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "traffic_notifications")
public class TrafficNotification {

    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String type;
    private String source;
    private String transport;
    private String message;
    private Double latitude;
    private Double longitude;
    private String date;

    @Ignore
    public TrafficNotification(String name, String type, String source, String transport,
                               Double latitude, Double longitude, String date, String message) {
        this(UUID.randomUUID().toString(), name, type, source, transport, latitude, longitude, date, message);
    }

    public TrafficNotification(String id, String name, String type, String source, String transport,
                               Double latitude, Double longitude, String date, String message) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.source = source;
        this.transport = transport;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.message = message;
    }

    public static TrafficNotification getDummy() {
        return new TrafficNotification("name", "type", "source",
                "transport", 0.0, 0.0, "date", "message");
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public String getTransport() {
        return transport;
    }

    public String getMessage() {
        return message;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return getSource() + " - " + getType();
    }



    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
