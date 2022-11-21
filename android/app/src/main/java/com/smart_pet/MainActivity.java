package com.smart_pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity{

    private NavController nav_host;
    private BottomNavigationView nav_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bottom nav bar
        nav_host = Navigation.findNavController(this, R.id.nav_host);
        nav_bar = findViewById(R.id.nav_bar);
        NavigationUI.setupWithNavController(nav_bar, nav_host);

        String url = "http://casio2978.dothome.co.kr/PetReceive.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try
            {
                JSONObject object = new JSONObject(response);
                Log.d("json", object.toString());
                JSONArray jsonArray =  object.getJSONArray("result");

                for (int i = 0; i < jsonArray.length()-1; i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.d("json", jsonObject.toString());

                    String temp = jsonObject.getString("temp");
                    String humidity = jsonObject.getString("humidity");
                    String day = jsonObject.getString("day");
                    String time = jsonObject.getString("time");
                    Log.d("json", "temp: " + temp + " humidity: " + humidity + " day: " + day + " time: " + time);
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, "실패함. 인터넷 연결 확인", Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }




}