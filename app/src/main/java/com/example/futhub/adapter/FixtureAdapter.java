package com.example.futhub.adapter;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.futhub.R;
import com.example.futhub.models.Response;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FixtureAdapter extends RecyclerView.Adapter<FixtureViewHolder>{
    Context context;
    List<Response> list;

    public FixtureAdapter(Context context, ArrayList<Response> list) {
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
        final Response data = list.get(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d, MMM");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat givenFormat = new SimpleDateFormat("EEE MMM dd H:mm:ss 'EDT' yyyy");

        try{
            Date date = givenFormat.parse(String.valueOf(data.fixture.date));
            holder.textView_time.setText(dateFormat.format(date) + "\n" + timeFormat.format(date));

        } catch(ParseException e){
            e.printStackTrace();
        }
        holder.textView_home.setText(data.teams.home.name);
        holder.textView_away.setText(data.teams.away.name);
        Picasso.get().load(data.teams.home.logo).into(holder.imageView_home);
        Picasso.get().load(data.teams.away.logo).into(holder.imageView_away);

        holder.textView_match.setText(data.teams.home.name + " vs " + data.teams.away.name);
    }

    @Override
    public int getItemCount() {

        return list.size();
    }
}

class FixtureViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView_home, imageView_away;
    TextView textView_home, textView_away, textView_time, textView_match;
    //ImageView

    public FixtureViewHolder(@NonNull View itemView) {

        super(itemView);
        textView_home = itemView.findViewById(R.id.textView_home);
        textView_away = itemView.findViewById(R.id.textView_away);
        textView_time = itemView.findViewById(R.id.textView_time);
        textView_match = itemView.findViewById(R.id.textView_match);
        imageView_home = itemView.findViewById(R.id.imageView_home);
        imageView_away = itemView.findViewById(R.id.imageView_away);
    }
}
