package be.ugent.oomt.trafficfeed;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import be.ugent.oomt.trafficfeed.databinding.FragmentMainBinding;
import be.ugent.oomt.trafficfeed.db.TrafficRepository;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;
import be.ugent.oomt.trafficfeed.view.ui.TrafficViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements TrafficListAdapter.OnNotificationClickListener {
    private RecyclerView recyclerView;



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
        View v = binding.getRoot();

        recyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration( new DividerItemDecoration(recyclerView.getContext(), manager.getOrientation()));

        final TrafficRepository repo = TrafficRepository.getInstance(getActivity().getApplication());

        final TrafficListAdapter adapter = new TrafficListAdapter(repo.getAllTrafficNotifications().getValue(), this);
        recyclerView.setAdapter(adapter);

        TrafficViewModel vm = ViewModelProviders.of(getActivity()).get(TrafficViewModel.class);

        vm.notifications.observe(getActivity(), new Observer<List<TrafficNotification>>() {
            @Override
            public void onChanged(List<TrafficNotification> trafficNotifications) {
                adapter.setNotifications(trafficNotifications);
                Log.i("fragment notifications", trafficNotifications.toString());
            }
        });

        return v;
    }

    @Override
    public void click(TrafficNotification notification) {
        if (getActivity().findViewById(R.id.navhostfragment) != null){
            TrafficViewModel vm = ViewModelProviders.of(getActivity()).get(TrafficViewModel.class);
            vm.setSelectedItem(notification);
            Navigation.findNavController(getView()).navigate(R.id.action_mainFragment_to_detailFragment);
        }
        else{
            Toast t = Toast.makeText(getContext(), "In landscape", Toast.LENGTH_SHORT);
            t.show();
        }
    }
}
