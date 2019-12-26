package be.ugent.oomt.trafficfeed;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import be.ugent.oomt.trafficfeed.databinding.FragmentMainBinding;
import be.ugent.oomt.trafficfeed.view.ui.TrafficViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


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
        return binding.getRoot();
    }

    public void click(View v){
        Log.i("click", "abc");
        if (getActivity().findViewById(R.id.navhostfragment) != null){
            TrafficViewModel vm = ViewModelProviders.of(getActivity()).get(TrafficViewModel.class);
            vm.selectRandom();
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_detailFragment);
        }
        else{
            Toast t = Toast.makeText(getContext(), "In landscape", Toast.LENGTH_SHORT);
            t.show();
        }

    }

}
