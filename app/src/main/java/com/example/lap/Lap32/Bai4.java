package com.example.lap.Lap32;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lap.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Bai4 extends AppCompatActivity {
    EditText etA, etB, etC;
    Button btnCalculateDelta;
    TextView tvDelta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai4);
        etA = findViewById(R.id.etA);
        etB = findViewById(R.id.etB);
        etC = findViewById(R.id.etC);
        btnCalculateDelta = findViewById(R.id.btnCalculateDelta);
        tvDelta = findViewById(R.id.tvDelta);

        btnCalculateDelta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = etA.getText().toString();
                String b = etB.getText().toString();
                String c = etC.getText().toString();

                new CalculateDeltaTask().execute(a, b, c);
            }
        });
    }

    private class CalculateDeltaTask extends AsyncTask<String, Void, Double> {

        @Override
        protected Double doInBackground(String... params) {
            try {
                String urlString = "";
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("a", params[0]);
                jsonObject.put("b", params[1]);
                jsonObject.put("c", params[2]);

                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(jsonObject.toString());
                writer.flush();
                writer.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                String response = "";
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                reader.close();
                conn.disconnect();

                JSONObject jsonResponse = new JSONObject(response);
                double delta = jsonResponse.getDouble("delta");

                return delta;
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Double delta) {
            if (delta != null) {
                double squareRootOfDelta = Math.sqrt(delta);
                tvDelta.setText("Delta: " + squareRootOfDelta);
            }
        }
    }
}