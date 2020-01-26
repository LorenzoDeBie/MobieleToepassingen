package be.ugent.oomt.trafficfeed.view.ui;


import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.ugent.oomt.trafficfeed.R;
import be.ugent.oomt.trafficfeed.databinding.FragmentTrafficItemBinding;
import be.ugent.oomt.trafficfeed.viewmodel.TrafficViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrafficItemFragment extends Fragment {

    private TrafficViewModel viewModel;

    public TrafficItemFragment() {
        // Required empty public constructor
    }

    public static TrafficItemFragment newInstance() {
        return new TrafficItemFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentTrafficItemBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_traffic_item, container, false);
        View rootView = binding.getRoot();

        this.viewModel = ViewModelProviders.of(getActivity()).get(TrafficViewModel.class);
        binding.setViewModel(this.viewModel);
        binding.setLifecycleOwner(this);

        return rootView;
    }

}
