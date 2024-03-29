package com.example.send_and_forget;
import android.content.Intent;
import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.send_and_forget.http_service.HttpService;

public class login_activity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println( "BLASDFDALSFASDFADSFLOGINACTIVITY" );
        setContentView(R.layout.login);
        initializeViews();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogin(view);
            }
        });
    }
    public void onLogin(View view) {
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        System.out.println( "username -------------" + username );
        HttpService httpService = HttpService.getInstance();
        if (isValidCredentials(username, password)) {
            login(username, password,httpService);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
    private void initializeViews() {
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
    }
    private boolean isValidCredentials(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }

    private void login(String username, String password, HttpService HttpService) {
        // Create a JSON object with the login credentials
//        JSONObject requestBody = new JSONObject();
//        try {
//            requestBody.put("username", username);
//            requestBody.put("password", password);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Exception"+e.getMessage());
//            // Handle JSON exception
//            return;
//        }

        Intent intent = new Intent(login_activity.this, controller.class);
        intent.putExtra("username", username);
        System.out.println("before go ---------------");
        startActivity(intent);
        finish();
        // Use the HttpService singleton instance to send the HTTP request
//        HttpService.sendRequest("http://localhost:3030/login", "POST", requestBody, new HttpService.HttpCallback() {
//            @Override
//            public void onSuccess(String response) {
//                Intent intent = new Intent(login_activity.this, HomePage.class);
//                intent.putExtra("username", username);
//                startActivity(intent);
//                finish();
//            }
//
//            @Override
//            public void onFailure(int statusCode, String message) {
//                // Handle login failure
//                // For example, show a toast message with the failure message
//                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Login failed: " + message, Toast.LENGTH_SHORT).show());
//            }
//        });
    }


}