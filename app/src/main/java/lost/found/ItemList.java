package lost.found;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ItemList extends AppCompatActivity {

    RecyclerView recyclerView;
    SQLDB DB;
    ArrayList<String> id, Type, Name, Phone, Desc, Date, Location;
    FloatingActionButton home;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_item_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        home = findViewById(R.id.floatingActionButton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jump = new Intent(ItemList.this, MainActivity.class);
                startActivity(jump);
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        DB = new SQLDB(ItemList.this);
        id = new ArrayList<>();
        Type = new ArrayList<>();
        Name = new ArrayList<>();
        Phone = new ArrayList<>();
        Desc = new ArrayList<>();
        Date = new ArrayList<>();
        Location = new ArrayList<>();

        ShowData();

        customAdapter = new CustomAdapter(ItemList.this, id, Type, Name, Phone, Desc, Date, Location);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ItemList.this));
    }

    void ShowData(){
        Cursor cursor = DB.readData();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "NO DATA", Toast.LENGTH_SHORT).show();
        }
        else {
            while(cursor.moveToNext())
            {
                id.add(cursor.getString(0));
                Type.add(cursor.getString(1));
                Name.add(cursor.getString(2));
                Phone.add(cursor.getString(3));
                Desc.add(cursor.getString(4));
                Date.add(cursor.getString(5));
                Location.add(cursor.getString(6));
            }
        }
    }
}