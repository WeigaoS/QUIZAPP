package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private List<Score> scoreList;
    private ScoreAdapter scoreAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        recyclerView = findViewById(R.id.recycler);
        dbHelper = new DBHelper(ScoreActivity.this);
        db = dbHelper.getReadableDatabase();
        scoreList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from score",null);
        while (cursor.moveToNext()){
            scoreList.add(new Score(cursor.getInt(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("score")),cursor.getString(cursor.getColumnIndex("date"))));
        }
        cursor.close();
        db.close();
        scoreAdapter = new ScoreAdapter(ScoreActivity.this, scoreList);
        recyclerView.setAdapter(scoreAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ScoreActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }
}