package be.ugent.oomt.trafficfeed.view.ui;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import be.ugent.oomt.trafficfeed.R;
import be.ugent.oomt.trafficfeed.databinding.FragmentMainBinding;
import be.ugent.oomt.trafficfeed.db.TrafficRepository;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;
import be.ugent.oomt.trafficfeed.view.adapter.TrafficAdapter;
import be.ugent.oomt.trafficfeed.viewmodel.TrafficViewModel;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements TrafficAdapter.RecyclerViewClickListener {


    private TrafficViewModel viewModel;
    private NavController navController;
    private boolean dualPaneMode;

    public MainFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new MainFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentMainBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_main, container, false);

        View rootView = binding.getRoot();
        this.viewModel = ViewModelProviders.of(getActivity()).get(TrafficViewModel.class);
        this.viewModel.setTrafficRepository(
                TrafficRepository.getInstance(getActivity().getApplication()));

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(recyclerView.getContext(), VERTICAL));


        final TrafficAdapter adapter = new TrafficAdapter(this);
        viewModel.notifications.observe(this, new Observer<List<TrafficNotification>>() {
            @Override
            public void onChanged(@Nullable List<TrafficNotification> list) {
                Log.d("", "onChanged()");
                adapter.submitList(list);
            }
        });
        recyclerView.setAdapter(adapter);

        this.navController = NavHostFragment.findNavController(this);
        this.dualPaneMode = rootView.findViewById(R.id.detailFragment) != null;

        return rootView;
    }

    public void onClick(View view) {
        this.onClick(view, this.viewModel.getRandomItem());
    }

    @Override
    public void onClick(View view, TrafficNotification item) {
        Log.i(MainFragment.class.getCanonicalName(), "onClick() item");
        this.viewModel.setSelectedItem(item);
        if (!dualPaneMode)
            this.navController.navigate(R.id.action_mainFragment_to_trafficItemFragment);
    }
}
