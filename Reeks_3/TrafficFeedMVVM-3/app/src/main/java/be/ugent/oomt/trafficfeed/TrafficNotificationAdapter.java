package be.ugent.oomt.trafficfeed;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import be.ugent.oomt.trafficfeed.databinding.TrafficNotificationBinding;
import be.ugent.oomt.trafficfeed.db.TrafficRepository;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;

import java.util.List;

public class TrafficNotificationAdapter extends RecyclerView.Adapter<TrafficNotificationAdapter.TrafficNotificationViewHolder> {


    public interface OnNotificationClickListener {
        void onNotificationClick(TrafficNotification notification);
    }

    public class TrafficNotificationViewHolder extends RecyclerView.ViewHolder {

        private TrafficNotificationBinding binding;
        public TrafficNotificationViewHolder(@NonNull TrafficNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void onBind(TrafficNotification notification) {
            binding.setTrafficNot(notification);
            binding.setListener(listener);
            binding.executePendingBindings();
        }


    }

    private List<TrafficNotification> data;
    private final OnNotificationClickListener listener;

    public TrafficNotificationAdapter(List<TrafficNotification> notifications, OnNotificationClickListener listener) {
        data = notifications;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TrafficNotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TrafficNotificationBinding binding = TrafficNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TrafficNotificationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrafficNotificationViewHolder holder, int position) {
        holder.onBind(data.get(position));
    }


    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setNotifications(List<TrafficNotification> notifications) {
        this.data = notifications;
        notifyDataSetChanged();
    }

}
