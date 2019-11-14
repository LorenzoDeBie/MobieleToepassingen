package be.ugent.oomt.trafficfeed.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import be.ugent.oomt.trafficfeed.R;
import be.ugent.oomt.trafficfeed.databinding.ActivityMainBinding;
import be.ugent.oomt.trafficfeed.db.TrafficRepository;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        TrafficViewModel vm = ViewModelProviders.of(this).get(TrafficViewModel.class);
        vm.setTrafficRepository(TrafficRepository.getInstance(this.getApplication()));
        binding.setViewmodel(vm);
        binding.setLifecycleOwner(this);
    }
}
