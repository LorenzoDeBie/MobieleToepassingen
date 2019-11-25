package be.ugent.oomt.trafficfeed.view.ui;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import be.ugent.oomt.trafficfeed.db.TrafficRepository;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;

import java.util.List;
import java.util.ListIterator;

public class TrafficViewModel extends ViewModel {
    private MutableLiveData<TrafficNotification> currentNotification = new MutableLiveData<>();
    private TrafficRepository repository;
    private List<TrafficNotification> notifications;
    private ListIterator<TrafficNotification> iterator;

    public TrafficViewModel() {
        Log.i(TrafficViewModel.class.getCanonicalName(), "TrafficViewModel: Constructor");
    }

    public void setRepository(TrafficRepository repository) {
        this.repository = repository;
        notifications = repository.getAllNotifications();
        iterator = notifications.listIterator();
        if(currentNotification.getValue() == null && iterator.hasNext())
            currentNotification.setValue(iterator.next());
    }

    public LiveData<TrafficNotification> getCurrentNotification() {
        currentNotification.setValue(TrafficNotification.getDummy());
        return currentNotification;
    }

    public void nextNotification() {
        if(iterator.hasNext()) {
            currentNotification.setValue(iterator.next());
        }
        Log.i(TrafficViewModel.class.getCanonicalName(), "nextNotification: function executed");
    }

    public void previousNotification() {
        if(iterator.hasPrevious()) {
            currentNotification.setValue(iterator.previous());
        }
        Log.i(TrafficViewModel.class.getCanonicalName(), "previousNotification: function executed");
    }
}
