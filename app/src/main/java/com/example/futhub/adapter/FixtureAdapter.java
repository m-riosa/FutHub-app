package com.example.futhub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.futhub.R;
import com.example.futhub.models.Fixture;
import com.example.futhub.models.FixtureData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FixtureAdapter extends RecyclerView.Adapter<FixtureViewHolder>{
    Context context;
    List<FixtureData> list;

    public FixtureAdapter(Context context, List<FixtureData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FixtureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FixtureViewHolder(LayoutInflater.from(context).inflate(R.layout.list_fixture, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FixtureViewHolder holder, int position) {
        final FixtureData data = list.get(position);

        holder.textView_home.setText(data.teams.home.name);
        holder.textView_away.setText(data.teams.away.name);

        holder.textView_match.setText(data.teams.home.name + " vs " + data.teams.away.name);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d, MMM");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
        SimpleDateFormat givenFormat = new SimpleDateFormat("yyyy-MM-DDTHH:mm:ss+ee:ee");

        try{
            Date date = givenFormat.parse(String.valueOf(data.date));
            holder.textView_time.setText(dateFormat.format(date) + "\n" + timeFormat.format(date));
        } catch(ParseException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {

        return list.size();
    }
}

class FixtureViewHolder extends RecyclerView.ViewHolder {
    TextView textView_home, textView_away, textView_time, textView_match;
    //ImageView

    public FixtureViewHolder(@NonNull View itemView) {

        super(itemView);
        textView_home = itemView.findViewById(R.id.textView_home);
        textView_away = itemView.findViewById(R.id.textView_away);
        textView_time = itemView.findViewById(R.id.textView_time);
        textView_match = itemView.findViewById(R.id.textView_match);
    }
}
