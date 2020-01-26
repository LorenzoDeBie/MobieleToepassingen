package be.ugent.oomt.trafficfeed.view.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import be.ugent.oomt.trafficfeed.R;
import be.ugent.oomt.trafficfeed.db.TrafficRepository;
import be.ugent.oomt.trafficfeed.db.model.TrafficNotification;
import be.ugent.oomt.trafficfeed.viewmodel.TrafficViewModel;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private TrafficViewModel viewModel;
    private GoogleMap mMap;

    public MapFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        viewModel = ViewModelProviders.of(getActivity()).get(TrafficViewModel.class);
        viewModel.setTrafficRepository(TrafficRepository.getInstance(getActivity().getApplication()));
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(mapFragment != null) mapFragment.getMapAsync(this);
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        updateMap(viewModel.notifications.getValue());
        viewModel.notifications.observe(this, new Observer<List<TrafficNotification>>() {
            @Override
            public void onChanged(List<TrafficNotification> notifications) {
                updateMap(notifications);
            }
        });
        googleMap.setOnMarkerClickListener(this);
    }

    private void updateMap(List<TrafficNotification> trafficNotifications) {
        if (mMap == null || trafficNotifications == null)
            return;

        mMap.clear();
        if (!trafficNotifications.isEmpty()) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (TrafficNotification item : trafficNotifications) {
                if (item.hasValidPosition()) {
                    Marker marker = mMap.addMarker(
                            new MarkerOptions()
                                    .position(new LatLng(item.getLatitude(), item.getLongitude()))
                                    .title(item.toString())
                    );
                    marker.setTag(item);
                    builder.include(marker.getPosition());
                }
            }
            LatLngBounds bounds = builder.build();
            final CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 100);
            // try catch to fix a bug: updating camera position if the map is not yet fully loaded.
            try {
                mMap.animateCamera(cameraUpdate);
            } catch (IllegalStateException ise) {
                mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        mMap.animateCamera(cameraUpdate);
                    }
                });
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        TrafficNotification notification = (TrafficNotification) marker.getTag();
        if(notification == null) return false;
        viewModel.setSelectedItem(notification);
        NavHostFragment.findNavController(this).navigate(R.id.action_mapFragment_to_trafficItemFragment);
        return true;
    }
}
