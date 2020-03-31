package com.example.roomdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AnimalsDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(),
                AnimalsDatabase.class,
                "animals_db").build();


        Button addBTN = findViewById(R.id.addBTN);
        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Button", "add button clicked");
                EditText inputET = findViewById(R.id.inputET);
                String theName = inputET.getText().toString();
                Cat myCat = new Cat();
                myCat.name = theName;
                myCat.weight = 10.0;
                threadInsert(myCat);

            }
        });

    }

    public void threadInsert(final Cat c){
        new Thread(
                new Runnable() {
            @Override
            public void run() {
                db.catDao().insert(c);
                List<Cat> catList = db.catDao().getAll();
                for (Cat cat : catList) {
                    Log.d("DB", "cat: " + cat.id
                            + " named " + cat.name
                            + " weighs " + cat.weight);
                }
            }
        }).start();
    }
}
