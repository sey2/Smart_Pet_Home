package com.smart_pet;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class DiaryAdapter extends RecyclerView.Adapter<Holder> {

    public ArrayList<DiaryDTO> listData = new ArrayList<>();
    private View context;

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context = parent;
        View itemView = inflater.inflate(R.layout.recycler_item, parent, false);

        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        DiaryDTO diary = listData.get(position);
        holder.setDiary(diary, context);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public ArrayList<DiaryDTO> getItemList(){return listData;}

    public void deleteList(){listData = new ArrayList<>();}
}

class Holder extends RecyclerView.ViewHolder{

    protected TextView humidityTextView,tempTextView,dateTextView;
    protected TextView timetextView, bobTimeText;
    protected ImageView diaryImageView;

    public Holder(View itemView){
        super(itemView);

        diaryImageView = itemView.findViewById(R.id.diary_img);
        tempTextView = itemView.findViewById(R.id.tempTextView);
        humidityTextView = itemView.findViewById(R.id.HumidityTextView);
        dateTextView = itemView.findViewById(R.id.DateTextView);
        timetextView = itemView.findViewById(R.id.bobTimeText);
        bobTimeText = itemView.findViewById(R.id.bobTimeText);

        itemView.setOnClickListener((v)->{
            Toast.makeText(itemView.getContext(),"클릭됨", Toast.LENGTH_SHORT).show();
        });

    }

    public void setDiary(DiaryDTO diary, View context){
        tempTextView.setText(diary.getTempText());
        humidityTextView.setText(diary.getHumidityText());
        dateTextView.setText(diary.getDateText());
        timetextView.setText(diary.getTimeText());
        bobTimeText.setText(diary.getBobTimeText());
        Glide.with(context).load(diary.getImg()).into(diaryImageView);
    }
}