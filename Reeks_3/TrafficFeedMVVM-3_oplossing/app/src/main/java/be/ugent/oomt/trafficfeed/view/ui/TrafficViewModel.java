package be.ugent.oomt.trafficfeed.view.ui;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Random;

import be.ugent.oomt.trafficfeed.db.TrafficRepository;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;

public class TrafficViewModel extends ViewModel {

    private int selectedIndex;
    public LiveData<List<TrafficNotification>> notifications;
    private MutableLiveData<TrafficNotification> selectedItem = new MutableLiveData<>();

    public TrafficViewModel() {
        Log.i("vm", "construction");
    }

    public void setTrafficRepository(TrafficRepository repository) {
        this.notifications = repository.getAllTrafficNotifications();
    }

    public LiveData<TrafficNotification> getSelectedItem() {
        return selectedItem;
    }

    public void previous() {
        Log.d(TrafficViewModel.class.getCanonicalName(), "previous()");
        if (selectedIndex > 0) {
            selectedIndex--;
            this.selectedItem.setValue(this.notifications.getValue().get(selectedIndex));
        }
    }

    public void next() {
        Log.d(TrafficViewModel.class.getCanonicalName(), "next()");
        if (selectedIndex < this.notifications.getValue().size()) {
            selectedIndex++;
            this.selectedItem.setValue(this.notifications.getValue().get(selectedIndex));
        }
    }

    public void setSelectedItem(TrafficNotification selectedItem) {
        this.selectedItem.setValue(selectedItem);
        this.selectedIndex = this.notifications.getValue().indexOf(selectedItem);
    }
}
