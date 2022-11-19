package com.smart_pet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        DiaryDTO diaryDTO = new DiaryDTO(R.drawable.default_img, "35%", "21C",
                "2022-11-18", "10:21:20","오전");
        DiaryDTO diaryDTO1 = new DiaryDTO(R.drawable.default_img, "35%", "21C",
                "2022-11-18", "10:21:20","오전");
        recyclerList.add(diaryDTO);
        recyclerList.add(diaryDTO1);
        diaryAdapter.listData = recyclerList;
        diaryAdapter.notifyDataSetChanged();

        return rootView;
    }
}