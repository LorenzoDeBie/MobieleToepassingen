package be.ugent.oomt.trafficfeed;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import be.ugent.oomt.trafficfeed.databinding.TrafficNotificationBinding;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;

import java.util.List;

public class TrafficListAdapter extends RecyclerView.Adapter<TrafficListAdapter.TrafficViewHolder> {

    private List<TrafficNotification> dataSet;
    private final OnNotificationClickListener notificationClickListener;

    public TrafficListAdapter(List<TrafficNotification> dataSet, OnNotificationClickListener notificationClickListener) {
        this.dataSet = dataSet;
        this.notificationClickListener = notificationClickListener;
    }

    //holds view
    public class TrafficViewHolder extends RecyclerView.ViewHolder {
        public TrafficNotificationBinding notificationBinding;
        public TrafficViewHolder(@NonNull TrafficNotificationBinding binding) {
            super(binding.getRoot());
            notificationBinding = binding;
        }

        public void onBind(TrafficNotification notification) {
            notificationBinding.setNotification(notification);
            notificationBinding.setClicklistener(notificationClickListener);
        }
    }

    //create new View
    @NonNull
    @Override
    public TrafficViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TrafficNotificationBinding trafficNotificationBinding = TrafficNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TrafficViewHolder(trafficNotificationBinding);
    }

    //bind data to view
    @Override
    public void onBindViewHolder(@NonNull TrafficViewHolder holder, int position) {
        holder.onBind(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setNotifications(List<TrafficNotification> trafficNotifications) {
        this.dataSet = trafficNotifications;
        notifyDataSetChanged();
    }

    public interface OnNotificationClickListener {
        void onNotificationClick(TrafficNotification n);
    }
}
