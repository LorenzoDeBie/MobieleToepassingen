package be.ugent.oomt.trafficfeed.view.ui;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Random;

import be.ugent.oomt.trafficfeed.db.TrafficRepository;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;

public class TrafficViewModel extends ViewModel {

    private TrafficRepository repository;
    private List<TrafficNotification> notifications;
    private int selectedIndex;
    private MutableLiveData<TrafficNotification> selectedItem = new MutableLiveData<>();
    private final Random rnd = new Random();

    public TrafficViewModel() {
        Log.i("vm", "construction");
    }

    public void setTrafficRepository(TrafficRepository repository) {
        Log.i("vm", "set repo");

        this.repository = repository;
        this.notifications = this.repository.getAllTrafficNotifications();

        Log.i("vm", this.notifications.toString());

        if (selectedItem.getValue() == null && this.notifications.size() != 0)
            selectedIndex = rnd.nextInt(notifications.size());
            this.selectedItem.setValue(notifications.get(selectedIndex));
    }

    public LiveData<TrafficNotification> getSelectedItem() {
        return selectedItem;
    }

    public void previous() {
        Log.d(TrafficViewModel.class.getCanonicalName(), "previous()");
        if (selectedIndex > 0) {
            selectedIndex--;
            this.selectedItem.setValue(notifications.get(selectedIndex));
        }
    }

    public void next() {
        Log.d(TrafficViewModel.class.getCanonicalName(), "next()");
        if (selectedIndex < notifications.size()) {
            selectedIndex++;
            this.selectedItem.setValue(notifications.get(selectedIndex));
        }
    }

    public void selectRandom(){
        selectedIndex = rnd.nextInt(notifications.size());
        this.selectedItem.setValue(notifications.get(selectedIndex));
    }

    public void setSelectedItem(TrafficNotification selectedItem) {
        this.selectedItem.setValue(selectedItem);
    }
}
