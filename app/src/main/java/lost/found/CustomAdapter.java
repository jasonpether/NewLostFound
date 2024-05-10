package lost.found;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList id, type, name, phone, desc,  date, location;


    CustomAdapter(Context context,
                  ArrayList id,
                  ArrayList type,
                  ArrayList name,
                  ArrayList phone,
                  ArrayList desc,
                  ArrayList date,
                  ArrayList location)
    {
        this.context = context;
        this.id = id;
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.desc = desc;
        this.date = date;
        this.location = location;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(context);
        View view = inflate.inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, final int position) {
        holder.TypeText.setText(String.valueOf(type.get(position)));
        holder.NameText.setText(String.valueOf(name.get(position)));
        holder.PhoneText.setText(String.valueOf(phone.get(position)));
        holder.DescText.setText(String.valueOf(desc.get(position)));
        holder.DateText.setText(String.valueOf(date.get(position)));
        holder.LocationText.setText(String.valueOf(location.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jump = new Intent(context, RemoveItem.class);
                jump.putExtra("id", String.valueOf(id.get(position)));
                jump.putExtra("name", String.valueOf(name.get(position)));
                jump.putExtra("phone", String.valueOf(phone.get(position)));
                jump.putExtra("desc", String.valueOf(desc.get(position)));
                jump.putExtra("date", String.valueOf(date.get(position)));
                jump.putExtra("location", String.valueOf(location.get(position)));
                context.startActivity(jump);
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView TypeText, NameText, PhoneText, DescText, DateText, LocationText;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TypeText = itemView.findViewById(R.id.TypeText);
            NameText = itemView.findViewById(R.id.NameText);
            PhoneText = itemView.findViewById(R.id.PhoneText);
            DescText = itemView.findViewById(R.id.DescText);
            DateText = itemView.findViewById(R.id.DateText);
            LocationText = itemView.findViewById(R.id.LocationText);
            mainLayout = itemView.findViewById(R.id.Main);
        }
    }
}
