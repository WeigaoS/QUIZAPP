package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class FinshActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private TextView tv_score;
    private Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finsh);
        Intent intent = getIntent();
        int score = intent.getIntExtra("score",0);
        int time = intent.getIntExtra("time",0);
        tv_score = findViewById(R.id.tv_score);
        btn_back = findViewById(R.id.back);
        tv_score.setText("GRADE："+score);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        dbHelper = new DBHelper(FinshActivity.this);
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("score",score);
        values.put("date",String.format(" %02d:%02d", time / 60, time % 60));
        db.insert("score",null,values);
        db.close();

    }
}