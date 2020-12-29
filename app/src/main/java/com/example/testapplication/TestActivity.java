package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TestActivity extends AppCompatActivity {
    private Timer mTimer;
    private TimerTask mTask;
    private TextView tv_time,tv_title,tv_A,tv_B,tv_C,tv_D;
    private Button btn_next;
    private RadioGroup radioGroup;
    private RadioButton radio_A,radio_B,radio_C,radio_D;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private int time = 0,sum=0,change = 0,score = 0;
    private List<Title> titleList;
    private String answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tv_time = findViewById(R.id.time);
        tv_title = findViewById(R.id.title);
        tv_A = findViewById(R.id.textViewA);
        tv_B = findViewById(R.id.textViewB);
        tv_C = findViewById(R.id.textViewC);
        tv_D = findViewById(R.id.textViewD);
        btn_next = findViewById(R.id.next);
        radio_A = findViewById(R.id.radioA);
        radio_B = findViewById(R.id.radioB);
        radio_C = findViewById(R.id.radioC);
        radio_D = findViewById(R.id.radioD);
        radioGroup = findViewById(R.id.radiogroup);
        dbHelper = new DBHelper(TestActivity.this);
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select count (*) as num from question",null);
        titleList = new ArrayList<>();
        while (cursor.moveToNext()){
            sum = cursor.getInt(cursor.getColumnIndex("num"));
        }
        cursor.close();
        if (sum>=10) {
            cursor = db.rawQuery("SELECT * FROM question ORDER BY RANDOM() limit 10", null);
            while (cursor.moveToNext()){
                titleList.add(new Title(cursor.getInt(cursor.getColumnIndex("id"))
                        ,cursor.getString(cursor.getColumnIndex("title")),cursor.getString(cursor.getColumnIndex("A")),cursor.getString(cursor.getColumnIndex("B")),
                        cursor.getString(cursor.getColumnIndex("C")),cursor.getString(cursor.getColumnIndex("D")),cursor.getString(cursor.getColumnIndex("answer"))));
            }
            cursor.close();
        }else{
            cursor = db.rawQuery("SELECT * FROM question", null);
            while (cursor.moveToNext()){
                titleList.add(new Title(cursor.getInt(cursor.getColumnIndex("id"))
                        ,cursor.getString(cursor.getColumnIndex("title")),cursor.getString(cursor.getColumnIndex("A")),cursor.getString(cursor.getColumnIndex("B")),
                        cursor.getString(cursor.getColumnIndex("C")),cursor.getString(cursor.getColumnIndex("D")),cursor.getString(cursor.getColumnIndex("answer"))));
            }
            cursor.close();
        }
        final int temp;
        if (sum<10)
            temp = sum;
        else
            temp = 10;
        db.close();

        Title title = titleList.get(0);
        tv_title.setText(title.getTitle());
        tv_A.setText(title.getA());
        tv_B.setText(title.getB());
        tv_C.setText(title.getC());
        tv_D.setText(title.getD());
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById(i);
                answer = radioButton.getText().toString();
            }
        });
        Log.d("TAG", "sum: "+sum);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Title title = titleList.get(change);
                if (answer.equals(title.getanswer()))
                        score++;
                if (change<temp)
                        change++;
                if (change!=temp){
                   title = titleList.get(change);
                   tv_title.setText(title.getTitle());
                   tv_A.setText(title.getA());
                   tv_B.setText(title.getB());
                   tv_C.setText(title.getC());
                   tv_D.setText(title.getD());
                    radio_A.setChecked(false);
                    radio_B.setChecked(false);
                    radio_C.setChecked(false);
                    radio_D.setChecked(false);
                }
                if (change==temp){
                    Intent intent = new Intent(TestActivity.this,FinshActivity.class);
                    intent.putExtra("score",score);
                    intent.putExtra("time",time);
                    startActivity(intent);
                    finish();
                }
                Log.d("TAG", "change: "+change);
                Log.d("TAG", "score: "+score);
            }
        });


        if (mTimer == null && mTask == null) {
            mTimer = new Timer();
            mTask = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_time.setText(String.format(" %02d:%02d", time / 60, time % 60));
                            time++;
                        }
                    });
                }
            };
            mTimer.schedule(mTask, 0, 1000);
        }



    }
}