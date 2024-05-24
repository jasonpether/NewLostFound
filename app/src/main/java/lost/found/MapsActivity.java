package lost.found;

import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import lost.found.databinding.ActivityMapsBinding;
import android.util.Log;
import android.widget.Toast;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng Melbourne = new LatLng(-37.799669718155926, 144.97042661788683);
        mMap.addMarker(new MarkerOptions().position(Melbourne).title("Melbourne Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Melbourne));

        intent = getIntent();

        if (intent != null) {

            String name = intent.getStringExtra("name");

            double latitude = intent.getDoubleExtra("latitude", 0.0);
            double longtitude = intent.getDoubleExtra("longtitude", 0.0);

            LatLng Ad = new LatLng(latitude, longtitude);
            mMap.addMarker(new MarkerOptions().position(Ad).title(name));
        }
    }
}
