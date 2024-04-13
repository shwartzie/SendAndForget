
package com.example.send_and_forget;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class recycle_adapter extends RecyclerView.Adapter<recycle_adapter.ViewHolder> {
        private List<Schedules.Schedule> schedules;

        public recycle_adapter(List<Schedules.Schedule> cardDataList) {
            //this.Schedules = cardDataList;
            this.schedules = (List<Schedules.Schedule>) cardDataList; //Schedules.getInstance();
            System.out.println("recycle_adapter" + this.schedules);
        }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
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
