package com.example.send_and_forget;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Schedules {
    private static Schedules instance;
    private List<Schedule> scheduleList;

    public interface SchedulesController {
        void addSchedule(JSONObject scheduleJson);
        List<Schedule> getAllSchedules();
        Schedule getScheduleById(int id);
        boolean updateSchedule(int id, JSONObject scheduleJson);
        boolean deleteSchedule(int id);
    }

    public interface ScheduleInterface {
        int getId();
        String getPrompt();
        String getPhone();
        String getDate();
        String getTime();
    }
    public Schedules() {
        this.scheduleList = new ArrayList<>();
    }

    public static Schedules getInstance() {
        if (instance == null) {
            instance = new Schedules();
        }
        return instance;
    }
    // Method to add a new schedule
    public void addSchedule(JSONObject scheduleJson) {
        try {
            int id = scheduleJson.getInt("id");
            String prompt = scheduleJson.getString("prompt");
            String phone = scheduleJson.getString("phone");
            String date = scheduleJson.getString("date");
            String time = scheduleJson.getString("time");
            scheduleList.add(new Schedule(id, prompt, phone, date, time));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception"+e.getMessage());
            return;
        }

    }

    // Method to retrieve all schedules
    public List<Schedule> getAllSchedules() {
        return scheduleList;
    }

    // Method to retrieve a schedule by ID
    public JSONObject getScheduleById(int id) throws JSONException {
        for (Schedule schedule : scheduleList) {
            System.out.println("ID" + id +" "+ schedule.id);
//            scheduleList.
//            if (schedule.getId() == id) {
//                JSONObject foundSchedule = null;
//                foundSchedule.put("prompt", schedule);
//                foundSchedule.put("phone", schedule.phone);
//                foundSchedule.put("date", schedule.date);
//                foundSchedule.put("time", schedule.time);
//                return foundSchedule;
//            }
        }
        return null; // Schedule not found
    }

    // Method to update a schedule
    public boolean updateSchedule(int id, String prompt, String phone, String date, String time) {
        for (Schedule schedule : scheduleList) {
            if (schedule.getId() == id) {
                schedule.setPrompt(prompt);
                schedule.setPhone(phone);
                schedule.setDate(date);
                schedule.setTime(time);
                return true; // Schedule updated successfully
            }
        }
        return false; // Schedule not found
    }

    // Method to delete a schedule by ID
    public boolean deleteSchedule(int id) {
        for (Schedule schedule : scheduleList) {
            if (schedule.getId() == id) {
                scheduleList.remove(schedule);
                return true; // Schedule deleted successfully
            }
        }
        return false; // Schedule not found
    }

    public static class Schedule {
        private int id;
        private String prompt;
        private String phone;
        private String date;
        private String time;

        public Schedule(int id, String prompt, String phone, String date, String time) {
            this.id = id;
            this.prompt = prompt;
            this.phone = phone;
            this.date = date;
            this.time = time;
        }

        // Getters and setters for Schedule fields
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPrompt() {
            return prompt;
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
