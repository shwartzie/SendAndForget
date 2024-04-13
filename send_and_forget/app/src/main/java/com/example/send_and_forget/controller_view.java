package com.example.send_and_forget;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import java.util.Calendar;
import java.util.Date;

import android.widget.Toast;
import org.json.JSONObject;

public class controller_view extends AppCompatActivity {
    private TextView loggedUserName;
    private  TextView prompt;
    private DatePicker datePicker;
    private Calendar calendar;
    private  TextView phone;
    private  Button scheduleBtn;
    private TextView time;
    private Button pickDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        loggedUserName = findViewById(R.id.loggedUserName);
        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        loggedUserName.setText(username);

        TextView prompt = findViewById(R.id.prompt);
        TextView phone =  findViewById(R.id.phone);
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker); // initiate a date picker
        datePicker.setSpinnersShown(false); // set false value for the spinner shown function
        TextView time = findViewById(R.id.time);
        Calendar calendar = Calendar.getInstance();
        Button scheduleBtn = findViewById(R.id.buttonSchedule);
        datePicker.setVisibility(View.GONE);
        time.setVisibility(View.GONE);
        Button pickDate = findViewById(R.id.pickDate);
        TextView promptsLink = findViewById(R.id.promptsLink);

        promptsLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(controller_view.this, schedules_view.class);
                System.out.println("before go SCHEDULES");
                //intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);


        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show date picker when input button is clicked
                datePicker.setVisibility(View.VISIBLE);
                pickDate.setVisibility(View.GONE);
            }
        });

        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject requestBody = new JSONObject();
            try {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                Date date = new Date(year, month, day);

                System.out.println("DATE "+date);

                requestBody.put("prompt", prompt.getText());
                requestBody.put("phone", phone.getText());
                requestBody.put("date", date);
                requestBody.put("time", time.getText());
                Schedules schedules = Schedules.getInstance();
                requestBody.put("id", Integer.parseInt("1"));
                schedules.addSchedule(requestBody);
                System.out.println("schedules"+ schedules.getAllSchedules());
                System.out.println("getScheduleById "+ schedules.getScheduleById(1));
                clean(prompt,phone,datePicker,time,pickDate,calendar);
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Prompt has been scheduled successfully ", Toast.LENGTH_SHORT).show());
//                HttpService.sendRequest("http://localhost:3030/schedule/", "POST", requestBody, new HttpService.HttpCallback() {
//                    @Override
//                    public void onSuccess(HttpService.HttpResponse response) throws JSONException {
//                        String id = response.getData();
//                        Schedules schedules = Schedules.getInstance();
//                        requestBody.put("id", Integer.parseInt(id));
//                        schedules.addSchedule(requestBody);
//                        System.out.println("schedules"+ schedules.getAllSchedules());
//                        clean();
//                        finish();
//                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Prompt has been scheduled successfully ", Toast.LENGTH_SHORT).show());
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, String message) {
//                        runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Schedule failed: " + message, Toast.LENGTH_SHORT).show());
//                    }
//                });


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Exception"+e.getMessage());
                // Handle JSON exception
                return;
            }


            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    time.setVisibility(View.VISIBLE);
                    showTimePicker(calendar,time);
                }
            });
        } else {
            datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                        @Override
                        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            showTimePicker(calendar,time);
                        }
                    });
        }

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(calendar,time);
            }
        });


    }
    public void onBackPressed() {
        Intent intent = new Intent(this, login_activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
    private void clean(TextView prompt,TextView phone, DatePicker datePicker,TextView time, Button pickDate, Calendar calendar) {
        prompt.setText("");
        phone.setText("");
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
        time.setText("");
        pickDate.setVisibility(View.VISIBLE);
        datePicker.setVisibility(View.GONE);
        time.setVisibility(View.GONE);
    }
    private void showTimePicker(Calendar calendar, TextView time) {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(controller_view.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }

}
