package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class TitleActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TitleAdapter titleAdapter;
    private List<Title> titleList;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Button btn_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        recyclerView = findViewById(R.id.recyclerview);
        btn_create = findViewById(R.id.create);
        dbHelper = new DBHelper(TitleActivity.this);
        db = dbHelper.getReadableDatabase();
        titleList = new ArrayList<>();

        titleAdapter = new TitleAdapter(TitleActivity.this, titleList);
        recyclerView.setAdapter(titleAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(TitleActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TitleActivity.this,AddTitleActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        titleList.clear();

        Cursor cursor = db.rawQuery("select * from question",null);
        while (cursor.moveToNext()){
            titleList.add(new Title(cursor.getInt(cursor.getColumnIndex("id"))
                    ,cursor.getString(cursor.getColumnIndex("title")),cursor.getString(cursor.getColumnIndex("A")),cursor.getString(cursor.getColumnIndex("B")),
                    cursor.getString(cursor.getColumnIndex("C")),cursor.getString(cursor.getColumnIndex("D")),cursor.getString(cursor.getColumnIndex("answer"))));
        }
        titleAdapter.notifyDataSetChanged();
    }
}