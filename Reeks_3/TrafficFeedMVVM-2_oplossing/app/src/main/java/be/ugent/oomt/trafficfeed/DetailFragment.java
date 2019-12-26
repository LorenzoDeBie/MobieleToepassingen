package be.ugent.oomt.trafficfeed;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.ugent.oomt.trafficfeed.databinding.FragmentDetailBinding;
import be.ugent.oomt.trafficfeed.view.ui.TrafficViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_detail, container, false);
        FragmentDetailBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        TrafficViewModel vm = ViewModelProviders.of(getActivity()).get(TrafficViewModel.class);
        binding.setViewmodel(vm);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

}
