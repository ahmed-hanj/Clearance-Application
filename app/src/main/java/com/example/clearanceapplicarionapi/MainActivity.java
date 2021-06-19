package com.example.clearanceapplicarionapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EditText rollno, sname;

    String[] degreename = {"BS", "MS", "BSc", "MSc", "Phd"};
    String[] deptname = {"CS", "SE", "IT", "PE", "Botany"};
    Spinner degreespn;
    Spinner deptspn;
    String degreeSelect, deptSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        degreespn = findViewById(R.id.degreeSpn);
        deptspn = findViewById(R.id.deptspn);
        rollno = findViewById(R.id.rollno);
        sname = findViewById(R.id.name_id);
        getValues_frominput();
        addconnection();
    }

    public void getValues_frominput() {
        ArrayAdapter degrees = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, degreename);
        degreespn.setAdapter(degrees);

        degreespn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                degreeSelect = degreename[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter depts = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, deptname);
        deptspn.setAdapter(depts);

        deptspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                deptSelect = deptname[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void addData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("sn", sname.getText().toString()).add("sid", rollno.getText().toString())
                .add("deg", degreeSelect).add("dept", deptSelect).build();


        Request request = new Request.Builder().url(serverconfig.IP+"/student").post(formBody).build();

        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                TextView textView = findViewById(R.id.text_view);
                textView.setText("Not Connected!");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                TextView textView = findViewById(R.id.text_view);
                textView.setText("Connected");
            }
        });
    }

    public void addconnection() {
        OkHttpClient okHttpClient = new OkHttpClient();


        Request request = new Request.Builder().url(serverconfig.IP).build();

        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                TextView textView = findViewById(R.id.text_view);
                textView.setText("Not Connected!");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                TextView textView = findViewById(R.id.text_view);
                textView.setText("Connected");
            }
        });

    }


    public void AddApplication(View view) {
        addData();
        Log.d("ASD", rollno.getText().toString() + " " + sname.getText() + " " + degreeSelect + " " + deptSelect);
    }

    public void ApplicationList(View view) {
        Intent applicationList= new Intent(this,datalist.class);
        startActivity(applicationList);
    }


}