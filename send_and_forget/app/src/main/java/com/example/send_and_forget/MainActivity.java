package com.example.send_and_forget;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.send_and_forget.login_activity;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        // Start the login_activity
        Intent intent = new Intent(this, login_activity.class);
        startActivity(intent);
    }
}

