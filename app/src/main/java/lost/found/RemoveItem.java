package lost.found;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RemoveItem extends AppCompatActivity {
    TextView name, location, phone, date, desc;
    Button Delete;
    String strName, strPhone, strDesc, strDate, strLocation, strID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_remove_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        name = findViewById(R.id.RETName);
        location = findViewById(R.id.RETLocation);
        phone = findViewById(R.id.RETPhone);
        date = findViewById(R.id.ReditTextDate);
        desc = findViewById(R.id.RETDesc);

        Delete = findViewById(R.id.RSaveButton);
        getIntentData();

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLDB db = new SQLDB(RemoveItem.this);
                db.RemoveTask(strID);
                Intent jump = new Intent(RemoveItem.this, ItemList.class);
                startActivity(jump);
            }
        });
    }

    void getIntentData(){
        if (getIntent().hasExtra("id") &&
            getIntent().hasExtra("name") &&
            getIntent().hasExtra("phone") &&
            getIntent().hasExtra("desc") &&
            getIntent().hasExtra("date") &&
            getIntent().hasExtra("location"))
        {
            strID = getIntent().getStringExtra("id");
            strName = getIntent().getStringExtra("name");
            strPhone = getIntent().getStringExtra("phone");
            strDesc = getIntent().getStringExtra("desc");
            strDate = getIntent().getStringExtra("date");
            strLocation = getIntent().getStringExtra("location");

            name.setText(strName);
            location.setText(strLocation);
            phone.setText(strPhone);
            date.setText(strDate);
            desc.setText(strDesc);
        }
    }
}