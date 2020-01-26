package be.ugent.oomt.trafficfeed.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;
import java.util.Random;

import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;
import be.ugent.oomt.trafficfeed.db.TrafficRepository;

public class TrafficViewModel extends ViewModel {

    private TrafficRepository repository;
    public LiveData<List<TrafficNotification>> notifications;
    private MutableLiveData<TrafficNotification> selectedItem = new MutableLiveData<>();
    private int selectedItemIndex = -1;
    private Random randomGenerator = new Random();

    public TrafficViewModel() {}

    public void setTrafficRepository(TrafficRepository repository) {
        this.repository = repository;
        this.notifications = this.repository.getAllTrafficNotifications();
    }

    public LiveData<TrafficNotification> getSelectedItem() {
        return selectedItem;
    }

    public TrafficNotification getRandomItem() {
        int index = randomGenerator.nextInt(this.notifications.getValue().size());
        return notifications.getValue().get(index);
    }

    public void previous() {
        Log.d(TrafficViewModel.class.getCanonicalName(), "previous()");
        if (this.selectedItemIndex - 1 > -1)
            setSelectedItem(this.selectedItemIndex - 1);
    }

    public void next() {
        Log.d(TrafficViewModel.class.getCanonicalName(), "next()");
        if (this.selectedItemIndex + 1 < this.notifications.getValue().size())
            setSelectedItem(this.selectedItemIndex + 1);
    }

    private void setSelectedItem(int index) {
        this.selectedItemIndex = index;
        this.selectedItem.setValue(this.notifications.getValue().get(this.selectedItemIndex));
    }

    public void setSelectedItem(TrafficNotification item) {
        this.selectedItem.setValue(item);
        this.selectedItemIndex = this.notifications.getValue().indexOf(item);
    }
}
