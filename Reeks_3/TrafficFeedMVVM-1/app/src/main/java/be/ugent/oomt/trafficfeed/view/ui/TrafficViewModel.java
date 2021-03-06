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
        if(iterator.hasNext()) {
            TrafficNotification temp = iterator.next();
            if(temp.equals(currentNotification.getValue()) && iterator.hasNext()) temp = iterator.next();
            currentNotification.setValue(temp);
        }
        Log.i(TrafficViewModel.class.getCanonicalName(), "nextNotification: function executed current = " + currentNotification.getValue().getId());
    }

    public void previousNotification() {
        Log.i(TrafficViewModel.class.getCanonicalName(), "nextNotification: current = " + currentNotification.getValue().getId());
        if(iterator.hasPrevious()) {
            TrafficNotification temp = iterator.previous();
            if(temp.equals(currentNotification.getValue()) && iterator.hasPrevious()) temp = iterator.previous();
            currentNotification.setValue(temp);
        }
        Log.i(TrafficViewModel.class.getCanonicalName(), "previousNotification: function executed current = " + currentNotification.getValue().getId());
    }
}
