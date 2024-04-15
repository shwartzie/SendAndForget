package com.example.send_and_forget;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Schedules {
    private static Schedules instance;
    private ArrayList<Schedule> scheduleList;

    public interface SchedulesController {
        void set(String prompt, String phone, Date date, String time, Integer scheduleId);

        ArrayList<Schedule> getAllSchedules();

        Schedule getScheduleById(int id);

        boolean update(int id, String prompt, String phone, Date date, String time);

        boolean delete(int id);

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
    public void set(String prompt, String phone, Date date, String time, Integer scheduleId) {
        try {
            if (scheduleId != null) {
                update(scheduleId, prompt, phone, date, time);
                return;
            }
            int lastId;
            if (scheduleList.size() == 0) {
                lastId = 100;
            } else {
                lastId = scheduleList.get(scheduleList.size() - 1).getId();
            }
            int id = lastId + 1;
            System.out.println("last id " + lastId + " " + id);

            scheduleList.add(new Schedule(id, prompt, phone, date, time));
//            System.out.println("scheduleList " + scheduleList.get(0).getDate() + " " + scheduleList.get(0).getDate().toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception" + e.getMessage());
            return;
        }

    }

    // Method to retrieve all schedules
    public List<Schedule> getAllSchedules() {
        return this.scheduleList;
    }

    // Method to retrieve a schedule by ID
    public Schedule getScheduleById(int id) {
        for (Schedule schedule : this.scheduleList) {
            if (schedule.id == id) {
                return schedule;
            }
        }
        return null;
    }

    // Method to update a schedule
    public boolean update(int id, String prompt, String phone, Date date, String time) {
        System.out.println("Updating.. " + id );
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
    public boolean delete(int id) {
        System.out.println("Removing.. " + id);
        for (Schedule schedule : scheduleList) {
            if (schedule.getId() == id) {
                System.out.println("found.. " + id);
                this.scheduleList.remove(schedule);
                return true; // Schedule deleted successfully
            }
        }
        return false; // Schedule not found
    }

    public static class Schedule {
        private int id;
        private String prompt;
        private String phone;
        private Date date;
        private String time;

        public Schedule(int id, String prompt, String phone, Date date, String time) {
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

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
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
