package be.ugent.oomt.trafficfeed.view.ui;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import be.ugent.oomt.trafficfeed.R;
import be.ugent.oomt.trafficfeed.databinding.ActivityMainBinding;
import be.ugent.oomt.trafficfeed.db.TrafficRepository;

public class MainActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        TrafficViewModel viewModel = ViewModelProviders.of(this).get(TrafficViewModel.class);
        viewModel.setRepository(TrafficRepository.getInstance(this.getApplication()));
        binding.setLifecycleOwner(this);
        Log.i(MainActivity.class.getCanonicalName(), "onCreate: created activity");

        try {
            this.navController = Navigation.findNavController(this, R.id.navhostfragment);
            NavigationUI.setupActionBarWithNavController(this, this.navController);
        } catch (IllegalArgumentException e) {
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if(this.navController != null && this.navController.navigateUp())
            return true;
        return super.onSupportNavigateUp();
    }
}
