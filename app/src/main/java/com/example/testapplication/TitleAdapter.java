package com.example.testapplication;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TitleAdapter  extends RecyclerView.Adapter<TitleAdapter.TitleAdapterViewHolder> {
    Context context;
    List<Title> titleList;
    DBHelper dbHelper;
    SQLiteDatabase db;

    public TitleAdapter(Context context, List<Title> titleList) {
        this.context = context;
        this.titleList = titleList;
    }


    @NonNull
    @Override
    public TitleAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new TitleAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TitleAdapterViewHolder holder, final int position) {
        final Title title = titleList.get(position);
        holder.tv_id.setText(String.valueOf(position+1));
        holder.tv_title.setText("QUESTION："+title.getTitle());
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new DBHelper(context);
                db = dbHelper.getWritableDatabase();
                db.execSQL("Delete from question where id = "+title.getId());
                db.close();
                titleList.remove(title);
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,AddTitleActivity.class);
                intent.putExtra("id",title.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }


    class TitleAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id,tv_title;
        ImageButton btn_delete;

        public TitleAdapterViewHolder(View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.id);
            tv_title = itemView.findViewById(R.id.title);
            btn_delete = itemView.findViewById(R.id.delete);

        }
    }

}