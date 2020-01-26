package be.ugent.oomt.trafficfeed;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import be.ugent.oomt.trafficfeed.databinding.ListitemBinding;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;

public class TrafficListAdapter extends RecyclerView.Adapter<TrafficListAdapter.TrafficViewHolder> {

    private final OnNotificationClickListener listener;
    private  List<TrafficNotification> notifications;

    public TrafficListAdapter(List<TrafficNotification> notifications, OnNotificationClickListener listener) {
        this.notifications = notifications;
        this.listener = listener;
    }

    public void setNotifications(List<TrafficNotification> notifications){
        this.notifications = notifications;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrafficViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListitemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.listitem, parent, false);
        return new TrafficViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrafficViewHolder holder, int position) {
        TrafficNotification notification = notifications.get(position);
        holder.bind(notification);
    }

    @Override
    public int getItemCount() {
        if (notifications != null) {
            return notifications.size();
        }
        return 0;
    }

    public class TrafficViewHolder extends RecyclerView.ViewHolder{
        private ListitemBinding binding;

        public TrafficViewHolder(@NonNull ListitemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TrafficNotification notification){
            this.binding.setNotification(notification);
            this.binding.setListener(listener);
        }
    }

    public interface OnNotificationClickListener{
        void click(TrafficNotification notification);
    }
}
