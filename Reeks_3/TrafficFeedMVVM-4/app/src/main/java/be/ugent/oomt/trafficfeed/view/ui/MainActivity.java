package be.ugent.oomt.trafficfeed.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashSet;
import java.util.Set;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import be.ugent.oomt.trafficfeed.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        appBarConfiguration = new AppBarConfiguration.Builder(this.navController.getGraph())
                .setDrawerLayout((DrawerLayout) findViewById(R.id.drawer_layout))
                .build();
        NavigationUI.setupWithNavController((NavigationView) findViewById(R.id.nav_view), navController);
        NavigationUI.setupActionBarWithNavController(this, this.navController, this.appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (NavigationUI.navigateUp(this.navController, this.appBarConfiguration))
            return true;
        return super.onSupportNavigateUp();
    }
}
