package be.ugent.oomt.trafficfeed;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import be.ugent.oomt.trafficfeed.databinding.FragmentMainBinding;
import be.ugent.oomt.trafficfeed.db.TrafficRepository;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;
import be.ugent.oomt.trafficfeed.view.ui.TrafficViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements TrafficListAdapter.OnNotificationClickListener {
    private static final String TAG = "MainFragment";

    private RecyclerView notificationsRecyclerView;
    //private final TrafficViewModel vm = ViewModelProviders.of(getActivity()).get(TrafficViewModel.class);

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_main, container, false);

        FragmentMainBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        binding.setFragment(this);
        notificationsRecyclerView = binding.notificationsRecyclerView;
        notificationsRecyclerView.setHasFixedSize(true);

        final TrafficRepository repo = TrafficRepository.getInstance(getActivity().getApplication());
        final TrafficListAdapter trafficListAdapter = new TrafficListAdapter(repo.getAllNotifications().getValue(), this);
        notificationsRecyclerView.setAdapter(trafficListAdapter);
        notificationsRecyclerView.addItemDecoration(new DividerItemDecoration(notificationsRecyclerView.getContext(),
                ((LinearLayoutManager)notificationsRecyclerView.getLayoutManager()).getOrientation()));

        TrafficViewModel vm = ViewModelProviders.of(getActivity()).get(TrafficViewModel.class);
        vm.getNotifications().observe(getActivity(), new Observer<List<TrafficNotification>>() {
            @Override
            public void onChanged(List<TrafficNotification> trafficNotifications) {
                trafficListAdapter.setNotifications(trafficNotifications);
            }
        });
        return binding.getRoot();
    }

    //click handler
    @Override
    public void onNotificationClick(TrafficNotification n) {
        Log.i(TAG, "onNotificationClick: Notification clicked");
        TrafficViewModel vm = ViewModelProviders.of(getActivity()).get(TrafficViewModel.class);
        vm.setCurrentNotification(n);
        if(getActivity().findViewById(R.id.navhostfragment) != null) {
            Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_detailFragment);
        }
        else {
            Toast.makeText(getContext(), "In landscape", Toast.LENGTH_SHORT).show();
        }
    }

}
