package be.ugent.oomt.trafficfeed;


import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProviders;
import be.ugent.oomt.trafficfeed.databinding.FragmentDetailBinding;
import be.ugent.oomt.trafficfeed.databinding.FragmentMainBinding;
import be.ugent.oomt.trafficfeed.view.ui.TrafficViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstancestate) {
        super.onCreate(savedInstancestate);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentDetailBinding binding = FragmentDetailBinding.inflate(inflater, container, false);
        TrafficViewModel vm = ViewModelProviders.of(getActivity()).get(TrafficViewModel.class);
        binding.setTrafficViewModel(vm);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

}
