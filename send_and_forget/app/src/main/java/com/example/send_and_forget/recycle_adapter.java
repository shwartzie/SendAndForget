
package com.example.send_and_forget;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.content.Context;
public class recycle_adapter extends RecyclerView.Adapter<recycle_adapter.ViewHolder> {
        private List<Schedules.Schedule> schedules;
        private Context context; // Add Context field
        public recycle_adapter(Context context, List<Schedules.Schedule> cardDataList) {
            //this.Schedules = cardDataList;
            this.schedules = (List<Schedules.Schedule>) cardDataList; //Schedules.getInstance();
            this.context = context;
            System.out.println("recycle_adapter" + this.schedules);
        }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView;
        TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            itemView.setOnClickListener(this); // Set OnClickListener to the itemView
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // Handle item click here
                Schedules.Schedule clickedItem = schedules.get(position);
                System.out.println("Clicked Item " + clickedItem.getId() + " " + clickedItem.getPrompt());
                Intent intent = new Intent(context,  controller_view.class);
                intent.putExtra("prompt",clickedItem.getPrompt());
                intent.putExtra("phone",clickedItem.getPhone());
                intent.putExtra("datePicker",clickedItem.getDate());
                intent.putExtra("time",clickedItem.getTime());
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
            holder.titleTextView.setText(schedule.getPhone());
            holder.descriptionTextView.setText(schedule.getPrompt());

        }

        @Override
        public int getItemCount() {
            return schedules.size();
        }
    }
