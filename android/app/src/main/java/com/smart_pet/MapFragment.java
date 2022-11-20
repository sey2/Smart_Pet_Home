package com.smart_pet;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);

        MapView mapView = new MapView(rootView.getContext());

        ViewGroup mapViewContainer = (ViewGroup) rootView.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        try {
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    private void loadData() throws IOException, CsvException {
        AssetManager assetManager = this.getActivity().getAssets();
        InputStream inputStream = assetManager.open("hospital.csv");
        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream, "EUC-KR"));

        List<String[]> allContent = (List<String[]>) csvReader.readAll();
        for(String content[] : allContent){
            StringBuilder sb = new StringBuilder("");
            Log.d("csv", content[19] + " 병원명: " + content[21] + " X: " + content[26] + " Y: " + content[27] );
        }
    }
}