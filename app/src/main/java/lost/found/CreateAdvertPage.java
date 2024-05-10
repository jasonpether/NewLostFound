package lost.found;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateAdvertPage extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton lost, found;
    EditText name, phone, desc, date, location;
    Button save;
    String type;


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
        location = findViewById(R.id.ETLocation);
        save = findViewById(R.id.SaveButton);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.lostTypeRadio) {
                    type = "Lost";
                } else if (checkedId == R.id.foundTypeRadio) {
                    type = "Found";
                } else {
                    type = "";
                }
            }
        });




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLDB db = new SQLDB(CreateAdvertPage.this);
                db.addItem(type, name.getText().toString(),
                           phone.getText().toString(),
                           desc.getText().toString(),
                           date.getText().toString(),
                           location.getText().toString());
                finish();
            }
        });


    }
}