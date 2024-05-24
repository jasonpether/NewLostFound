package lost.found;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.activity.result.ActivityResultLauncher;

public class CreateAdvertPage extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    RadioGroup radioGroup;
    RadioButton lost, found;
    EditText name, phone, desc, date, tvlocation;
    Button save, getCurrentLocation;
    String type;

    LocationManager locationManager;
    LocationListener locationListener;
    double latitude, longtitude;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_advert_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        radioGroup = findViewById(R.id.radioGroup);
        lost = findViewById(R.id.lostTypeRadio);
        found = findViewById(R.id.foundTypeRadio);

        name = findViewById(R.id.ETName);
        phone = findViewById(R.id.ETPhone);
        desc = findViewById(R.id.ETDesc);
        date = findViewById(R.id.editTextDate);
        tvlocation = findViewById(R.id.ETLocation);
        save = findViewById(R.id.SaveButton);
        getCurrentLocation = findViewById(R.id.GetCurrentLocationButton);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Places.initialize(getApplicationContext(), "AIzaSyDszHftUN0fgLH2hIV9DJcLp4TarBHML-M");
        PlacesClient placesClient = Places.createClient(this);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.lostTypeRadio) {
                type = "Lost";
            } else if (checkedId == R.id.foundTypeRadio) {
                type = "Found";
            } else {
                type = "";
            }
        });
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);


        ActivityResultLauncher<Intent> startAutocomplete = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            Place place = Autocomplete.getPlaceFromIntent(data);
                            LatLng latlng = place.getLatLng();
                            latitude = latlng.latitude;
                            longtitude = latlng.longitude;
                            if (latlng != null) {
                                tvlocation.setText(place.getName());
                            }

                        }
                    } else if (result.getResultCode() == AutocompleteActivity.RESULT_ERROR) {
                        Status status = Autocomplete.getStatusFromIntent(result.getData());
                    }
                }
        );

        tvlocation.setOnClickListener(v -> {
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(CreateAdvertPage.this);
            startAutocomplete.launch(intent);
        });

        save.setOnClickListener(view -> {
            SQLDB db = new SQLDB(CreateAdvertPage.this);
            db.addItem(type, name.getText().toString(),
                    phone.getText().toString(),
                    desc.getText().toString(),
                    date.getText().toString(),
                    tvlocation.getText().toString());

            Intent push = new Intent(CreateAdvertPage.this, MapsActivity.class);
            push.putExtra("name", name.getText().toString());
            push.putExtra("latitude", latitude);
            push.putExtra("longtitude", longtitude);
            startActivity(push);
        });

        getCurrentLocation.setOnClickListener(view -> getCurrentLocation());
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CreateAdvertPage.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            Task<Location> locationTask = fusedLocationClient.getLastLocation();
            locationTask.addOnSuccessListener(location -> {
                if (location != null) {
                    latitude = location.getLatitude();
                    longtitude = location.getLongitude();
                    String currentLocation = "Latitude: " + location.getLatitude() + ", Longitude: " + location.getLongitude();
                    tvlocation.setText(currentLocation);
                } else {
                    tvlocation.setText("Location not available");
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }
    }
}
