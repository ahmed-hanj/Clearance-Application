package com.example.clearanceapplicarionapi;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class datalist extends AppCompatActivity {


    private ArrayList<String> data = new ArrayList<>();
    public RecyclerView recyclerView;
    private String fetchdata;
    private ArrayList<String> sname = new ArrayList<>();
    public static ArrayList<Long> rollno = new ArrayList<>();
    private ArrayList<String> dept = new ArrayList<>();
    private ArrayList<String> degree = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datalist);
        recyclerView = findViewById(R.id.RecyclerID);
        Connect(serverconfig.IP+"/student");
        SetAdapter();
    }

    public void SetAdapter() {
        Recycler recycleView = new Recycler(data);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recycleView);
    }


    void Connect(String student) {
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url(student).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("ASD", "Not Connected");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                TextView textView = findViewById(R.id.text_view);
//                textView.setText("Connected");
                fetchdata = response.body().string();
//                Log.d("ASD",fetchdata);
                getStudentData();
            }
        });
    }

    void getStudentData() {
        int lengthOfArray = 0;
        try {
            JSONObject jsonObject = new JSONObject(fetchdata);
            while (lengthOfArray < jsonObject.getJSONArray("Student").length()) {
                JSONObject jsonArray = (JSONObject) jsonObject.getJSONArray("Student").get(lengthOfArray);
                sname.add((String) jsonArray.get("sn"));
                rollno.add((Long) jsonArray.get("sid"));
                degree.add((String) jsonArray.get("deg"));
                dept.add((String) jsonArray.get("dept"));
//                Log.d("ASD","asd");
                data.add(sname.get(lengthOfArray) + " " + rollno.get(lengthOfArray) + " " + degree.get(lengthOfArray) + " " + dept.get(lengthOfArray));
                lengthOfArray++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}