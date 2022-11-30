package com.smart_pet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DiaryFragment extends Fragment {

    private RecyclerView diaryRecycler;
    private DiaryAdapter diaryAdapter;
    private ArrayList<DiaryDTO> recyclerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_diary, container, false);

        diaryAdapter = new DiaryAdapter();
        diaryRecycler = rootView.findViewById(R.id.diaryView);
        diaryRecycler.setAdapter(diaryAdapter);
        diaryRecycler.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));

        recyclerList = new ArrayList<>();

        loadData();

//        DiaryDTO diaryDTO = new DiaryDTO(R.drawable.default_img, "35%", "21C",
//                "2022-11-18", "10:21:20","오전");
//        DiaryDTO diaryDTO1 = new DiaryDTO(R.drawable.default_img, "35%", "21C",
//                "2022-11-18", "10:21:20","오전");
//        recyclerList.add(diaryDTO);
//        recyclerList.add(diaryDTO1);
        diaryAdapter.listData = recyclerList;
        diaryAdapter.notifyDataSetChanged();

        return rootView;
    }

    public void loadData(){
        String url = "http://casio2978.dothome.co.kr/PetReceive.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try
            {
                JSONObject object = new JSONObject(response);
                Log.d("json", object.toString());
                JSONArray jsonArray =  object.getJSONArray("result");

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.d("json", jsonObject.toString());

                    String temp = jsonObject.getString("temp");
                    String humidity = jsonObject.getString("humidity");
                    String day = jsonObject.getString("day");
                    String time = jsonObject.getString("time");
                    String amPm = parseAmPm(time);
                    String filename = temp + humidity + day + time;
                    recyclerList.add(new DiaryDTO(R.drawable.default_img, temp, humidity, day, time, amPm, filename));
                    Log.d("json", "temp: " + temp + " humidity: " + humidity + " day: " + day + " time: " + time);
                }
                diaryAdapter.notifyDataSetChanged();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getActivity().getApplicationContext(), "실패함. 인터넷 연결 확인", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public String parseAmPm(String time){
        int hour = Integer.parseInt(time.substring(0, 2));

        if(hour < 12) return "아침밥";
        else if(hour >= 12 && hour < 18) return "점심밥";
        else return "저녁밥";
    }

}