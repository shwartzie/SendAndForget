package com.example.send_and_forget.http_service;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpService {

    private static final String TAG = "HttpService";

    private static HttpService instance;

    private HttpService() {
        // Private constructor to prevent instantiation
//        String this::baseUrl = "http://localhost:3030/";

    }


    public static synchronized HttpService getInstance() {
        if (instance == null) {
            instance = new HttpService();
        }
        return instance;
    }

    public interface HttpCallback {
        void onSuccess(HttpResponse response) throws JSONException;
        void onFailure(int statusCode, String message);
    }

    public static void sendRequest(String url, String method, JSONObject requestBody, HttpCallback callback) {

        new HttpRequestTask(url, method, requestBody, callback).execute();
    }

    private static class HttpRequestTask extends AsyncTask<Void, Void, HttpResponse> {
        private final String url;
        private final String method;
        private final JSONObject requestBody;
        private final HttpCallback callback;

        public HttpRequestTask(String url, String method, JSONObject requestBody, HttpCallback callback) {
            this.url = url;
            this.method = method;
            this.requestBody = requestBody;
            this.callback = callback;
        }

        @Override
        protected HttpResponse doInBackground(Void... voids) {
            try {
                URL urlObject = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
                connection.setRequestMethod(method);

                if (requestBody != null) {
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(requestBody.toString().getBytes());
                    outputStream.flush();
                    outputStream.close();
                }

                int responseCode = connection.getResponseCode();
                String responseMessage = connection.getResponseMessage();
                String responseData = "";

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder responseBuilder = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    reader.close();
                    responseData = responseBuilder.toString();
                }

                connection.disconnect();

                return new HttpResponse(responseCode, responseMessage, responseData);
            } catch (IOException e) {
                Log.e(TAG, "Error sending HTTP request: " + e.getMessage());
                return new HttpResponse(HttpURLConnection.HTTP_INTERNAL_ERROR, "Internal Server Error", "");
            }
        }

        @Override
        protected void onPostExecute(HttpResponse httpResponse) {
            if (httpResponse.getCode() == HttpURLConnection.HTTP_OK) {
                try {
                    callback.onSuccess(httpResponse);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } else {
                callback.onFailure(httpResponse.getCode(), httpResponse.getMessage());
            }
        }
    }

    public static class HttpResponse {
        private final int code;
        private final String message;
        private final String data;

        public HttpResponse(int code, String message, String data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public String getData() {
            return data;
        }
    }
}

