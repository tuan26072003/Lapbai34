package com.example.lap.Lap3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lap.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Bai1 extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextGPA;
    private Button buttonSubmit;
    private TextView textViewResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai1);
        editTextName = findViewById(R.id.editTextName);
        editTextGPA = findViewById(R.id.editTextGPA);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        textViewResult = findViewById(R.id.textViewResult);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String gpa = editTextGPA.getText().toString();

                if (!name.isEmpty() && !gpa.isEmpty()) {
                    String url = "http://10.24.48.212/web/student_Get.php" + URLEncoder.encode(name) + "&gpa=" + URLEncoder.encode(gpa);
                    new GetDataFromServerTask().execute(url);
                }
            }
        });
    }

    private class GetDataFromServerTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            StringBuilder result = new StringBuilder();

            try {
                URL requestUrl = new URL(url);
                urlConnection = (HttpURLConnection) requestUrl.openConnection();
                urlConnection.setRequestMethod("GET");

                InputStream inputStream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            textViewResult.setText(result);
        }
    }

}