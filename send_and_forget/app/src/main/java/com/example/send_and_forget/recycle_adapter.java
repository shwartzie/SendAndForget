
package com.example.send_and_forget;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;

public class recycle_adapter extends RecyclerView.Adapter<recycle_adapter.ViewHolder> {
    private ArrayList<Schedules.Schedule> schedules;
    private Context context; // Add Context field

    public recycle_adapter(Context context, List<Schedules.Schedule> cardDataList) {
        //this.Schedules = cardDataList;
        this.schedules = (ArrayList<Schedules.Schedule>) cardDataList; //Schedules.getInstance();
        this.context = context;
        System.out.println("recycle_adapter" + this.schedules);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView phone;
        TextView prompt;

        TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            phone = itemView.findViewById(R.id.phone);
            prompt = itemView.findViewById(R.id.prompt);
            date = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this); // Set OnClickListener to the itemView
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // Handle item click here
                Schedules.Schedule clickedItem = schedules.get(position);
                System.out.println("CLICKED ITEM " + clickedItem.getId() + " " + clickedItem.getPrompt() + " " + clickedItem.getDate());
                Intent intent = new Intent(context, controller_view.class);
                intent.putExtra("id", clickedItem.getId());
                intent.putExtra("prompt", clickedItem.getPrompt());
                intent.putExtra("phone", clickedItem.getPhone());
                intent.putExtra("datePicker", clickedItem.getDate().toString());
                intent.putExtra("time", clickedItem.getTime());
                context.startActivity(intent);


            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Schedules.Schedule schedule = schedules.get(position);
        Date currentDate = schedule.getDate();
        String date = currentDate.getDay() + "/" + currentDate.getMonth() + "/" + currentDate.getYear() + " " + schedule.getTime();
        holder.phone.setText(holder.phone.getText() + " " + schedule.getPhone());
        holder.prompt.setText(holder.prompt.getText() + " " + schedule.getPrompt());
        holder.date.setText(holder.date.getText() + " " + date);

    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }
}
