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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import be.ugent.oomt.trafficfeed.databinding.FragmentMainBinding;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;
import be.ugent.oomt.trafficfeed.view.ui.TrafficViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements TrafficNotificationAdapter.OnNotificationClickListener {

    private static final String TAG = "MainFragment";

    private RecyclerView recyclerView;
    private TrafficNotificationAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

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

        recyclerView = binding.mainRecyclerView;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        TrafficViewModel vm = ViewModelProviders.of(getActivity()).get(TrafficViewModel.class);
        mAdapter = new TrafficNotificationAdapter(vm.getNotifications().getValue(), this);
        vm.getNotifications().observe(this, new Observer<List<TrafficNotification>>() {
            @Override
            public void onChanged(List<TrafficNotification> notifications) {
                mAdapter.setNotifications(notifications);
            }
        });
        recyclerView.setAdapter(mAdapter);
        return binding.getRoot();
    }

    public void click(View v) {
        Log.i(TAG, "Click: random button");
        TrafficViewModel vm = ViewModelProviders.of(getActivity()).get(TrafficViewModel.class);
        vm.selectRandomNotification();
        if(getActivity().findViewById(R.id.navhostfragment) != null) {
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_detailFragment);
        }
        else {
            Toast.makeText(getContext(), "In landscape", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNotificationClick(TrafficNotification notification) {
        Log.i(TAG, "onNotificationClick: selected notification");
        TrafficViewModel vm = ViewModelProviders.of(getActivity()).get(TrafficViewModel.class);
        vm.selectCurrentNotification(notification);
        if(getActivity().findViewById(R.id.navhostfragment) != null) {
            Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_detailFragment);
        }
    }
}
