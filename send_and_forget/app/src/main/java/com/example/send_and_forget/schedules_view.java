package com.example.send_and_forget;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
public class schedules_view extends AppCompatActivity {
    private RecyclerView recyclerView;
    private recycle_adapter adapter;
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, controller_view.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedules);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Schedules schedules = Schedules.getInstance(); // Assuming schedules is your data source
        adapter = new recycle_adapter(schedules.getAllSchedules());
        recyclerView.setAdapter(adapter);
    }
}
