package lost.found;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button createItem, showList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    createItem = findViewById(R.id.CreateAdvertButton);
    showList = findViewById(R.id.ViewAdvertsButton);

    createItem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent jump = new Intent(MainActivity.this, CreateAdvertPage.class);
            startActivity(jump);
        }
    });

    showList.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent jump = new Intent(MainActivity.this, ItemList.class);
            startActivity(jump);
        }
    });

    }
}