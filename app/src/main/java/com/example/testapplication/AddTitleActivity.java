package com.example.testapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class AddTitleActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private EditText ed_title,ed_a,ed_b,ed_c,ed_d,ed_answer;
    private Button btn_save,btn_cannel;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_title);
        
        ed_title = findViewById(R.id.c_title);
        ed_a = findViewById(R.id.c_A);
        ed_b = findViewById(R.id.c_B);
        ed_c = findViewById(R.id.c_C);
        ed_d = findViewById(R.id.c_D);
        ed_answer = findViewById(R.id.c_answer);

        dbHelper = new  DBHelper(AddTitleActivity.this);
        id = getIntent().getIntExtra("id",0);
        if (id!=0){
            db = dbHelper.getReadableDatabase();
           Cursor cursor =  db.rawQuery("select * from question where id = "+id,null);
           while (cursor.moveToNext()){
               ed_title.setText(cursor.getString(cursor.getColumnIndex("title")));
               ed_a.setText(cursor.getString(cursor.getColumnIndex("A")));
               ed_b.setText(cursor.getString(cursor.getColumnIndex("B")));
               ed_c.setText(cursor.getString(cursor.getColumnIndex("C")));
               ed_d.setText(cursor.getString(cursor.getColumnIndex("D")));
               ed_answer.setText(cursor.getString(cursor.getColumnIndex("answer")));
           }
        }
        btn_save = findViewById(R.id.save);
        btn_cannel = findViewById(R.id.cannel);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = dbHelper.getWritableDatabase();
                String title = ed_title.getText().toString();
                String a = ed_a.getText().toString().trim();
                String b = ed_b.getText().toString().trim();
                String c = ed_c.getText().toString().trim();
                String d = ed_d.getText().toString().trim();
                String answer = ed_answer.getText().toString();
                ContentValues values = new ContentValues();
                values.put("title",title);
                values.put("A",a);
                values.put("B",b);
                values.put("C",c);
                values.put("D",d);
                values.put("answer",answer);
                if(id==0)
                    db.insert("question",null,values);
                else {
                    String[] args = {String.valueOf(id)};
                    db.update("question",values,"id=?",args);
                }
                db.close();

                onBackPressed();
            }
        });
        btn_cannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}