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

public class MainActivity extends AppCompatActivity {
    EditText etCubeSide;
    Button btnCalculateVolume;
    TextView tvVolume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        etCubeSide = findViewById(R.id.etCubeSide);
        btnCalculateVolume = findViewById(R.id.btnCalculateVolume);
        tvVolume = findViewById(R.id.tvVolume);

        btnCalculateVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://yourserver.com/calculateVolume.php";
                new CalculateVolumeTask().execute(url);
            }
        });
    }

    private class CalculateVolumeTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("side", etCubeSide.getText().toString());

                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(jsonObject.toString());
                writer.flush();
                writer.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response += line;
                }
                reader.close();
                conn.disconnect();

            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                double volume = jsonObject.getDouble("volume");
                tvVolume.setText("Volume: " + volume);
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage());
            }
        }

    }
}