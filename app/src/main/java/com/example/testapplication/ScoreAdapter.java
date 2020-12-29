package com.example.testapplication;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreAdapterViewHolder> {
    Context context;
    List<Score> scoreList;
    DBHelper dbHelper;
    SQLiteDatabase db;

    public ScoreAdapter(Context context, List<Score> scoreList) {
        this.context = context;
        this.scoreList = scoreList;
    }


    @NonNull
    @Override
    public ScoreAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.score, parent, false);
        return new ScoreAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreAdapterViewHolder holder, final int position) {
        final Score score = scoreList.get(position);
        holder.tv_id.setText(String.valueOf(position+1));
        holder.tv_score.setText("GRADE："+score.getScore());
        holder.tv_date.setText("TIME："+score.getDate());
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new DBHelper(context);
                db = dbHelper.getWritableDatabase();
                db.execSQL("Delete from score where id = "+score.getId());
                db.close();
                scoreList.remove(score);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }


    class ScoreAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id,tv_score,tv_date;
        ImageButton btn_delete;

        public ScoreAdapterViewHolder(View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.id);
            tv_score = itemView.findViewById(R.id.score);
            tv_date = itemView.findViewById(R.id.date);
            btn_delete = itemView.findViewById(R.id.delete);

        }
    }

}