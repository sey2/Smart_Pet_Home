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

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.locationtech.proj4j.CRSFactory;
import org.locationtech.proj4j.CoordinateReferenceSystem;
import org.locationtech.proj4j.CoordinateTransform;
import org.locationtech.proj4j.CoordinateTransformFactory;
import org.locationtech.proj4j.ProjCoordinate;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MapFragment extends Fragment {
    private ArrayList<Hospital> hospitalList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {
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

        for(Hospital hospital : hospitalList){
            MapPOIItem marker = new MapPOIItem();
            marker.setItemName(hospital.name);
            marker.setTag(0);

            // EPSG:2097 -> WGS84(GEO) 좌표 변환
            CRSFactory crsFactory = new CRSFactory();
            //변환할 input 값
            CoordinateReferenceSystem in = crsFactory.createFromName("epsg:2097");
            // 원하는 output값
            CoordinateReferenceSystem WGS84 = crsFactory.createFromParameters("WGS84",
                    "+proj=longlat +datum=WGS84 +no_defs");

            CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
            CoordinateTransform trans = ctFactory.createTransform(in, WGS84);
            ProjCoordinate result = new ProjCoordinate();
            trans.transform(new ProjCoordinate(hospital.x, hospital.y), result);

            marker.setMapPoint(MapPoint.mapPointWithGeoCoord(result.y, result.x));
            Log.d("csv", "y: " + result.y + "x: " + result.x);
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

            mapView.addPOIItem(marker);
        }



        return rootView;
    }

    private void loadData() throws IOException, CsvException {
        AssetManager assetManager = this.getActivity().getAssets();
        InputStream inputStream = assetManager.open("hospital.csv");
        CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream, "EUC-KR"));

        List<String[]> allContent = (List<String[]>) csvReader.readAll();

        // 타이틀 제거
        allContent.remove(0);
        for(String content[] : allContent){
            StringBuilder sb = new StringBuilder("");
           // Log.d("csv", content[19] + " 병원명: " + content[21] + " X: " + content[26] + " Y: " + content[27] );

            // 좌표 정보가 없으면 건너 뜀
            if(content[26].equals("") || content[27].equals("")) continue;

            if(content[19].contains("광주") || content[19].contains("전남")) {
                hospitalList.add(new Hospital(content[19], content[21], Double.parseDouble(content[26]), Double.parseDouble(content[27])));
                Log.d("csv", content[19] + " 병원명: " + content[21] + " X: " + content[26] + " Y: " + content[27] );
            }
        }
    }
}