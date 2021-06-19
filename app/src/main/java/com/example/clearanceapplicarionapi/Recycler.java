package com.example.clearanceapplicarionapi;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Recycler extends RecyclerView.Adapter<Recycler.RVHolder> {
    public static ArrayList<String> arrayList;
//    OnUpdateRecord recordupdation;
    public Recycler(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Recycler.RVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view, parent, false);
        RVHolder rvHolder = new RVHolder(view);

//        recordupdation= (OnUpdateRecord) parent.getContext();
        return rvHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull Recycler.RVHolder holder, int position) {
        holder.textView.setText(arrayList.get(position));
        holder.delete_Btn.setOnClickListener(v -> {
            String url = serverconfig.IP+"/student";
            DeleteRecord(url, datalist.rollno.get(position));
//            Log.d("ASD", String.valueOf(ListData.std_roll.size()));
        });

        holder.update_Btn.setOnClickListener(v -> {

            String url = serverconfig.IP+"/student";
            DeleteRecord(url, datalist.rollno.get(position));

            Intent main = new Intent(v.getContext(),MainActivity.class);
            v.getContext().startActivity(main);
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RVHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        public TextView textView;
        public Button update_Btn;
        public Button delete_Btn;

        public RVHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.details);
            update_Btn = itemView.findViewById(R.id.update);
            delete_Btn = itemView.findViewById(R.id.delete);
        }
    }

    void DeleteRecord(String conectionurl, Long data) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(conectionurl + "/" + data).delete().build();

        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            }
        });

    }

    /// Todo update function


//    interface OnUpdateRecord{
//        void updateRecord(String conectionurl, Long data);
//
//    }

}
