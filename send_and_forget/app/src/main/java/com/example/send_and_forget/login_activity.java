package com.example.send_and_forget;

import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
import com.example.send_and_forget.MainActivity;
import androidx.appcompat.app.AppCompatActivity;
import com.example.send_and_forget.R;

public class login_activity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initializeViews();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View myView) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                System.out.println( username );

                if (isValidCredentials(username, password)) {
                    login(username, password);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void initializeViews() {
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
    }
    private boolean isValidCredentials(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }

    private void login(String username, String password) {
        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
    }
    public void h() {
        System.out.println(R.id.buttonLogin);
    }
}