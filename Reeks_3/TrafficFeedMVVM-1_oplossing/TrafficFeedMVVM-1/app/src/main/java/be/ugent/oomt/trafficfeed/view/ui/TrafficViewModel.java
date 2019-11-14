package be.ugent.oomt.trafficfeed.view.ui;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.ListIterator;

import be.ugent.oomt.trafficfeed.db.TrafficRepository;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;

public class TrafficViewModel extends ViewModel {

    private ListIterator<TrafficNotification> listiterator;
    private TrafficRepository repository;
    private List<TrafficNotification> notifications;
    private MutableLiveData<TrafficNotification> selectedItem = new MutableLiveData<>();

    public TrafficViewModel() {}

    public void setTrafficRepository(TrafficRepository repository) {
        this.repository = repository;
        this.notifications = this.repository.getAllTrafficNotifications();
        this.listiterator = this.notifications.listIterator();
        if (selectedItem.getValue() == null && listiterator.hasNext())
            this.selectedItem.setValue(listiterator.next());
    }

    public LiveData<TrafficNotification> getSelectedItem() {
        return selectedItem;
    }

    public void previous() {
        Log.d(TrafficViewModel.class.getCanonicalName(), "previous()");
        if (listiterator.hasPrevious()) {
            this.selectedItem.setValue(this.listiterator.previous());
        }
    }

    public void next() {
        Log.d(TrafficViewModel.class.getCanonicalName(), "next()");
        if (listiterator.hasNext()) {
            this.selectedItem.setValue(this.listiterator.next());
        }
    }
}
