package com.example.send_and_forget;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

public class HomePage extends AppCompatActivity {
    private TextView loggedUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        loggedUserName = findViewById(R.id.loggedUserName);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        loggedUserName.setText(username);
    }
}
