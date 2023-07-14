package com.example.lap.Buoi5cach2;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lap.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityTodos extends AppCompatActivity {

    private ListView mListView;
    private List<ItemTodo> mList=new ArrayList<>();
    private TodoAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);
        mListView = findViewById(R.id.lvCustomListView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ItemsApiService apiService = retrofit.create(ItemsApiService.class);
        Call<List<ItemTodo>> call = apiService.getItemsTodo();

        call.enqueue(new Callback<List<ItemTodo>>() {
            @Override
            public void onResponse(Call<List<ItemTodo>> call, Response<List<ItemTodo>> response) {
                if (response.isSuccessful()) {
                    // thu vien gson
                    mList = response.body();
                    Log.i("Sync","Sucess");
                    Log.i("Sync",String.valueOf(mList.size()));
                    customAdapter = new TodoAdapter(ActivityTodos.this,mList);
                    mListView.setAdapter(customAdapter);
                } else {
                    Log.i("Sync","Fail");
                }

            }

            @Override
            public void onFailure(Call<List<ItemTodo>> call, Throwable t) {

            }
        });



        Log.i("INT", String.valueOf(mList.size()));



    }
}