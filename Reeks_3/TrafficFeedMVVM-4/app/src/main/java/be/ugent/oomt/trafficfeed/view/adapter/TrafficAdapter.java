package be.ugent.oomt.trafficfeed.view.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.ugent.oomt.trafficfeed.R;
import be.ugent.oomt.trafficfeed.databinding.ListItemBinding;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;

public class TrafficAdapter extends ListAdapter<TrafficNotification, TrafficAdapter.ViewHolder> {

    public interface RecyclerViewClickListener {
        void onClick(View view, TrafficNotification item);
    }

    private RecyclerViewClickListener mListener;

    public TrafficAdapter(RecyclerViewClickListener listener) {
        super(DIFF_CALLBACK);
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.list_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ListItemBinding binding;

        public ViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setHandler(TrafficAdapter.this.mListener);
        }

        public void bindTo(final TrafficNotification item) {
            binding.setNotification(item);
        }
    }

    static final DiffUtil.ItemCallback<TrafficNotification> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<TrafficNotification>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull TrafficNotification oldNotification,
                        @NonNull TrafficNotification newNotification) {
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldNotification.getId() == newNotification.getId();
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull TrafficNotification oldNotification,
                        @NonNull TrafficNotification newNotification) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldNotification.equals(newNotification);
                }
            };

}
