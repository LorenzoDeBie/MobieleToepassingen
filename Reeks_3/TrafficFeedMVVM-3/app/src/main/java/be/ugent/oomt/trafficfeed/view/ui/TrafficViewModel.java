package be.ugent.oomt.trafficfeed.view.ui;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import be.ugent.oomt.trafficfeed.R;
import be.ugent.oomt.trafficfeed.db.TrafficRepository;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;

import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class TrafficViewModel extends ViewModel {
    private int currentIndex;
    private LiveData<List<TrafficNotification>> notifications;
    private MutableLiveData<TrafficNotification> currentNotification;

    public TrafficViewModel() {
        Log.i(TrafficViewModel.class.getCanonicalName(), "TrafficViewModel: Constructor");
    }

    public void setRepository(TrafficRepository repository) {
        this.notifications = repository.getAllNotifications();
    }

    public LiveData<TrafficNotification> getCurrentNotification() {
        return currentNotification;
    }

    /*
    * Bij het gebruik van iterators en dan na elkaar previous en next oproepen zal hetzelfde object gegeven worden
    * -> in de app opeenvolgend op de knop next, previous of omgekeerd drukken heeft geen effect
    * (dit is intended behaviour van de iterators, maar wij moeten er dus wel rekening mee houden)
    * aangezien de trafficnotifications hier normaal altijd verschillend zullen zijn kunnen wij het simpel oplossen met equals
    * indien er toch gelijke inzitten moet je in principe bijhouden wat of de laatste ingedrukte button next of previous was
    * of niet met iterators werken is ook een optie natuurlijk en gewoon een index bijhouden
    * */
    public void nextNotification() {
        Log.i(TrafficViewModel.class.getCanonicalName(), "nextNotification: current = " + currentNotification.getValue().getId());
        if(currentIndex < notifications.getValue().size()) {
            currentIndex++;
            this.currentNotification.setValue(notifications.getValue().get(currentIndex));
        }
        Log.i(TrafficViewModel.class.getCanonicalName(), "nextNotification: function executed current = " + currentNotification.getValue().getId());
    }

    public void previousNotification() {
        Log.i(TrafficViewModel.class.getCanonicalName(), "nextNotification: current = " + currentNotification.getValue().getId());
        if(currentIndex > 0) {
            currentIndex--;
            this.currentNotification.setValue(notifications.getValue().get(currentIndex));
        }
        Log.i(TrafficViewModel.class.getCanonicalName(), "previousNotification: function executed current = " + currentNotification.getValue().getId());
    }

    public LiveData<List<TrafficNotification>> getNotifications() {
        return notifications;
    }

    public void setCurrentNotification(TrafficNotification notification) {
        currentNotification.setValue(notification);
    }
}
